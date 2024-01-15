package freitas.abner.expenses.controllers;

import freitas.abner.expenses.domain.expense.*;
import freitas.abner.expenses.domain.user.User;
import freitas.abner.expenses.exceptions.InvalidCategoryException;
import freitas.abner.expenses.exceptions.SameDescriptionException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("expenses")
@SecurityRequirement(name = "bearer-key")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    @Transactional
    public ResponseEntity<ReadExpenseData> createExpense(
            @RequestBody @Valid CreateExpenseData expenseDto,
            UriComponentsBuilder uriBuilder
    ) throws SameDescriptionException, InvalidCategoryException
    {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var expense = expenseService.registerNewExpense(expenseDto, user);
        var uri = uriBuilder.path("/expenses/{id}").buildAndExpand(expense.getId()).toUri();
        return ResponseEntity.created(uri).body(new ReadExpenseData(expense));
    }

    @GetMapping
    public ResponseEntity<Page<ReadExpenseData>> getExpenses(
            @PageableDefault(size = 10, page = 0, sort = {"datetime"}) Pageable pageable
    )
    {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var page = expenseService.getAllExpenseDataPageable(pageable, user);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadExpenseData> getExpense(@PathVariable Long id)
    {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var expenseDto = expenseService.getExpenseDetail(id, user);
        return ResponseEntity.ok(expenseDto);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<ReadExpenseData> updateExpense(
            @RequestBody @Valid UpdateExpenseData expenseDto
    ) throws SameDescriptionException
    {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var expense = expenseService.updateExpense(expenseDto, user);
        return ResponseEntity.ok(expense);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<Void> deleteExpense(
            @RequestBody DeleteExpenseData deleteExpenseData
    )
    {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        expenseService.deleteExpense(deleteExpenseData, user);
        return ResponseEntity.noContent().build();
    }
}
