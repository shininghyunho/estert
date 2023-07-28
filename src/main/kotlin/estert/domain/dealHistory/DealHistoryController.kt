package estert.domain.dealHistory

import estert.common.CustomResponseEntity
import estert.domain.dealHistory.dto.filter.DealHistoryFilterRequest
import estert.domain.dealHistory.dto.filter.DealHistoryFilterResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1/deal-history")
@RestController
class DealHistoryController(
    private val dealHistoryService: DealHistoryService
) {
    // filter
    @GetMapping("/filter")
    fun filter(@RequestBody request: DealHistoryFilterRequest): ResponseEntity<CustomResponseEntity.Body> {
        return CustomResponseEntity(
            status = HttpStatus.OK,
            data = dealHistoryService.filter(request),
        ).toResponseEntity()
    }

    // filter-test
    @GetMapping("/filter-test")
    fun filterTest(@RequestBody request: DealHistoryFilterRequest): ResponseEntity<CustomResponseEntity.Body> {
        return CustomResponseEntity(
            status = HttpStatus.OK,
            data = dealHistoryService.filterStub(request),
        ).toResponseEntity()
    }
}