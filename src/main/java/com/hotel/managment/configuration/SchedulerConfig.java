package com.hotel.managment.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * This configuration class, {@code SchedulerConfig}, is used to customize the task scheduling
 * behavior in the application. It implements the {@code SchedulingConfigurer} interface to provide
 * configuration for the task scheduler. It sets up a thread pool for scheduled tasks with a
 * specified pool size and thread name prefix.
 */
@Configuration
public class SchedulerConfig implements SchedulingConfigurer {

  private static final int POOL_SIZE = 10;

  @Override
  public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
    ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();

    threadPoolTaskScheduler.setPoolSize(POOL_SIZE);
    threadPoolTaskScheduler.setThreadNamePrefix("my-scheduled-task-pool-");
    threadPoolTaskScheduler.initialize();

    scheduledTaskRegistrar.setTaskScheduler(threadPoolTaskScheduler);
  }
}
