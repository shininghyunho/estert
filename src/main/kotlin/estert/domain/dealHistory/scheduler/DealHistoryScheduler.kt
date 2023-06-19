package estert.domain.dealHistory.scheduler

import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

private val log = LoggerFactory.getLogger(DealHistoryScheduler::class.java)
@Component
class DealHistoryScheduler(
    private val dealHistoryJob: Job,
    private val jobLauncher: JobLauncher
) {
    // 매월 1월 1일 00:00:00에 실행
    @Scheduled(cron = "0 0 0 1 1/1 ?")
    fun executeJob() {
        val jobParameters = JobParametersBuilder()
            .addLong("time", System.currentTimeMillis())
            .toJobParameters()
        try {
            jobLauncher.run(dealHistoryJob, jobParameters)
        } catch (e: Exception) {
            log.error("dealHistoryJob 실행 실패 : ${e.message}")
        }
    }
}