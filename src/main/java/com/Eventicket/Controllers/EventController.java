package com.Eventicket.Controllers;

import com.Eventicket.DTO.Consulta.EventDTOConsulta;
import com.Eventicket.Entities.EventEntity;
import com.Eventicket.Services.EventService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("/save")
    public ResponseEntity<EventEntity> save(@RequestBody EventEntity eventEntity) {
        try {
            return ResponseEntity.ok(eventService.save(eventEntity));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EventEntity> update(@RequestBody EventEntity eventEntity, @PathVariable Long id) {
        try {
            return ResponseEntity.ok(eventService.update(eventEntity, id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(eventService.delete(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao deletar o evento: " + e.getMessage());
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<EventDTOConsulta>> findAll() {
        try {
            return ResponseEntity.ok(eventService.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<EventEntity> findById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.findById(id));
    }

    @GetMapping("/buscar-por-cidade/{cidade}")
    public ResponseEntity<List<EventEntity>> buscarEventosPorCidade(@PathVariable String cidade) {
        try {
            return ResponseEntity.ok(eventService.buscarEventosPorCidade(cidade));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/eventos-disponiveis")
    public ResponseEntity<List<EventEntity>> eventosDisponiveis() {
        try {
            return ResponseEntity.ok(eventService.eventosDisponiveis());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}