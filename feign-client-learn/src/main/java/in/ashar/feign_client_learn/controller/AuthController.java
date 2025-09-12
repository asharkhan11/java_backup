package in.ashar.feign_client_learn.controller;

import in.ashar.feign_client_learn.dto.AuthResponse;
import in.ashar.feign_client_learn.dto.CredentialDto;
import in.ashar.feign_client_learn.feignClient.SecurityClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private SecurityClient securityClient;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestHeader("username") String username, @RequestHeader("password") String password) {
        return securityClient.login(username, password);
    }

    @GetMapping("/refresh")
    public ResponseEntity<AuthResponse> generateAccessToken(@RequestHeader("refresh-token") String refreshToken) {
        return securityClient.generateAccessToken(refreshToken);
    }

    @PostMapping("/register")
    public ResponseEntity<CredentialDto> register(@RequestBody CredentialDto credentialDto) {
        return securityClient.register(credentialDto);
    }

}
