package estert.domain.house

import estert.domain.house_detail.HouseDetail
import jakarta.persistence.*
import java.math.BigDecimal

// (roadAddress,danjiName) : index
@Table(
    indexes = [
        Index(name = "idx_roadAddress_danjiName", columnList = "roadAddress,danjiName")
    ]
)
@Entity
class House (
    jibunAddress: String,
    roadAddress: String,
    danjiName: String,
    postCode: Int,
    latitude: BigDecimal,
    longitude: BigDecimal,
){
    var jibunAddress: String = jibunAddress
        private set

    var roadAddress: String = roadAddress
        private set

    var danjiName: String = danjiName
        private set

    var postCode: Int = postCode
        private set

    var latitude: BigDecimal = latitude
        private set

    var longitude: BigDecimal = longitude
        private set

    @OneToMany(mappedBy = "house", cascade = [CascadeType.REMOVE])
    var houseDetails: MutableSet<HouseDetail> = hashSetOf()
        private set

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
    
    // update
    fun update(jibunAddress: String?=null, roadAddress: String?=null, danjiName: String?=null, postCode: Int?=null, latitude: String?=null, longitude: String?=null) {
        jibunAddress?.let { this.jibunAddress = it }
        roadAddress?.let { this.roadAddress = it }
        danjiName?.let { this.danjiName = it }
        postCode?.let { this.postCode = it }
        latitude?.let { this.latitude = it.toBigDecimal() }
        longitude?.let { this.longitude = it.toBigDecimal() }
    }
}