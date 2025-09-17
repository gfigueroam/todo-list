package org.tradebyte.todolist.rest.controller.v1;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tradebyte.todolist.rest.dto.CreateItemDTO;
import org.tradebyte.todolist.rest.dto.ItemDTO;

@RestController
@RequestMapping("/api/v1/items")
public class ItemController {

    @PostMapping
    public ResponseEntity<ItemDTO> createItem(@Valid @RequestBody CreateItemDTO itemDTO){

        return null;
    }
}
