package org.tradebyte.todolist.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.tradebyte.todolist.rest.dto.CreateItemDto;
import org.tradebyte.todolist.rest.dto.ItemDto;
import org.tradebyte.todolist.rest.dto.PatchItemDto;

import java.util.UUID;

public interface TodoService {
    @Transactional
    ItemDto createItem(CreateItemDto itemDto);

    Page<ItemDto> getItems(Pageable pageable);

    ItemDto getItem(UUID id);

    ItemDto patchItem(UUID id, PatchItemDto itemDto);
}
