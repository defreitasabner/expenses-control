package freitas.abner.expenses.domain.expense;

import freitas.abner.expenses.domain.income.Income;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findAllByDatetimeBetweenAndUserId(LocalDateTime startDateTime, LocalDateTime endDateTime, Long userId);
}
