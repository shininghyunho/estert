package estert.domain.dealHistory

import estert.common.CustomResponseEntity
import estert.domain.dealHistory.dto.filter.DealHistoryFilterRequest
import estert.domain.dealHistory.dto.filter.DealHistoryFilterResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class DealHistoryController(
    private val dealHistoryService: DealHistoryService
) {
    // filter
    @GetMapping("/deal-history/filter")
    fun filter(@RequestBody request: DealHistoryFilterRequest): ResponseEntity<CustomResponseEntity.Body> {
        return CustomResponseEntity(
            status = HttpStatus.OK,
            data = dealHistoryService.filter(request),
        ).toResponseEntity()
    }
}