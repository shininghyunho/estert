package estert.domain.api.kakao

import com.google.gson.Gson
import com.google.gson.JsonObject
import estert.domain.api.kakao.config.KakaoProperty
import estert.common.handler.HttpHandler
import estert.domain.api.kakao.dto.KakaoAddress
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.net.URL
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

// 문자열
private val ROAD_ADDRESS = "road_address"
private val ADDRESS = "address"
private val ADDRESS_NAME = "address_name"
private val B_CODE = "b_code"
private val BUILDING_NAME = "building_name"
private val ZONE_NO = "zone_no"
private val Y = "y"
private val X = "x"

@Component
class KakaoApiHandler(
    private val gson: Gson,
    private val kakaoProperty: KakaoProperty
) {
    // make Kakao Address
    fun makeKakaoAddress(roadAddress: String): KakaoAddress {
        // headers
        val headers = HashMap<String, String>()
        val apiKey = kakaoProperty.apiKey
        headers["Authorization"] = "KakaoAK $apiKey"

        val urlSB = StringBuilder()
        val baseURL = "https://dapi.kakao.com/v2/local/search/address.json?"
        val query = URLEncoder.encode(roadAddress, StandardCharsets.UTF_8)

        urlSB.append(baseURL)
        urlSB.append("query=$query")

        // response
        val response = HttpHandler.get(URL(urlSB.toString()), headers)
        val jsonObj = gson.fromJson(response, JsonObject::class.java)
        val document = jsonObj.getAsJsonArray("documents")[0].asJsonObject

        return KakaoAddress(
            roadAddress = getRoadAddress(document),
            jibunAddress = getJibunAddress(document),
            hangCode = getHangCode(document),
            danjiName = getDanjiName(document),
            postCode = getPostCode(document),
            latitude = getLatitude(document),
            longitude = getLongitude(document)
        )
    }

    // document method
    private fun getRoadAddress(document: JsonObject): String = document.get(ROAD_ADDRESS).asJsonObject.get(ADDRESS_NAME).asString
    private fun getJibunAddress(document: JsonObject): String = document.get(ADDRESS).asJsonObject.get(ADDRESS_NAME).asString
    private fun getHangCode(document: JsonObject): String = document.get(ADDRESS).asJsonObject.get(B_CODE).asString
    private fun getDanjiName(document: JsonObject): String = document.get(ROAD_ADDRESS).asJsonObject.get(BUILDING_NAME).asString
    private fun getPostCode(document: JsonObject): String = document.get(ROAD_ADDRESS).asJsonObject.get(ZONE_NO).asString
    private fun getLatitude(document: JsonObject): BigDecimal = document.asJsonObject.get(Y).asBigDecimal
    private fun getLongitude(document: JsonObject): BigDecimal = document.asJsonObject.get(X).asBigDecimal
}