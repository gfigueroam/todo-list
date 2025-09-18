package org.tradebyte.todolist.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.tradebyte.todolist.repository.ToDoItemRepository;
import org.tradebyte.todolist.repository.entity.Status;
import org.tradebyte.todolist.rest.dto.CreateItemDto;
import org.tradebyte.todolist.rest.dto.ItemDto;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class ToDoIntegrationTest {

    private static final String TODOS_URL = "/api/v1/todos";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ToDoItemRepository itemRepository;

    @BeforeEach
    public void setUp() {
        itemRepository.deleteAll();
    }

    @Test
    void createAndGetItem() {
        String description = "description";
        LocalDateTime dueDatetime = LocalDateTime.now().plusDays(1);
        CreateItemDto itemDto = CreateItemDto.builder()
                .description(description)
                .dueDatetime(dueDatetime)
                .build();

        ResponseEntity<ItemDto> itemCreated = restTemplate.postForEntity(TODOS_URL, itemDto, ItemDto.class);

        assertThat(itemCreated).isNotNull();
        assertThat(itemCreated.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertNotNull(itemCreated.getBody());
        assertThat(itemCreated.getBody().getDescription()).isEqualTo(description);
        assertThat(itemCreated.getBody().getDueDatetime()).isEqualTo(dueDatetime);
        assertThat(itemCreated.getBody().getStatus()).isEqualTo(Status.NOT_DONE);
        assertThat(itemCreated.getBody().getId()).isNotNull();
        assertThat(itemCreated.getBody().getCreationDatetime()).isNotNull();

    }

    @Test
    void getTodoItem() {
        UUID id = createTodoItemForTesting(null);

        ResponseEntity<ItemDto> itemCreated = restTemplate.getForEntity(TODOS_URL + "/" + id, ItemDto.class);

        assertThat(itemCreated).isNotNull();
        assertThat(itemCreated.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void getTodoItem_itemNotFound() {
        UUID id = UUID.randomUUID();

        ResponseEntity<ItemDto> itemCreated = restTemplate.getForEntity(TODOS_URL + "/" + id, ItemDto.class);

        assertThat(itemCreated).isNotNull();
        assertThat(itemCreated.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    private UUID createTodoItemForTesting(LocalDateTime dueDatetime) {

        String description = "description";
        dueDatetime = dueDatetime == null ? LocalDateTime.now().plusDays(1) : dueDatetime;
        CreateItemDto itemDto = CreateItemDto.builder()
                .description(description)
                .dueDatetime(dueDatetime)
                .build();

        ResponseEntity<ItemDto> itemCreated = restTemplate.postForEntity(TODOS_URL, itemDto, ItemDto.class);

        assertNotNull(itemCreated.getBody());
        return itemCreated.getBody().getId();
    }

    //Todo:
    // Integration test for patchItem
    // - correctly patch status and description
    // - pass due item should throw a HttpStatus.CONFLICT, verify no changes where saved
    // Integration test for getItems
    // - get all the items
    // - get items filtering by a single status
    // NOTE: I would consider to use a framework for integration test, for example restassure


}
