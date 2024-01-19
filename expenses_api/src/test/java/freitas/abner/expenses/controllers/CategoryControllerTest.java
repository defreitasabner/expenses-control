package freitas.abner.expenses.controllers;

import freitas.abner.expenses.domain.category.Category;
import freitas.abner.expenses.domain.category.CategoryService;
import freitas.abner.expenses.domain.category.dtos.CreateOrUpdateCategoryData;
import freitas.abner.expenses.domain.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private CategoryService categoryService;
    @Autowired
    private JacksonTester<CreateOrUpdateCategoryData> jsonDto;
    @MockBean
    private Category category;

    @Test
    @DisplayName("Should return status code 400 when request body is not valid.")
    @WithMockUser
    void shouldReturn400WhenRequestBodyIsNotValid() throws Exception {
        String json = "{}";
        var response = mvc.perform(
                post("/categories")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    @DisplayName("Should return status code 201 when request body is valid.")
    @WithMockUser
    void shouldReturn201WhenRequestBodyIsValid() throws Exception {
        CreateOrUpdateCategoryData dto = new CreateOrUpdateCategoryData("Alimentação");
        BDDMockito.given(categoryService.createNewCategory(any(), any())).willReturn(category);
        BDDMockito.given(category.getId()).willReturn(1L);
        var response = mvc.perform(
                post("/categories")
                        .content(jsonDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();
        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

}