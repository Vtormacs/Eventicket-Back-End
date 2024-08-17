package com.Eventicket.Controllers;

import com.Eventicket.Entities.AddresEntity;
import com.Eventicket.Services.AddresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addres")
public class AddresController {

    @Autowired
    private AddresService addresService;

    @PostMapping("/save")
    public ResponseEntity<AddresEntity> save(@RequestBody AddresEntity addresEntity) {
        try {
            return ResponseEntity.ok(addresService.save(addresEntity));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AddresEntity> update(@RequestBody AddresEntity addresEntity, @PathVariable Long id) {
        try {
            return ResponseEntity.ok(addresService.update(addresEntity, id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(addresService.delete(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao deletar o endereço: " + e.getMessage());
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<AddresEntity>> findAll() {
        try {
            return ResponseEntity.ok(addresService.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<AddresEntity> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(addresService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
