//package com.Eventicket.Services;
//
//import com.Eventicket.DTO.Recebido.AuthenticationDTO;
//import com.Eventicket.DTO.Recebido.RegisterDTO;
//import com.Eventicket.Entities.AddresEntity;
//import com.Eventicket.Entities.EmailEntity;
//import com.Eventicket.Entities.Enum.Role;
//import com.Eventicket.Entities.UserEntity;
//import com.Eventicket.Repositories.UserRepository;
//import com.Eventicket.Security.TokenService;
//import com.Eventicket.Exception.Email.EmailSendException;
//import com.Eventicket.Exception.User.UserCPFException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class AuthenticationService {
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private TokenService tokenService;
//
//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;
//
//    @Autowired
//    private EmailService emailService;
//
//    public String login(AuthenticationDTO dado) {
//        try {
//            var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(dado.email(), dado.senha());
//            var auth = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
//
//            var token = tokenService.generateToken((UserEntity) auth.getPrincipal());
//
//            return token;
//        } catch (BadCredentialsException e) {
//            throw e;
//        }
//    }
//
//    public String registrar(RegisterDTO dado) throws Exception {
//        try {
//            if (userRepository.findByEmail(dado.email()) != null) {
//                throw new IllegalArgumentException("Email ja esta sendo usado");
//            }
//
//            AddresEntity endereco = new AddresEntity();
//            endereco.setRua(dado.rua());
//            endereco.setCidade(dado.cidade());
//            endereco.setEstado(dado.estado());
//            endereco.setNumero(dado.numero());
//
//            String encryptedSenha = passwordEncoder.encode(dado.senha());
//
//            UserEntity novoUsuario = new UserEntity(dado.nome(), dado.email(), encryptedSenha, Role.CLIENTE, dado.cpf(), dado.celular());
//
//            novoUsuario.setEndereco(endereco);
//
//            userRepository.save(novoUsuario);
//
//            if (!novoUsuario.getEmail().isEmpty()) {
//                EmailEntity email = emailService.criarEmail(novoUsuario);
//                emailService.enviaEmail(email);
//            }
//
//            return "Usuario registrado";
//        } catch (DataIntegrityViolationException e) {
//            if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
//                System.out.println("CPF já existe no sistema: " + e.getMessage());
//                throw new UserCPFException();
//            }
//            System.out.println("Erro de integridade de dados: " + e.getMessage());
//            throw e;
//        } catch (EmailSendException e) {
//            System.out.println("Erro ao enviar o e-mail: " + e.getMessage());
//            throw e;
//        } catch (IllegalArgumentException e) {
//            throw e;
//        } catch (Exception e) {
//            throw new Exception("erro ao registrar user", e);
//        }
//    }
//}
