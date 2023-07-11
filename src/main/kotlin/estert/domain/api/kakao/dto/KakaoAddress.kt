package estert.domain.api.kakao.dto

import java.math.BigDecimal

class KakaoAddress(
    val jibunAddress: String,
    val roadAddress: String,
    val hangCode: String,
    val danjiName: String,
    val postCode: String,
    val latitude: BigDecimal,
    val longitude: BigDecimal,
)