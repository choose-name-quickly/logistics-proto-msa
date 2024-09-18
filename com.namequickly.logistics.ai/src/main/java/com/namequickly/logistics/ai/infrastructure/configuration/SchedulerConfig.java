package com.namequickly.logistics.ai.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class SchedulerConfig {
    @Bean
    public ThreadPoolTaskScheduler customTaskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(5); // 스레드 풀의 크기 설정
        taskScheduler.setThreadNamePrefix("Custom-Scheduler-"); // 스레드명 Prefix 설정
        return taskScheduler;
    }
}
