package org.tradebyte.todolist.rest.controller.v1;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tradebyte.todolist.rest.dto.CreateItemDto;
import org.tradebyte.todolist.rest.dto.ItemDto;
import org.tradebyte.todolist.rest.dto.PatchItemDto;
import org.tradebyte.todolist.service.TodoService;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/todos")
public class TodoController {

    private final TodoService todoServiceImpl;

    @PostMapping
    public ResponseEntity<ItemDto> createTodoItem(@Valid @RequestBody CreateItemDto itemDTO) {
        ItemDto itemDto = todoServiceImpl.createItem(itemDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(itemDto);
    }

    @GetMapping
    public ResponseEntity<Page<ItemDto>> getTodoItems(Pageable pageable) {
        Page<ItemDto> items = todoServiceImpl.getItems(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDto> getTodoItem(@PathVariable UUID id) {
        ItemDto item = todoServiceImpl.getItem(id);
        return ResponseEntity.status(HttpStatus.OK).body(item);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ItemDto> patchTodoItem(@PathVariable UUID id, @Valid @RequestBody PatchItemDto itemDto) {
        ItemDto item = todoServiceImpl.patchItem(id, itemDto);
        return ResponseEntity.status(HttpStatus.OK).body(item);
    }
}
