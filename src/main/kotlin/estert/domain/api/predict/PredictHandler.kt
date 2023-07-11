package estert.domain.api.predict

import estert.domain.api.predict.dto.PredictRequest
import estert.domain.api.predict.dto.PredictResponse
import org.springframework.stereotype.Component

@Component
class PredictHandler {
    fun getPredictList(request: PredictRequest): List<PredictResponse> {
        return listOf()
    }
}