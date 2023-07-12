package estert.domain.deal

import estert.domain.deal.dto.DealFilteredResponse
import estert.domain.deal.dto.DealSaveRequest
import estert.domain.house_detail.HouseDetailRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.stream.Stream

@Service
class DealService(
    private val dealRepository: DealRepository,
    private val houseDetailRepository: HouseDetailRepository
){
    @Transactional
    fun save(request: DealSaveRequest): Long {
        return dealRepository.save(Deal(
            cost = request.cost,
            dealDate = LocalDateTime.parse(request.dealDate),
            houseDetail = houseDetailRepository.findById(request.houseDetailId).get()
        )).id
    }

    @Transactional(readOnly = true)
    fun findByCostRange(lowCost: Int, highCost: Int): Stream<DealFilteredResponse> {
        // TODO : return dealRepository.findByCostBetween(lowCost, highCost)
        return TODO()
    }

    @Transactional(readOnly = true)
    fun findByHouseIdAndCostRange(houseId: Long, lowCost: Int, highCost: Int): Stream<DealFilteredResponse> {
        // TODO : return dealRepository.findByHouseIdAndCostBetween(houseId, lowCost, highCost)
        return TODO()
    }
}