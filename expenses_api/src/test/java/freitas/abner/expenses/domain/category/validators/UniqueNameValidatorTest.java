package freitas.abner.expenses.domain.category.validators;

import freitas.abner.expenses.domain.category.CategoryRepository;
import freitas.abner.expenses.domain.category.dtos.CreateOrUpdateCategoryData;
import freitas.abner.expenses.domain.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UniqueNameValidatorTest {

    @InjectMocks
    private UniqueNameValidator validator;
    @Mock
    private CategoryRepository repository;
    @Mock
    CreateOrUpdateCategoryData dto;
    @Mock
    private User user;

    @Test
    @DisplayName("Shouldn't throw Exception when receive a valid category name.")
    void shouldNotThrowExceptionWhenReceiveValidName() {
        BDDMockito.given(dto.name()).willReturn("Alimentação");
        BDDMockito.given(user.getId()).willReturn(0L);
        BDDMockito.given(repository.existsByNameAndUserId(dto.name(), user.getId())).willReturn(false);
        Assertions.assertDoesNotThrow(() -> validator.validate(dto, user));
    }

    @Test
    @DisplayName("Should throw Exception when receive a not valid category name.")
    void shouldThrowExceptionWhenReceiveNotValidName() {
        BDDMockito.given(dto.name()).willReturn("Alimentação");
        BDDMockito.given(user.getId()).willReturn(0L);
        BDDMockito.given(repository.existsByNameAndUserId(dto.name(), user.getId())).willReturn(true);
        Assertions.assertThrows(RuntimeException.class, () -> validator.validate(dto, user));
    }
}