package br.com.erudio.restprojecterudio.controllers;

import br.com.erudio.restprojecterudio.data.vo.v1.security.AccountCredentialsVO;
import br.com.erudio.restprojecterudio.services.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication Endpoint")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping("/signin")
    public ResponseEntity signin(@RequestBody AccountCredentialsVO data){
        if(checkIfParamsIsNotNull(data))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
        var token = service.signin(data);
        if(token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
        return token;
    }

    @PutMapping("/refresh/{username}")
    public ResponseEntity refreshToken(@PathVariable("username") String username, @RequestHeader("Authorization") String refreshToken){
        if(checkIfParamsIsNotNull(username, refreshToken))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
        var token = service.refreshToken(username, refreshToken);
        if(token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
        return token;
    }
    private boolean checkIfParamsIsNotNull(AccountCredentialsVO data) {
        return data == null || data.getUsername() == null || data.getUsername().isBlank()
                || data.getPassword() == null || data.getPassword().isBlank();
    }
    private boolean checkIfParamsIsNotNull(String username, String refreshToken) {
        return username == null || username.isBlank() || refreshToken == null || refreshToken.isBlank();
    }

}
