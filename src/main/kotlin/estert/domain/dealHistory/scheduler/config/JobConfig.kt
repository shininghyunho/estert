package estert.domain.dealHistory.scheduler.config

import estert.domain.dealHistory.scheduler.DealHistoryTasklet
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
@EnableBatchProcessing
class JobConfig {
    @Autowired
    lateinit var jobRepository: JobRepository
    @Autowired
    lateinit var dealHistoryTasklet: DealHistoryTasklet
    @Autowired
    lateinit var transactionManager: PlatformTransactionManager

    @Bean
    fun dealHistoryJob() : Job = JobBuilder("dealHistoryJob", jobRepository)
        .start(dealHistoryStep())
        .build()

    @Bean
    fun dealHistoryStep() : Step = StepBuilder("dealHistoryStep", jobRepository)
        .tasklet(dealHistoryTasklet,transactionManager)
        .build()
}