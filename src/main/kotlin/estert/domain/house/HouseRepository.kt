package estert.domain.house

import org.springframework.data.jpa.repository.JpaRepository

interface HouseRepository : JpaRepository<House, Long> {
    fun findByRoadAddressAndDanjiName(roadAddress: String, danjiName: String): House
    fun deleteByRoadAddressAndDanjiName(roadAddress: String, danjiName: String)
}