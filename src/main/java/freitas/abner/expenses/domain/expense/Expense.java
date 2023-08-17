package freitas.abner.expenses.domain.expense;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "Expenses")
@Entity(name = "Expense")
@EqualsAndHashCode(of = "id")
public class Expense {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private BigDecimal amount;
    private LocalDateTime datetime;

    public Expense() {}

    public Expense(Long id, String description, BigDecimal amount, LocalDateTime datetime) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.datetime = datetime;
    }

    public Expense(CreateExpenseData expenseDto) {
        this.description = expenseDto.description();
        this.amount = expenseDto.amount();
        this.datetime = expenseDto.datetime();
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }
}
