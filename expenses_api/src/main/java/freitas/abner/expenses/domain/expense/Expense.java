package freitas.abner.expenses.domain.expense;

import freitas.abner.expenses.domain.category.Category;
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
    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    public Expense() {}

    public Expense(
            Long id,
            String description,
            BigDecimal amount,
            LocalDateTime datetime,
            Category category
    ) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.datetime = datetime;
        this.category = category;
    }

    public Expense(CreateExpenseData expenseDto, Category category) {
        this.description = expenseDto.description();
        this.amount = expenseDto.amount();
        this.datetime = expenseDto.datetime();
        this.category = category;
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

    public Category getCategory() {
        return category;
    }

    public void update(UpdateExpenseData expenseDto) {
        if(expenseDto.amount() != null) this.amount = expenseDto.amount();
        if(expenseDto.description() != null) this.description = expenseDto.description();
        if(expenseDto.datetime() != null) this.datetime = expenseDto.datetime();
    }
}
