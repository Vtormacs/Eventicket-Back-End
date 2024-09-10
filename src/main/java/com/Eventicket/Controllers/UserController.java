package com.Eventicket.Controllers;

import com.Eventicket.Entities.EventEntity;
import com.Eventicket.Entities.UserEntity;
import com.Eventicket.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public ResponseEntity<UserEntity> save(@RequestBody UserEntity userEntity) {
            return ResponseEntity.ok(userService.save(userEntity));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserEntity> update(@RequestBody UserEntity userEntity, @PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.update(userEntity, id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.delete(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<UserEntity>> findAll() {
        try {
            return ResponseEntity.ok(userService.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<UserEntity> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/buscarEventos/{idUsuario}")
    public ResponseEntity<List<EventEntity>> buscarEventosDaMesmaCidade(@PathVariable Long idUsuario) {
        try {
            return ResponseEntity.ok(userService.buscarEventosDaMesmaCidade(idUsuario));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/validar-conta")
    public ResponseEntity<String> validarConta(@RequestParam Long idUser,@RequestParam String hash) {
        boolean isValid = userService.validarConta(idUser,hash);

        if (isValid) {
            return ResponseEntity.ok("Conta validada com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falha na validação da conta.");
        }
    }
}
