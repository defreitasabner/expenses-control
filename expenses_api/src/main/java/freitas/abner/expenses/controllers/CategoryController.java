package freitas.abner.expenses.controllers;

import freitas.abner.expenses.domain.category.CategoryRepository;
import freitas.abner.expenses.domain.category.CategoryService;
import freitas.abner.expenses.domain.category.CreateCategoryData;
import freitas.abner.expenses.domain.category.ReadCategoryData;
import freitas.abner.expenses.domain.user.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("categories")
@SecurityRequirement(name = "bearer-key")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    @Transactional
    ResponseEntity<ReadCategoryData> createCategory(
            @RequestBody @Valid CreateCategoryData createCategoryData,
            UriComponentsBuilder uriBuilder
    )
    {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var category = categoryService.createNewCategory(createCategoryData, user);
        var uri = uriBuilder.path("/categories/{id}").buildAndExpand(category.getId()).toUri();
        return ResponseEntity.created(uri).body(new ReadCategoryData(category));
    }

    @GetMapping
    ResponseEntity<Page<ReadCategoryData>> getCategories(
            @PageableDefault(size = 10, page = 0, sort = {"name"}) Pageable pageable
    )
    {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var page = categoryService.getAllCategoriesByUser(pageable, user);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    ResponseEntity<ReadCategoryData> getCategory(@PathVariable Long id)
    {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var category = categoryService.getCategoryById(id);
        if (category.getUser() != user) {
            throw new RuntimeException("You couldn't check another user category.");
        }
        return ResponseEntity.ok(new ReadCategoryData(category));
    }

}
