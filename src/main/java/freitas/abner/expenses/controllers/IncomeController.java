package freitas.abner.expenses.controllers;

import freitas.abner.expenses.domain.income.Income;
import freitas.abner.expenses.domain.income.IncomeRepository;
import freitas.abner.expenses.domain.income.ReadIncomeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import freitas.abner.expenses.domain.income.CreateIncomeData;

import java.util.List;

@RestController
@RequestMapping("incomes")
public class IncomeController {

    @Autowired
    private IncomeRepository repository;

    @PostMapping
    public void createIncome(@RequestBody @Valid CreateIncomeData incomeDto) {
        repository.save(new Income(incomeDto));
    }

    @GetMapping
    public List<ReadIncomeData> getIncomes() {
        return repository.findAll().stream().map(ReadIncomeData::new).toList();
    }
}
