package freitas.abner.expenses.domain.expense;

import freitas.abner.expenses.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findAllByDatetimeBetweenAndUserId(LocalDateTime startDateTime, LocalDateTime endDateTime, Long userId);

    Page<Expense> findAllByUserId(Long userId, Pageable pageable);

    Expense findByDescriptionAndUserId(String description, Long userId);
}
