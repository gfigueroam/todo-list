package org.tradebyte.todolist.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.tradebyte.todolist.repository.ToDoItemRepository;
import org.tradebyte.todolist.repository.entity.Item;
import org.tradebyte.todolist.repository.entity.Status;
import org.tradebyte.todolist.rest.advice.exception.EntityImmutableException;
import org.tradebyte.todolist.rest.dto.CreateItemDto;
import org.tradebyte.todolist.rest.dto.ItemDto;
import org.tradebyte.todolist.rest.dto.PatchItemDto;
import org.tradebyte.todolist.rest.dto.QueryParams;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ToDoServiceImplTest {

    @Mock
    ToDoItemRepository itemRepository;

    @InjectMocks
    ToDoServiceImpl toDoService;


    @Test
    void createItem_ItemDtoIsCorrectlyMappedAndDefaultValuesAreSet() {
        String description = "Testing the code";
        LocalDateTime dueDatetime = LocalDateTime.now();
        CreateItemDto itemDto = new CreateItemDto(description, dueDatetime);

        when(itemRepository.save(any(Item.class))).thenAnswer(i -> i.getArgument(0));

        ItemDto itemCreated = toDoService.createItem(itemDto);

        assertEquals(description, itemCreated.getDescription());
        assertEquals(dueDatetime, itemCreated.getDueDatetime());
        assertEquals(Status.NOT_DONE, itemCreated.getStatus());
        assertNotNull(itemCreated.getCreationDatetime());
    }

    @Test
    void getItems_() {

        Item item1 = Item.builder().description("test1").build();
        Item item2 = Item.builder().description("test2").build();
        QueryParams queryParams = new QueryParams(Status.DONE);
        Page<Item> items = new PageImpl<>(Arrays.asList(item1, item2));
        Pageable pageable = PageRequest.of(0, 10);

        when(itemRepository.findAll(any(Example.class), any(Pageable.class))).thenReturn(items);

        Page<ItemDto> itemDtos = toDoService.getItems(queryParams, pageable);

        assertEquals(2, itemDtos.getTotalElements());
    }

    @Test
    void getItem_throwsException_whenItemNotFound() {
        UUID id = UUID.randomUUID();
        when(itemRepository.findById(any())).thenReturn(Optional.empty());
        Exception e = assertThrows(EntityNotFoundException.class, () -> toDoService.getItem(id));
        verify(itemRepository).findById(any());
        assertEquals("ToDo item not found", e.getMessage());
    }

    @Test
    void patchItem_throwsException_whenItemPastDueAndStatusNotDone() {
        UUID id = UUID.randomUUID();
        LocalDateTime pastDatetime = LocalDateTime.now().minusDays(1);
        Item item = Item.builder()
                .dueDatetime(pastDatetime)
                .status(Status.NOT_DONE)
                .build();

        when(itemRepository.findById(any())).thenReturn(Optional.of(item));

        Exception e = assertThrows(EntityImmutableException.class, () -> toDoService.patchItem(id, PatchItemDto.builder().build()));

        verify(itemRepository, never()).save(item);
        assertEquals("Can not update a past-due ToDo item.", e.getMessage());
    }

    @Test
    void patchItem_throwsException_whenStatusPastDue() {
        UUID id = UUID.randomUUID();
        Item item = Item.builder()
                .status(Status.PAST_DUE)
                .build();

        when(itemRepository.findById(any())).thenReturn(Optional.of(item));

        Exception e = assertThrows(EntityImmutableException.class, () -> toDoService.patchItem(id, PatchItemDto.builder().build()));

        verify(itemRepository, never()).save(item);
        assertEquals("Can not update a past-due ToDo item.", e.getMessage());
    }

    @Test
    void patchItem_updatesDescriptionAndStatusAndDoneDate_whenCorrect() {
        UUID id = UUID.randomUUID();
        String description = "TODO item";
        PatchItemDto patchItemDto = PatchItemDto.builder()
                .description(description)
                .status(Status.DONE)
                .build();

        Item item = Item.builder()
                .dueDatetime(LocalDateTime.now().plusDays(1))
                .build();

        when(itemRepository.findById(any())).thenReturn(Optional.of(item));
        when(itemRepository.save(any())).thenReturn(item);

        ItemDto itemPatched = toDoService.patchItem(id, patchItemDto);

        verify(itemRepository).save(item);

        assertEquals(description, itemPatched.getDescription());
        assertEquals(Status.DONE, itemPatched.getStatus());
        //Done date is updated when the status changed to DONE
        assertNotNull(itemPatched.getDoneDatetime());


    }

    @Test
    void patchItem_updatesDoneDatetime_whenStatusChangeToDoneAndNotDone() {
        UUID id = UUID.randomUUID();
        PatchItemDto patchItemDto = PatchItemDto.builder()
                .description("My first TODO item")
                .status(Status.NOT_DONE)
                .build();

        Item item = Item.builder()
                .dueDatetime(LocalDateTime.now().plusDays(1))
                .doneDatetime(LocalDateTime.now())
                .status(Status.DONE)
                .build();

        when(itemRepository.findById(any())).thenReturn(Optional.of(item));
        when(itemRepository.save(any())).thenReturn(item);

        ItemDto itemPatched = toDoService.patchItem(id, patchItemDto);

        verify(itemRepository).save(item);

        //Done date is set to null when the status changed to NOT_DONE
        assertNull(itemPatched.getDoneDatetime());
        assertEquals(Status.NOT_DONE, itemPatched.getStatus());
    }

}