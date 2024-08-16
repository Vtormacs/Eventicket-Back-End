package com.Eventicket.Controllers;

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
            return new ResponseEntity<>(eventService.save(eventEntity), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EventEntity> update(@RequestBody EventEntity eventEntity, @PathVariable Long id) {
        try {
            return new ResponseEntity<>(eventService.update(eventEntity, id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(eventService.delete(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<EventEntity>> findAll() {
        try {
            return new ResponseEntity<>(eventService.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<EventEntity> findById(@PathVariable Long id){
        try {
            return new ResponseEntity<>(eventService.findById(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}