package com.Eventicket.Controllers;

import com.Eventicket.DTO.AuthenticationDTO;
import com.Eventicket.DTO.LoginResponseDTO;
import com.Eventicket.DTO.RegisterDTO;
import com.Eventicket.Services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO dado) {
        try {
            var token = authenticationService.login(dado);
            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity registrar(@RequestBody RegisterDTO dado) throws Exception {
        authenticationService.registrar(dado);
        return ResponseEntity.ok().build();
    }
}
