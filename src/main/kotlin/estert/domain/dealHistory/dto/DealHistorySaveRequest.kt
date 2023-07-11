package estert.domain.dealHistory.dto

import java.net.URL

private val molitApartUrl="http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev?"
class DealHistorySaveRequest (
    val pageNo: Int, // 페이지 번호
    val perPage: Int, // 한 페이지 결과 수
    val lawdCd: String, // 법정동코드
    val dealYmd: String // 년월
) {
    fun toUrl(serviceKey: String): URL {
        val url = StringBuilder()
        url.append(molitApartUrl)
        url.append("pageNo=$pageNo")
        url.append("&")
        url.append("numOfRows=$perPage")
        url.append("&")
        url.append("LAWD_CD=$lawdCd")
        url.append("&")
        url.append("DEAL_YMD=$dealYmd")
        url.append("&")
        url.append("serviceKey=$serviceKey")
        return URL(url.toString())
    }
}