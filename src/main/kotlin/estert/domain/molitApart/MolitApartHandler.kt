package estert.domain.molitApart

import estert.domain.molitApart.config.MolitProperty
import estert.common.error.CustomException
import estert.domain.dealHistory.dto.DealHistorySaveRequest
import estert.common.handler.HttpHandler
import estert.domain.molitApart.dto.MolitApart
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.io.File
import javax.xml.parsers.SAXParser

private val log = LoggerFactory.getLogger(MolitApartHandler::class.java)

@Component
class MolitApartHandler (
    private val saxParser: SAXParser,
    private val molitApartXmlHandler: MolitApartXmlHandler,
    private val molitProperty : MolitProperty
) {
    // TODO : 오래걸리는 작업이므로 비동기로 처리해야 한다.
    fun getMolitApartList(request: DealHistorySaveRequest): List<MolitApart> {
        for(molitApiKey in molitProperty.apiKeyList) {
            try {
                val url = request.toUrl(molitApiKey)
                val httpResponse = HttpHandler.get(url)

                var xmlFile: File? = null
                try {
                    xmlFile = makeXmlFile(httpResponse)
                    // xml 파싱
                    saxParser.parse(xmlFile, molitApartXmlHandler)
                } catch (e: Exception) {
                    log.error("xml 파싱 실패 : ${e.message}")
                } finally {
                    // xml 파일 삭제
                    xmlFile?.delete()
                }

                return molitApartXmlHandler.molitApartList.get()
            } catch (e: Exception) {
                // 다음 api key 로 재시도
            }
        }

        // 모든 api key 로 실패
        throw CustomException(message = "유효한 molit api key 가 없습니다.")
    }

    private fun makeXmlFile(httpResponse: String): File {
        val xml = File.createTempFile("molitApart", ".xml")
        if(httpResponse.isNotEmpty()) xml.writeText(httpResponse)
        return xml
    }
}