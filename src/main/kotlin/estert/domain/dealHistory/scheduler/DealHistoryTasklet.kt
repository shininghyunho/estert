package estert.domain.dealHistory.scheduler

import estert.domain.dealHistory.DealHistoryService
import estert.domain.dealHistory.dto.DealHistorySaveRequest
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.time.LocalDateTime

private val log = org.slf4j.LoggerFactory.getLogger(DealHistoryTasklet::class.java)
@Component
class DealHistoryTasklet(
    private val dealHistoryService: DealHistoryService
) : Tasklet {
    override fun execute(contribution: StepContribution,chunkContext: ChunkContext) : RepeatStatus {
        // 현재 년월
        val nowYearMonth = makeNowYearMonth()

        getLawdCdList().stream().forEach {lawdCd ->
            try {
                saveDealHistory(
                    perPage = 1000,
                    lawdCd = lawdCd,
                    dealYmd = nowYearMonth
                )
            } catch (e: Exception) {
                log.error("거래내역 저장 실패 lawdCd:${lawdCd}, error:${e.message}")
            }
        }

        return RepeatStatus.FINISHED
    }

    fun saveDealHistory(perPage: Int, lawdCd: String, dealYmd: String) {
        var pageNo = 1
        var totalCount = perPage+1
        while(totalCount > (pageNo * perPage)) {
            totalCount = dealHistoryService.save(DealHistorySaveRequest(
                perPage = perPage,
                pageNo = pageNo,
                lawdCd = lawdCd,
                dealYmd = dealYmd
            ))
            pageNo++
        }
    }

    fun getLawdCdList() : List<String> {
        val lawdCdList = mutableListOf<String>()
        ClassPathResource("LAWD_CD.txt").inputStream.bufferedReader().forEachLine {
            lawdCdList.add(it)
        }
        return lawdCdList
    }

    fun makeNowYearMonth() : String {
        val now = LocalDateTime.now()
        val year = now.year.toString()
        val month = if (now.monthValue < 10) "0${now.monthValue}" else now.monthValue.toString()
        return "$year$month"
    }
}