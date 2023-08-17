package freitas.abner.expenses.domain.income;

import freitas.abner.expenses.exceptions.SameDescriptionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepository repository;

    public Income registerNewIncome(CreateIncomeData incomeDto) throws SameDescriptionException {
        var income = new Income(incomeDto);
        var monthIncomes = getAllMonthIncomes(income.getDatetime());
        if(monthIncomes.stream().anyMatch(monthIncome -> monthIncome.getDescription().equals(income.getDescription())))
            throw new SameDescriptionException();
        repository.save(income);
        return income;
    }

    private List<Income> getAllMonthIncomes(LocalDateTime dateTime) {
        var firstDay = LocalDateTime.of(
                LocalDate.of(
                        dateTime.getYear(),
                        dateTime.getMonth(),
                        1),
                LocalTime.of(0, 0, 0)
        );
        var lastDay = firstDay.with(TemporalAdjusters.lastDayOfMonth());
        return repository.findAllByDatetimeBetween(firstDay, lastDay);
    }
}
