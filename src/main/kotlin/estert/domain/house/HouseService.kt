package estert.domain.house

import estert.domain.house.dto.HouseGetResponse
import estert.domain.house.dto.HouseSaveRequest
import estert.domain.house.dto.HouseUpdateRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class HouseService (
    private val houseRepository: HouseRepository
){
    @Transactional
    fun save(request : HouseSaveRequest): Long {
        return houseRepository.save(request.toEntity()).id
    }

    @Transactional(readOnly = true)
    fun findById(id: Long): HouseGetResponse {
        val house = houseRepository.findById(id)
            .orElseThrow { IllegalArgumentException("$id`에 해당하는 아파트가 존재하지 않습니다.") }
        return HouseGetResponse.from(house)
    }

    @Transactional(readOnly = true)
    fun findByIdWithHouseDetails(id: Long): HouseGetResponse {
        return HouseGetResponse.fromWithHouseDetails(houseRepository.findById(id)
            .orElseThrow { IllegalArgumentException("$id`에 해당하는 아파트가 존재하지 않습니다.") })
    }

    @Transactional(readOnly = true)
    fun findByRoadAddressAndDanjiName(roadAddress: String, danjiName: String): HouseGetResponse {
        return HouseGetResponse.from(houseRepository.findByRoadAddressAndDanjiName(roadAddress, danjiName))
    }

    @Transactional(readOnly = true)
    fun findByRoadAddressAndDanjiNameWithHouseDetails(roadAddress: String, danjiName: String): HouseGetResponse {
        return HouseGetResponse.fromWithHouseDetails(houseRepository.findByRoadAddressAndDanjiName(roadAddress, danjiName))
    }

    @Transactional
    fun update(id: Long, request: HouseUpdateRequest) {
        val house = houseRepository.findById(id)
            .orElseThrow { IllegalArgumentException("$id`에 해당하는 아파트가 존재하지 않습니다.") }
        // (도로명,단지명) 이 중복되면 안된다.
        if (request.roadAddress!=null && request.danjiName!=null) {
            try {
                // 존재하지 않으면 Exception 발생
                houseRepository.findByRoadAddressAndDanjiName(request.roadAddress, request.danjiName)
                // exception 이 발생하지 않으면 중복이므로 update 불가능
                throw IllegalArgumentException("이미 존재하는 (도로명,단지명) 입니다.")
            } catch (e: Exception) {
                // 존재하지 않으면 update 가능
            }
        }
        house.update(
            jibunAddress = request.jibunAddress,
            roadAddress = request.roadAddress,
            danjiName = request.danjiName,
            postCode = request.postCode,
            latitude = request.latitude,
            longitude = request.longitude
        )
    }

    @Transactional
    fun deleteById(id: Long) {
        houseRepository.deleteById(id)
    }
}