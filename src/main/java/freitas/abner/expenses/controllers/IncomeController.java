package freitas.abner.expenses.controllers;

import freitas.abner.expenses.domain.income.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("incomes")
public class IncomeController {

    @Autowired
    private IncomeRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<ReadIncomeData> createIncome(
            @RequestBody @Valid CreateIncomeData incomeDto,
            UriComponentsBuilder uriBuilder
    ) {
        var income = new Income(incomeDto);
        repository.save(income);
        var uri = uriBuilder.path("/incomes/{id}").buildAndExpand(income.getId()).toUri();
        return ResponseEntity.created(uri).body(new ReadIncomeData(income));
    }

    @GetMapping
    public ResponseEntity<Page<ReadIncomeData>> getIncomes(
            @PageableDefault(size = 10, page = 0, sort = {"datetime"}) Pageable pageable
    ) {
        var page = repository.findAll(pageable).map(ReadIncomeData::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadIncomeData> getIncome(@PathVariable Long id) {
        var income = repository.getReferenceById(id);
        return ResponseEntity.ok(new ReadIncomeData(income));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<ReadIncomeData> updateIncome(@RequestBody @Valid UpdateIncomeData incomeDto) {
        var income = repository.getReferenceById(incomeDto.id());
        income.update(incomeDto);
        return ResponseEntity.ok(new ReadIncomeData(income));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteIncome(@PathVariable Long id) {
        var income = repository.getReferenceById(id);
        repository.delete(income);
        return ResponseEntity.noContent().build();
    }
}
