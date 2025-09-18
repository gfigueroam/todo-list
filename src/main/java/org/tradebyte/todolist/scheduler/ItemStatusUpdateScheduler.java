package org.tradebyte.todolist.scheduler;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.tradebyte.todolist.repository.ToDoItemRepository;
import org.tradebyte.todolist.repository.entity.Item;
import org.tradebyte.todolist.repository.entity.Status;

import java.time.LocalDateTime;
import java.util.List;

@Component
@AllArgsConstructor
public class ItemStatusUpdateScheduler {

    //TODO: Spring Scheduler @Schedule do not work well with a cluster of instance,
    // the implementation of lock is necessary to ensure that the task runs only once.
    // For example: ShedLock
    // Depending of the amount of items to update, a distributed scheduler could be considered JobRunr, DB-Scheduler.

    private static final Logger logger = LoggerFactory.getLogger(ItemStatusUpdateScheduler.class);

    private final ToDoItemRepository itemRepository;

    @Scheduled(cron = "${scheduler.cron}")
    public void updatePastDueItems() {
        System.out.println("scheduler" + LocalDateTime.now());
        LocalDateTime now = LocalDateTime.now();
        List<Item> overdueItems = itemRepository.findByStatusAndDueDatetimeBefore(Status.NOT_DONE, now);
        for (Item item : overdueItems) {
            item.setStatus(Status.PAST_DUE);
            try {
                itemRepository.save(item);
            } catch (Exception e) {
                logger.error("Scheduler failed: Item with id {} status update failed with error", item.getId(), e.getCause());
            }
        }
    }

}
