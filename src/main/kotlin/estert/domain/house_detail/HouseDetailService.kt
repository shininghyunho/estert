package estert.domain.house_detail

import estert.domain.house.HouseRepository
import estert.domain.house_detail.dto.HouseDetailSaveRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class HouseDetailService(
    private val houseDetailRepository: HouseDetailRepository,
    private val houseRepository: HouseRepository
){
    // save
    @Transactional
    fun save(request: HouseDetailSaveRequest): Long {
        return houseDetailRepository.save(HouseDetail(
            dedicatedArea = request.dedicatedArea.toBigDecimal(),
            house = houseRepository.findById(request.houseId).get()
        )).id
    }
}