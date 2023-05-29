package estert.domain.house_detail

import estert.domain.deal.Deal
import estert.domain.house.House
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
class HouseDetail(
    dedicatedArea: BigDecimal,
    house: House)
{
    init {
        setHouseRelation(house)
    }

    var dedicatedArea: BigDecimal = dedicatedArea
        private set

    @ManyToOne
    @JoinColumn(name = "house_id")
    var house: House = house
        private set

    @OneToMany(mappedBy = "houseDetail", cascade = [CascadeType.REMOVE])
    val deals: MutableSet<Deal> = hashSetOf()

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0


    // house relation
    private fun setHouseRelation(house: House) {
        // 기존 연관관계 제거
        this.house?.houseDetails?.remove(this)
        // 새로운 연관관계 설정
        this.house = house
        house.houseDetails.add(this)
    }
}