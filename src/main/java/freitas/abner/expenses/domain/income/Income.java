package freitas.abner.expenses.domain.income;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@Table(name = "Incomes")
@Entity(name = "Income")
@EqualsAndHashCode(of = "id")
public class Income {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private BigDecimal amount;
    private Date datetime;

    public Income(Long id, String description, BigDecimal amount, Date datetime) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.datetime = datetime;
    }

    public Income(CreateIncomeData incomeDto) {
        this.description = incomeDto.description();
        this.amount = incomeDto.amount();
        this.datetime = incomeDto.datetime();
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

    public Date getDatetime() {
        return datetime;
    }
}
