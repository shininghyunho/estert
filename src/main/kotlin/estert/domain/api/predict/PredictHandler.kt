package estert.domain.api.predict

import com.google.gson.Gson
import estert.common.handler.HttpHandler
import estert.domain.api.predict.dto.PredictRequest
import estert.domain.api.predict.dto.PredictResponse
import org.springframework.stereotype.Component
import java.net.URL

@Component
class PredictHandler(
    val gson : Gson
){
    fun getPredictList(request: PredictRequest): PredictResponse {
        val httpResponse = try {
            val url = makePredictUrl(request)
            HttpHandler.get(url)
        } catch (e: Exception) {
            throw Exception("예측 API 요청 실패")
        }

        val predictResponse = try {
            makePredictResponse(httpResponse)
        } catch (e: Exception) {
            throw Exception("예측 API 응답 변환 실패")
        }

        return predictResponse
    }

    private fun makePredictUrl(request: PredictRequest): URL {
        val baseUrl=StringBuilder("http://localhost:5000/api/v1")
        // add query
        baseUrl.append("?time=${request.time}")
        baseUrl.append("&latitude=${request.latitude}")
        baseUrl.append("&longitude=${request.longitude}")
        return URL(baseUrl.toString())
    }

    private fun makePredictResponse(httpResponse: String) : PredictResponse {
        // to predictHouseList
        val predictHouseList = gson.fromJson(httpResponse, Array<PredictResponse.PredictHouse>::class.java).toList()
        return PredictResponse(predictHouseList)
    }
}