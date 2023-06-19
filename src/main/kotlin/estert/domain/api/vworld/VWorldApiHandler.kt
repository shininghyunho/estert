package estert.domain.api.vworld

import com.google.gson.Gson
import com.google.gson.JsonObject
import estert.common.handler.HttpHandler
import estert.domain.api.molitApart.dto.MolitApart
import estert.domain.api.vworld.config.VWorldProperty
import org.springframework.stereotype.Component
import java.lang.StringBuilder
import java.net.URL

@Component
class VWorldApiHandler(
    private val gson: Gson,
    private val vWorldProperty: VWorldProperty
) {
    fun makeRoadAddress(validMolitApart: MolitApart.Validated): String {
        // molitApart.roadNameCityCode 가 null 이면 exception 발생 후 return empty string
        val roadAddressCityName = makeRoadAddressCityName(validMolitApart.roadNameCityCode)
        if(roadAddressCityName.isEmpty()) throw Exception("roadAddressCityName result is empty")

        val roadName = validMolitApart.roadName
        // 도로명 빌딩 메인코드 + 도로명 빌딩 서브코드
        val roadNameBuildingMainCode = validMolitApart.roadNameBuildingMainCode
        val roadNameBuildingSubCode = validMolitApart.roadNameBuildingSubCode
        // roadNameCityName +" "+ roadNameBuildingMainCode +"_"+ roadNameBuildingSubCode
        // if roadNameBuildingSubCode is null or empty
        val roadAddressBuildingCode = if(roadNameBuildingSubCode.isEmpty()) {
            roadNameBuildingMainCode
        } else {
            "{$roadNameBuildingMainCode}_$roadNameBuildingSubCode"
        }

        return "$roadAddressCityName $roadName $roadAddressBuildingCode"
    }

    private fun makeRoadAddressCityName(adminDistCode: String): String {
        val baseURL = "http://api.vworld.kr/req/data"
        val service = "data"
        val request = "getFeature"
        val data = "LT_C_ADSIGG_INFO"
        val key = vWorldProperty.apiKey
        val domain = vWorldProperty.domain
        val attrFilter = "sig_cd:=:$adminDistCode"
        val geometry = "false"

        val strUrl = StringBuilder()
            .append(baseURL)
            .append("?service=$service")
            .append("&request=$request")
            .append("&data=$data")
            .append("&key=$key")
            .append("&domain=$domain")
            .append("&attrFilter=$attrFilter")
            .append("&geometry=$geometry")
            .toString()

        val httpResponse = HttpHandler.get(URL(strUrl))
        try {
            val jsonObj = gson.fromJson(httpResponse, JsonObject::class.java)
            return jsonObj.get("response")
                .asJsonObject.get("result")
                .asJsonObject.get("featureCollection")
                .asJsonObject.get("features")
                .asJsonArray.get(0)
                .asJsonObject.get("properties")
                .asJsonObject.get("full_nm")
                .asString
        } catch (e: Exception) {
            throw Exception("vworld api 호출 실패 : ${e.message}")
        }
    }
}