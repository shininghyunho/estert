package estert.domain.deal

import estert.domain.house_detail.HouseDetail
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Deal(
    cost: Long,
    dealDate: LocalDateTime,
    houseDetail: HouseDetail
) {
    init {
        setHouseDetailRelation(houseDetail)
    }
    var cost: Long = cost
        private set
    var dealDate: LocalDateTime = dealDate
        private set
    @ManyToOne
    @JoinColumn(name = "house_detail_id")
    var houseDetail: HouseDetail = houseDetail
        private set
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    // houseDetail relation
    private fun setHouseDetailRelation(houseDetail: HouseDetail) {
        // 기존 연관관계 제거
        this.houseDetail?.deals?.remove(this)
        // 새로운 연관관계 설정
        this.houseDetail = houseDetail
        houseDetail.deals.add(this)
    }
}