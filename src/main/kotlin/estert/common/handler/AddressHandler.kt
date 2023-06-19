package estert.common.handler

import estert.domain.house.dto.HouseSaveRequest
import estert.domain.api.molitApart.dto.MolitApart
import estert.domain.api.kakao.KakaoApiHandler
import estert.domain.api.vworld.VWorldApiHandler
import org.springframework.stereotype.Component

@Component
class AddressHandler(
    private val vWorldApiHandler: VWorldApiHandler,
    private val kakaoApiHandler: KakaoApiHandler
) {
    fun makeHouseSaveRequest(validMolitApart: MolitApart.Validated): HouseSaveRequest {
        // vworld api 를 통해 roadAddress 를 만든다.
        val roadAddress = vWorldApiHandler.makeRoadAddress(validMolitApart)
        // kakao api 를 통해 kakaoAddress 를 만든다.
        val kakaoAddress = kakaoApiHandler.makeKakaoAddress(roadAddress)

        return HouseSaveRequest(
            jibunAddress = kakaoAddress.jibunAddress,
            roadAddress = kakaoAddress.roadAddress,
            danjiName = kakaoAddress.danjiName,
            postCode = kakaoAddress.postCode,
            latitude = kakaoAddress.latitude.toString(),
            longitude = kakaoAddress.longitude.toString(),
        )
    }
}