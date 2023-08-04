package freitas.abner.expenses.controllers;

import freitas.abner.expenses.domain.income.Income;
import freitas.abner.expenses.domain.income.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import freitas.abner.expenses.domain.income.CreateIncomeData;

@RestController
@RequestMapping("ganhos")
public class IncomeController {

    @Autowired
    private IncomeRepository repository;

    @PostMapping
    public void createIncome(@RequestBody CreateIncomeData incomeDto) {
        repository.save(new Income(incomeDto));
    }
}
