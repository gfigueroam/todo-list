package org.tradebyte.todolist.scheduler;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ConditionalOnBooleanProperty(value = "scheduler.enabled")
@ComponentScan("org.tradebyte.todolist.scheduler")
public class SchedulerConfig {


}
