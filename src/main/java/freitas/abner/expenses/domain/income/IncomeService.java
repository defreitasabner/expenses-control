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
        verifyDescription(income.getDescription(), monthIncomes);
        repository.save(income);
        return income;
    }

    public Income updateIncome(Long id, UpdateIncomeData incomeDto) throws SameDescriptionException{
        var income = repository.getReferenceById(id);
        var monthIncomes = getAllMonthIncomes(income.getDatetime());
        verifyDescription(incomeDto.description(), monthIncomes);
        income.update(incomeDto);
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

    private void verifyDescription(String description, List<Income> monthIncomes) throws SameDescriptionException{
        if(monthIncomes.stream().anyMatch(monthIncome -> monthIncome.getDescription().equals(description)))
            throw new SameDescriptionException();
    }
}
