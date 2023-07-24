package estert.domain.deal

import estert.domain.deal.dto.DealFilterResponse
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

    /**
     * @param houseIdList : 필터링할 houseId 리스트
     * @param lowCost : 필터링할 최저 가격
     * @param highCost : 필터링할 최고 가격
     * @return : 필터링된 dealFilterResponse stream
     */
    @Transactional
    fun getDealFilterResponseStream(houseIdList: List<Long>, lowCost: Int, highCost: Int): Stream<DealFilterResponse> {
        return dealRepository.getDealFilterResponseStream(houseIdList, lowCost, highCost)
    }
}