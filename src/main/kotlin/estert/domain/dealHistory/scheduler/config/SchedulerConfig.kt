package estert.domain.dealHistory.scheduler.config

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@ConditionalOnProperty(
    value = ["scheduling.enabled"],
    havingValue = "true",
    matchIfMissing = true
)
@Configuration
class SchedulerConfig