package com.Eventicket.Controllers;

import com.Eventicket.Entities.CategoryEntity;
import com.Eventicket.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/save")
    public ResponseEntity<CategoryEntity> save(@RequestBody CategoryEntity categoryEntity) {
        return ResponseEntity.ok(categoryService.save(categoryEntity));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryEntity> update(@RequestBody CategoryEntity categoryEntity, @PathVariable Long id) {
        return ResponseEntity.ok(categoryService.update(categoryEntity, id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.delete(id));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<CategoryEntity>> findAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<CategoryEntity> findById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }
}
