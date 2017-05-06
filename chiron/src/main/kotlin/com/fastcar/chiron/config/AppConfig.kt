package com.fastcar.chiron.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@Configuration
@EnableScheduling
class SpringConfiguration {

    @Bean(destroyMethod = "shutdown")
    fun taskScheduler(): Executor
    {
        return Executors.newScheduledThreadPool(5);
    }
}