package org.tradebyte.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tradebyte.todolist.repository.entity.Item;
import org.tradebyte.todolist.repository.entity.Status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ToDoItemRepository extends JpaRepository<Item, UUID> {

    List<Item> findByStatusAndDueDatetimeBefore(Status status, LocalDateTime dateTime);
}
