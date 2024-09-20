package com.Eventicket.Controllers;

import com.Eventicket.Entities.EventEntity;
import com.Eventicket.Entities.TicketEntity;
import com.Eventicket.Services.EventService;
import com.Eventicket.Services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/save")
    public ResponseEntity<TicketEntity> save(@RequestBody TicketEntity ticketEntity) {
        try {
            return ResponseEntity.ok(ticketService.save(ticketEntity));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

//    @PutMapping("/update/{id}")
//    public ResponseEntity<TicketEntity> update(@RequestBody TicketEntity ticketEntity, @PathVariable Long id) {
//        try {
//            return ResponseEntity.ok(ticketService.update(ticketEntity, id));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(ticketService.delete(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao deletar o ticket: " + e.getMessage());
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<TicketEntity>> findAll() {
        try {
            return ResponseEntity.ok(ticketService.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<TicketEntity> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(ticketService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/changeStatus/{id}")
    public ResponseEntity<TicketEntity> changeStatus(@PathVariable Long id){
        try {
            return ResponseEntity.ok(ticketService.changeStatusToUsado(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}