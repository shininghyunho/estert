package estert.domain.molitApart.dto

import java.time.LocalDateTime

data class MolitApart(
    var roadName: String? = null,
    var roadNameCityCode: String? = null,
    var roadNameBuildingMainCode: String? = null,
    var roadNameBuildingSubCode: String? = null,
    var cost: String? = null,
    var dedicatedArea: String? = null,
    var dealYear: String? = null,
    var dealMonth: String? = null,
    var dealDay: String? = null,
) {
    // post process class
    class Validated (
        val roadName: String,
        val roadNameCityCode: String,
        val roadNameBuildingMainCode: String,
        val roadNameBuildingSubCode: String,
        val cost: Long,
        val dedicatedArea: String,
        val dealDate: String,
    )

    fun validate(): Validated {
        return Validated(
            roadName = roadName ?: throw IllegalArgumentException("roadName is null"),
            roadNameCityCode = roadNameCityCode ?: throw IllegalArgumentException("roadNameCityCode is null"),
            roadNameBuildingMainCode = roadNameBuildingMainCode ?: throw IllegalArgumentException("roadNameBuildingMainCode is null"),
            roadNameBuildingSubCode = roadNameBuildingSubCode ?: throw IllegalArgumentException("roadNameBuildingSubCode is null"),
            cost = cost?.replace(",", "")
                ?.replace(" ","")?.toLong() ?: throw IllegalArgumentException("cost is null"),
            dedicatedArea = dedicatedArea ?: throw IllegalArgumentException("dedicatedArea is null"),
            dealDate = LocalDateTime.of(
                dealYear?.toInt() ?: throw IllegalArgumentException("dealYear is null"),
                dealMonth?.toInt() ?: throw IllegalArgumentException("dealMonth is null"),
                dealDay?.toInt() ?: throw IllegalArgumentException("dealDay is null"),
                0, 0, 0).toString()
        )
    }
}