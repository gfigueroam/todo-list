package org.tradebyte.todolist.rest.controller.v1;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class ToDoControllerTest {

    private static final String PATH_TO_JSON_FILES = "src/test/resources/json/";
    private static final String TODOS_URL = "/api/v1/todos";


    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @CsvSource(textBlock = """
            createitem_null_description.json, 400
            createitem_null_duedate.json, 400
            createitem_date_in_past.json,400
            createitem.json,201
            """)
    void createToDoItem(String fileName, int expectedStatus) throws Exception {
        String jsonContent = new String(
                Files.readAllBytes(
                        Paths.get(PATH_TO_JSON_FILES + fileName)), StandardCharsets.UTF_8);

        mockMvc.perform(post(TODOS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().is(expectedStatus));
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            ?status=NOT_DONE&none_existing=10, 200
            ?, 200
            """)
    void getToDoItems(String queryParams, int expectedStatus) throws Exception {
        mockMvc.perform(get(TODOS_URL + queryParams))
                .andExpect(status().is(expectedStatus));
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            patchitem_status_invalid.json, 400
            patchitem_null_description_and_status.json, 400
            """)
    void patchToDoItem(String fileName, int expectedStatus) throws Exception {
        UUID id = UUID.randomUUID();
        String jsonContent = new String(
                Files.readAllBytes(
                        Paths.get(PATH_TO_JSON_FILES + fileName)), StandardCharsets.UTF_8);

        mockMvc.perform(patch(TODOS_URL + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().is(expectedStatus));
    }
}
