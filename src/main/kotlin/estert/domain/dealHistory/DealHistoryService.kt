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
import estert.domain.molitApart.MolitApartHandler
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private val log = LoggerFactory.getLogger(DealHistoryService::class.java)
@Service
class DealHistoryService(
    private val houseService: HouseService,
    private val houseDetailService: HouseDetailService,
    private val dealService: DealService,
    private val molitApartHandler: MolitApartHandler,
    private val addressHandler: AddressHandler
) {
    @Transactional
    fun save(request: DealHistorySaveRequest) {
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
                return
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
                return
            }
        }
    }

    @Transactional(readOnly = true)
    fun get(houseId: Long): DealHistory {
        return DealHistory(houseService.findByIdWithHouseDetails(houseId))
    }
    @Transactional(readOnly = true)
    fun get(roadAddress: String, danjiName: String): DealHistory {
        return DealHistory(houseService.findByRoadAddressAndDanjiNameWithHouseDetails(roadAddress, danjiName))
    }
}