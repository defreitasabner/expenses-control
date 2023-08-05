package freitas.abner.expenses.domain.income;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReadIncomeData(
        Long id,
        String description,
        BigDecimal amount,
        LocalDateTime datetime
) {
    public ReadIncomeData(Income income){
        this(income.getId(), income.getDescription(), income.getAmount(), income.getDatetime());
    }
}
