package org.tradebyte.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tradebyte.todolist.repository.entity.Item;

import java.util.UUID;

public interface TodoItemRepository extends JpaRepository<Item, UUID> {

}
