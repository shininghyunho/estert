package estert.domain.deal.dto

import estert.domain.deal.Deal

class DealGetResponse(
    val id: Long,
    val cost: Long,
    val dealDate: String
) {
    companion object {
        fun from(deal: Deal): DealGetResponse {
            return DealGetResponse(
                id = deal.id,
                cost = deal.cost,
                dealDate = deal.dealDate.toString()
            )
        }
    }
}