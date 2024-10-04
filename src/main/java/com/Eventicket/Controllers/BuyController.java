package com.Eventicket.Controllers;

import com.Eventicket.DTO.Consulta.BuyDTOConsulta;
import com.Eventicket.Entities.BuyEntity;
import com.Eventicket.Entities.EventEntity;
import com.Eventicket.Repositories.EventRepository;
import com.Eventicket.Services.BuyService;
import org.hibernate.metamodel.mapping.EntityVersionMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buy")
public class BuyController {

    @Autowired
    private BuyService buyService;

    @Autowired
    private EventRepository eventRepository;

    @PostMapping("/sell/{idUsuario}")
    public ResponseEntity<BuyEntity> processarCompra(@PathVariable Long idUsuario, @RequestBody Map<Long, Integer> carrinho) {
        try {
            return ResponseEntity.ok(buyService.processarCompra(idUsuario,carrinho));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(buyService.delete(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao deletar a compra: " + e.getMessage());
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<BuyDTOConsulta>> findAll() {
        try {
            return ResponseEntity.ok(buyService.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<BuyDTOConsulta> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(buyService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
