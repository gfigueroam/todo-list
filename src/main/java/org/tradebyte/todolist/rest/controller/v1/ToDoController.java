package org.tradebyte.todolist.rest.controller.v1;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tradebyte.todolist.rest.dto.CreateItemDto;
import org.tradebyte.todolist.rest.dto.ItemDto;
import org.tradebyte.todolist.rest.dto.PatchItemDto;
import org.tradebyte.todolist.rest.dto.QueryParams;
import org.tradebyte.todolist.service.ToDoService;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/todos")
public class ToDoController {

    private final ToDoService toDoServiceImpl;

    @PostMapping
    public ResponseEntity<ItemDto> createToDoItem(@Valid @RequestBody CreateItemDto itemDTO) {
        ItemDto itemDto = toDoServiceImpl.createItem(itemDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(itemDto);
    }

    @GetMapping
    public ResponseEntity<Page<ItemDto>> getToDoItems(@ModelAttribute QueryParams queryParams, Pageable pageable) {
        Page<ItemDto> items = toDoServiceImpl.getItems(queryParams, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDto> getToDoItem(@PathVariable UUID id) {
        ItemDto item = toDoServiceImpl.getItem(id);
        return ResponseEntity.status(HttpStatus.OK).body(item);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ItemDto> patchToDoItem(@PathVariable UUID id, @Valid @RequestBody PatchItemDto itemDto) {
        ItemDto item = toDoServiceImpl.patchItem(id, itemDto);
        return ResponseEntity.status(HttpStatus.OK).body(item);
    }
}
