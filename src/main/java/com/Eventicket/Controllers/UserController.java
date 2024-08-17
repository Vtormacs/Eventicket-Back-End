package com.Eventicket.Controllers;

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
    public ResponseEntity<UserEntity> save(@RequestBody UserEntity userEntity){
        try {
            return new ResponseEntity<>(userService.save(userEntity), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserEntity> update(@RequestBody UserEntity userEntity, @PathVariable Long id){
        try {
            return new ResponseEntity<>(userService.update(userEntity, id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        try {
            return new ResponseEntity<>(userService.delete(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<UserEntity>> findAll(){
        try {
            return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<UserEntity> findById(@PathVariable Long id){
        try {
            return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
