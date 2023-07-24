package estert.domain.deal

import estert.domain.deal.dto.DealFilterResponse
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.stream.Stream

interface DealRepository : JpaRepository<Deal, Long> {
    /**
     * TODO: Test 필요
     * @param houseIdList : 필터링할 houseId 리스트
     * @param lowCost : 필터링할 최저 가격
     * @param highCost : 필터링할 최고 가격
     *
     * @return : 필터링된 dealFilterResponse stream
     * */
    @Query("select new estert.domain.deal.dto.DealFilterResponse(d.houseDetail.house.id, d.id, d.houseDetail.house.latitude, d.houseDetail.house.longitude) "+
            "from Deal d "+
            "where d.cost >= :lowCost and d.cost <= :highCost "+
            "and d.houseDetail.house.id in :houseIdList")
    fun getDealFilterResponseStream(houseIdList: List<Long>, lowCost: Int, highCost: Int): Stream<DealFilterResponse>
}