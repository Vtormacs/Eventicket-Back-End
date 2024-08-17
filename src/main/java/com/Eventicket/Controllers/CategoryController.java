package com.Eventicket.Controllers;

import com.Eventicket.Entities.AddresEntity;
import com.Eventicket.Entities.CategoryEntity;
import com.Eventicket.Services.AddresService;
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
        try {
            return ResponseEntity.ok(categoryService.save(categoryEntity));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryEntity> update(@RequestBody CategoryEntity categoryEntity, @PathVariable Long id) {
        try {
            return ResponseEntity.ok(categoryService.update(categoryEntity, id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(categoryService.delete(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao deletar o categoria: " + e.getMessage());
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<CategoryEntity>> findAll() {
        try {
            return ResponseEntity.ok(categoryService.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<CategoryEntity> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(categoryService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
