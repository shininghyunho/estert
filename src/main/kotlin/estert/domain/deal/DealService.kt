package estert.domain.deal

import estert.domain.deal.dto.DealSaveRequest
import estert.domain.house_detail.HouseDetailRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

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
}