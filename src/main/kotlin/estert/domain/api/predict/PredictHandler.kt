package estert.domain.api.predict

import estert.domain.api.predict.dto.PredictRequest
import estert.domain.api.predict.dto.PredictResponse
import org.springframework.stereotype.Component

@Component
class PredictHandler {
    /**
     * return : 예측 결과 Map (key : houseId, value : 예상 시간)
     */
    fun getPredictMap(request: PredictRequest): Map<Long,Int> {
        // TODO : 예측 서버 API 호출
        return mapOf()
    }
}