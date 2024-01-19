package freitas.abner.expenses.domain.category;

import freitas.abner.expenses.domain.category.dtos.CreateOrUpdateCategoryData;
import freitas.abner.expenses.domain.category.validators.CategoryValidator;
import freitas.abner.expenses.domain.category.validators.UniqueNameValidator;
import freitas.abner.expenses.domain.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @InjectMocks
    CategoryService categoryService;
    @Mock
    CategoryRepository repository;
    @Spy
    List<CategoryValidator> validators = new ArrayList<>();
    @Mock
    private UniqueNameValidator uniqueNameValidator;
    @Captor
    private ArgumentCaptor<Category> categoryCaptor;

    @Test
    @DisplayName("Should save a new Category instance on repository.")
    void shouldSaveCategoryOnRepository() {
        CreateOrUpdateCategoryData dto = new CreateOrUpdateCategoryData("Alimentação");
        User user = new User(
                0L,
                "testeuser",
                "testeuser@emai.com",
                "12345678"
        );
        categoryService.createNewCategory(dto, user);
        BDDMockito.then(repository).should().save(categoryCaptor.capture());
        Category categorySaved = categoryCaptor.getValue();
        Assertions.assertEquals(dto.name(), categorySaved.getName());
        Assertions.assertEquals(user, categorySaved.getUser());
    }

    @Test
    @DisplayName("Should call Category validators.")
    void shouldCallCategoryValidators() {
        CreateOrUpdateCategoryData dto = new CreateOrUpdateCategoryData("Alimentação");
        User user = new User(
                0L,
                "testeuser",
                "testeuser@emai.com",
                "12345678"
        );
        validators.add(uniqueNameValidator);
        categoryService.createNewCategory(dto, user);
        BDDMockito.then(uniqueNameValidator).should().validate(dto, user);
    }

}