package org.tradebyte.todolist.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.tradebyte.todolist.repository.ToDoItemRepository;
import org.tradebyte.todolist.scheduler.ItemStatusUpdateScheduler;
import org.tradebyte.todolist.scheduler.SchedulerConfig;

import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@SpringBootTest
@SpringJUnitConfig(SchedulerConfig.class)
@TestPropertySource(properties = {"scheduler.cron= */1 * * * * *", "scheduler.enabled = true"})
class ItemStatusUpdateSchedulerTest {

    @MockitoSpyBean
    private ItemStatusUpdateScheduler scheduler;

    @MockitoBean
    private ToDoItemRepository itemRepository;

    @Test
    public void whenWaitOneSecond_thenScheduledIsCalledAtLeastTenTimes() {
        await()
                .atMost(Duration.ofSeconds(2))
                .untilAsserted(() -> verify(scheduler, atLeastOnce()).updatePastDueItems());
    }

}