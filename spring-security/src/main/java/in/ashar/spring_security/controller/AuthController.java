package in.ashar.spring_security.controller;

import in.ashar.spring_security.dto.AuthResponse;
import in.ashar.spring_security.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestHeader("username") String username, @RequestHeader("password") String password){

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username,password);

        Authentication authenticated = authenticationManager.authenticate(authToken);

        UserDetails userDetails = (UserDetails) authenticated.getPrincipal();

        String jwt = jwtUtil.generateJwt(userDetails);

        return ResponseEntity.ok(new AuthResponse(jwt));
    }
}
