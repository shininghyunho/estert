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
import estert.domain.api.predict.dto.PredictResponse
import estert.domain.deal.dto.DealFilterResponse
import estert.domain.dealHistory.dto.filter.DealHistoryFilterRequest
import estert.domain.dealHistory.dto.filter.DealHistoryFilterResponse
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Stream

private val log = LoggerFactory.getLogger(DealHistoryService::class.java)

// 필터링 최대 반환 갯수
private const val MAX_FILTER_COUNT = 100
// 디폴트 필터링 반환 갯수
private const val DEFAULT_FILTER_COUNT = 20
@Service
class DealHistoryService(
    private val houseService: HouseService,
    private val houseDetailService: HouseDetailService,
    private val dealService: DealService,
    private val molitApartHandler: MolitApartHandler,
    private val addressHandler: AddressHandler,
    private val predictHandler: PredictHandler
) {
    // 영속성 매니저
    @PersistenceContext
    private lateinit var em: EntityManager

    /**
     * @return : 저장된 거래 내역 수
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

    /**
     * @return : 필터링된 거래 내역
     */
    @Transactional(readOnly = true)
    fun filter(request: DealHistoryFilterRequest): DealHistoryFilterResponse {
        // 예측 서버를 이용한 예상시간에 도달할수 있는 houseId, time Map 을 반환
        val predictedHouseMap = makePredictedHouseMap(PredictRequest(
            time = request.time,
            latitude = request.latitude,
            longitude = request.longitude
        ))

        // 필터링된 거래 내역을 스트림
        val dealFilterResponseStream = dealService.getDealFilterResponseStream(
            houseIdList = predictedHouseMap.keys.toList(),
            lowCost = request.lowCost,
            highCost = request.highCost
        )

        // 필터링된 거래 내역을 예상시간과 함께 반환
        val dealHistoryList = mutableListOf<DealHistoryFilterResponse.FilteredDealHistory>()
        dealFilterResponseStream.forEach {
            dealHistoryList.add(DealHistoryFilterResponse.FilteredDealHistory(
                dealId = it.dealId,
                latitude = it.latitude,
                longitude = it.longitude,
                estimatedTime = predictedHouseMap[it.houseId] ?: 0
            ))
            // 영속성 컨텍스트에서 제거
            em.detach(it)
        }

        // 필터링 반환 갯수를 제한한다.
        val limitedDealHistoryList = limitFilterCount(request.count, dealHistoryList)

        return DealHistoryFilterResponse(limitedDealHistoryList)
    }

    /**
     * 부동산 ID로 예측된 시간을 반환한다.
     * @return
     * Map<houseId, time>
     * @sample
     * {1: 30, 2: 25, 3: 35}
     */
    private fun makePredictedHouseMap(predictRequest: PredictRequest): Map<Long, Int> {
        val predictResponse = predictHandler.predict(predictRequest)
        return predictResponse.predictHouseList.associateBy({ it.id }, { it.time })
    }

    // 필터링 반환 갯수를 제한한다.
    private fun limitFilterCount(count: Int?,dealHistoryList: List<DealHistoryFilterResponse.FilteredDealHistory>): List<DealHistoryFilterResponse.FilteredDealHistory> {
        if(count == null) {
            return dealHistoryList.take(DEFAULT_FILTER_COUNT)
        }

        else if(count > MAX_FILTER_COUNT) {
            return dealHistoryList.take(MAX_FILTER_COUNT)
        }

        return dealHistoryList.take(count)
    }
}