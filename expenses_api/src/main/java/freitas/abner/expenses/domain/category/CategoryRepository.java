package freitas.abner.expenses.domain.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByNameAndUserId(String name, Long id);

    Page<Category> findAllByUserId(Long userId, Pageable pageable);

    Category findByNameAndUserId(String name, Long userId);
}
