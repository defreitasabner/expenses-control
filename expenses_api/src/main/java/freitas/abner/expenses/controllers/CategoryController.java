package freitas.abner.expenses.controllers;

import freitas.abner.expenses.domain.category.*;
import freitas.abner.expenses.domain.category.dtos.CreateOrUpdateCategoryData;
import freitas.abner.expenses.domain.category.dtos.DeleteCategoryData;
import freitas.abner.expenses.domain.category.dtos.ReadCategoryData;
import freitas.abner.expenses.domain.user.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
            @RequestBody @Valid CreateOrUpdateCategoryData createOrUpdateCategoryData,
            UriComponentsBuilder uriBuilder,
            @AuthenticationPrincipal User user
    )
    {
        var category = categoryService.createNewCategory(createOrUpdateCategoryData, user);
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
        if (!category.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You couldn't check another user categories.");
        }
        return ResponseEntity.ok(new ReadCategoryData(category));
    }

    @PutMapping
    @Transactional
    ResponseEntity<ReadCategoryData> updateCategory(
            @RequestBody @Valid CreateOrUpdateCategoryData updateCategoryData
    )
    {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var category = categoryService.update(updateCategoryData, user);
        return ResponseEntity.ok(new ReadCategoryData(category));
    }

    @DeleteMapping
    @Transactional
    ResponseEntity<Void> deleteCategory(
            @RequestBody @Valid DeleteCategoryData deleteCategoryData
    )
    {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        categoryService.delete(deleteCategoryData, user);
        return ResponseEntity.noContent().build();
    }

}
