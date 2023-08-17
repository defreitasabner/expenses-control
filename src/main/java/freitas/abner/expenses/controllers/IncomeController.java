package freitas.abner.expenses.controllers;

import freitas.abner.expenses.domain.income.*;
import freitas.abner.expenses.exceptions.SameDescriptionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;

@RestController
@RequestMapping("incomes")
public class IncomeController {

    @Autowired
    private IncomeService service;

    @PostMapping
    @Transactional
    public ResponseEntity<ReadIncomeData> createIncome(
            @RequestBody @Valid CreateIncomeData incomeDto,
            UriComponentsBuilder uriBuilder
    ) throws SameDescriptionException {
        var income = service.registerNewIncome(incomeDto);
        var uri = uriBuilder.path("/incomes/{id}").buildAndExpand(income.getId()).toUri();
        return ResponseEntity.created(uri).body(new ReadIncomeData(income));
    }

    @GetMapping
    public ResponseEntity<Page<ReadIncomeData>> getIncomes(
            @PageableDefault(size = 10, page = 0, sort = {"datetime"}) Pageable pageable
    ) {
        var page = service.getAllIncomeDataPageable(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadIncomeData> getIncome(@PathVariable Long id) {
        var incomeDto = service.getIncomeDetail(id);
        return ResponseEntity.ok(incomeDto);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ReadIncomeData> updateIncome(
            @PathVariable Long id,
            @RequestBody @Valid UpdateIncomeData incomeDto
    ) throws SameDescriptionException {
        var income = service.updateIncome(id, incomeDto);
        return ResponseEntity.ok(new ReadIncomeData(income));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteIncome(@PathVariable Long id) {
        service.deleteIncome(id);
        return ResponseEntity.noContent().build();
    }
}
