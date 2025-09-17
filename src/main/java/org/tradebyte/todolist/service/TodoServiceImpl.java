package org.tradebyte.todolist.service;


import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tradebyte.todolist.repository.TodoItemRepository;
import org.tradebyte.todolist.repository.entity.Item;
import org.tradebyte.todolist.rest.dto.CreateItemDto;
import org.tradebyte.todolist.rest.dto.ItemDto;
import org.tradebyte.todolist.rest.dto.PatchItemDto;

import java.util.UUID;

@AllArgsConstructor
@Service
public class TodoServiceImpl implements TodoService {

    private final TodoItemRepository itemRepository;
    private final ModelMapper mapper;

    @Transactional
    @Override
    public ItemDto createItem(CreateItemDto itemDto) {
        //mapping from CreateItemDto to Item entity
        Item item = mapper.map(itemDto, Item.class);

        item = itemRepository.save(item);

        //map back to Item entity to ItemDTO
        return mapper.map(item, ItemDto.class);
    }

    @Override
    public Page<ItemDto> getItems(Pageable pageable) {
        return itemRepository.findAll(pageable)
                .map(item -> mapper.map(item, ItemDto.class));
    }

    @Override
    public ItemDto getItem(UUID id) {
        Item item = getItemById(id);
        return mapper.map(item, ItemDto.class);
    }

    @Override
    public ItemDto patchItem(UUID id, PatchItemDto itemDto) {
        Item item = getItemById(id);
        if (itemDto.getDescription() != null)
            item.setDescription(itemDto.getDescription());
        if (itemDto.getStatus() != null)
            item.setStatus(itemDto.getStatus());
        Item saved = itemRepository.save(item);
        return mapper.map(saved, ItemDto.class);
    }

    private Item getItemById(UUID id) {
        //If instance with specific id was not found in the repository,
        // throw an EntityNotFoundException
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item not found"));
        return item;
    }

}
