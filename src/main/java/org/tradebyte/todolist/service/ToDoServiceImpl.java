package org.tradebyte.todolist.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tradebyte.todolist.repository.ToDoItemRepository;
import org.tradebyte.todolist.repository.entity.Item;
import org.tradebyte.todolist.repository.entity.Status;
import org.tradebyte.todolist.rest.advice.exception.EntityImmutableException;
import org.tradebyte.todolist.rest.dto.CreateItemDto;
import org.tradebyte.todolist.rest.dto.ItemDto;
import org.tradebyte.todolist.rest.dto.PatchItemDto;
import org.tradebyte.todolist.rest.dto.QueryParams;

import java.util.UUID;


@Service
@AllArgsConstructor
public class ToDoServiceImpl implements ToDoService {

    private static final Logger logger = LoggerFactory.getLogger(ToDoServiceImpl.class);

    private final ToDoItemRepository itemRepository;


    @Transactional
    @Override
    public ItemDto createItem(CreateItemDto itemDto) {
        Item item = itemDto.ToItem();
        return itemRepository.save(item).toDto();
    }

    @Override
    public Page<ItemDto> getItems(QueryParams params, Pageable pageable) {
        Item probe = Item.builder()
                .status(params != null ? params.status() : null)
                .creationDatetime(null)
                .build();

        Page<Item> items = itemRepository.findAll(Example.of(probe), pageable);
        return items.map(Item::toDto);
    }

    @Override
    public ItemDto getItem(UUID id) {
        Item item = getItemById(id);
        return item.toDto();
    }

    @Override
    public ItemDto patchItem(UUID id, @NotNull PatchItemDto itemDto) {
        Item item = getItemById(id);

        //Ensures that the item won't be updated if it is past-due.
        if (item.getStatus().equals(Status.PAST_DUE) || item.isPastDue()) {
            throw new EntityImmutableException("Can not update a past-due ToDo item.");
        }

        if (itemDto.getDescription() != null)
            item.setDescription(itemDto.getDescription());
        if (itemDto.getStatus() != null)
            item.setStatus(itemDto.getStatus());

        Item saved = itemRepository.save(item);
        return saved.toDto();
    }

    private Item getItemById(@NotNull UUID id) {
        //If instance with specific id was not found in the repository, throws an EntityNotFoundException
        return itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ToDo item not found"));
    }

}
