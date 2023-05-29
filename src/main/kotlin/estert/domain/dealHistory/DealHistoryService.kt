package estert.domain.dealHistory

import estert.domain.dealHistory.dto.DealHistory
import estert.domain.house.HouseService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DealHistoryService(
    private val houseService: HouseService
) {
    @Transactional(readOnly = true)
    fun getDealHistory(houseId: Long): DealHistory {
        return DealHistory(houseService.findByIdWithHouseDetails(houseId))
    }
    @Transactional(readOnly = true)
    fun getDealHistory(roadAddress: String, danjiName: String): DealHistory {
        return DealHistory(houseService.findByRoadAddressAndDanjiNameWithHouseDetails(roadAddress, danjiName))
    }
}