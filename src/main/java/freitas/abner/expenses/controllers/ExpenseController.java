package freitas.abner.expenses.controllers;

import freitas.abner.expenses.domain.expense.CreateExpenseData;
import freitas.abner.expenses.domain.expense.ReadExpenseData;
import freitas.abner.expenses.exceptions.SameDescriptionException;
import freitas.abner.expenses.services.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("expenses")
public class ExpenseController {
    @Autowired
    ExpenseService service;

    @PostMapping
    public ResponseEntity<ReadExpenseData> createExpense(
            @RequestBody @Valid CreateExpenseData expenseDto,
            UriComponentsBuilder uriBuilder
    ) throws SameDescriptionException {
        var expense = service.registerNewExpense(expenseDto);
        var uri = uriBuilder.path("/expenses/{id}").buildAndExpand(expense.getId()).toUri();
        return ResponseEntity.created(uri).body(new ReadExpenseData(expense));
    }
}
