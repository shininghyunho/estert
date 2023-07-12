package estert.domain.dealHistory

import estert.common.error.CustomException
import estert.common.handler.AddressHandler
import estert.domain.deal.DealService
import estert.domain.deal.dto.DealSaveRequest
import estert.domain.dealHistory.dto.DealHistory
import estert.domain.dealHistory.dto.DealHistorySaveRequest
import estert.domain.house.HouseService
import estert.domain.house_detail.HouseDetailService
import estert.domain.house_detail.dto.HouseDetailSaveRequest
import estert.domain.api.molitApart.MolitApartHandler
import estert.domain.api.predict.PredictHandler
import estert.domain.api.predict.dto.PredictRequest
import estert.domain.dealHistory.dto.filter.DealHistoryFilteredRequest
import estert.domain.dealHistory.dto.filter.DealHistoryFilteredResponse
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private val log = LoggerFactory.getLogger(DealHistoryService::class.java)

// 최대 반환 개수
private val DEFAULT_LIMIT = 50
@Service
class DealHistoryService(
    private val houseService: HouseService,
    private val houseDetailService: HouseDetailService,
    private val dealService: DealService,
    private val molitApartHandler: MolitApartHandler,
    private val addressHandler: AddressHandler,
    private val predictHandler: PredictHandler
) {
    @PersistenceContext
    private val entityManager: EntityManager? = null
    /**
     * return : 저장된 거래 내역 수
     */
    @Transactional
    fun save(request: DealHistorySaveRequest) : Int{
        val molitApartList = try {
            molitApartHandler.getMolitApartList(request)
        } catch (e: Exception) {
            log.error("molit api 호출 실패 : ${e.message}")
            throw CustomException(message = "molit api 호출 실패", detail = e.message)
        }
        molitApartList.forEach { molitApart ->
            val validMolitApart = try {
                molitApart.validate()
            } catch (e: Exception) {
                log.error("molitApart validation 실패 : ${e.message}")
                return@forEach // continue
            }
            try {
                val house = houseService.saveAndReturnEntity(addressHandler.makeHouseSaveRequest(validMolitApart))
                val houseDetail = houseDetailService.saveAndReturnEntity(HouseDetailSaveRequest(
                    houseId = house.id,
                    dedicatedArea = validMolitApart.dedicatedArea))
                dealService.save(DealSaveRequest(
                    houseDetailId = houseDetail.id,
                    cost = validMolitApart.cost,
                    dealDate = validMolitApart.dealDate
                ))
            } catch (e: Exception) {
                log.error("dealHistory 저장 실패 : ${e.message}")
                return@forEach
            }
        }

        // 저장된 거래 내역 수
        return molitApartHandler.getTotalCount()
    }

    @Transactional(readOnly = true)
    fun get(houseId: Long): DealHistory {
        return DealHistory(houseService.findByIdWithHouseDetails(houseId))
    }
    @Transactional(readOnly = true)
    fun get(roadAddress: String, danjiName: String): DealHistory {
        return DealHistory(houseService.findByRoadAddressAndDanjiNameWithHouseDetails(roadAddress, danjiName))
    }
    // 필터링된 거래 내역
    @Transactional(readOnly = true)
    fun getFiltered(request: DealHistoryFilteredRequest): List<DealHistoryFilteredResponse> {
        // TODO : 후보군 Map 을 받아와 가격으로 필터링
        // 후보군을 Map 으로 받아옴 (houseId, 예상 시간)
        val houseEstimatedTimeMap = predictHandler.getPredictMap(PredictRequest(
            time = request.time,
            latitude = request.latitude,
            longitude = request.longitude
        ))
        // 가격으로 필터링된 거래들
        val filteredDeals = dealService.findByCostRange(request.lowCost, request.highCost)

        // 시간을 넣어서 반환
        val ret = mutableListOf<DealHistoryFilteredResponse>()
        filteredDeals.forEach {deal ->
            ret.add(DealHistoryFilteredResponse(
                roadAddress = deal.roadAddress,
                danjiName = deal.danjiName,
                cost = deal.cost.toInt(),
                latitude = deal.latitude.toString(),
                longitude = deal.longitude.toString(),
                time = houseEstimatedTimeMap[deal.houseId] ?: 0,
                dealDate = deal.dealDate.toString(),
                dedicatedArea = deal.dedicatedArea.toString()
            ))
            // 영송석 컨텍스트에서 제거
            entityManager?.detach(deal)
            // 갯수 체크
            if (ret.size >= (request.limit ?: DEFAULT_LIMIT)) return@forEach
        }
        return ret
    }
}