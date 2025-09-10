package in.ashar.spring_security.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.ashar.spring_security.dto.AuthResponse;
import in.ashar.spring_security.dto.UserDto;
import in.ashar.spring_security.entity.Users;
import in.ashar.spring_security.repository.RolesRepository;
import in.ashar.spring_security.repository.UsersRepository;
import in.ashar.spring_security.utility.JwtUtil;
import in.ashar.spring_security.utility.Roles;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.HashSet;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestHeader("username") String username, @RequestHeader("password") String password) {


        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

        Authentication authenticated = authenticationManager.authenticate(authToken);

        if(!authenticated.isAuthenticated()){
            throw new RuntimeException("UnAuthenticated...");
        }


        UserDetails userDetails = (UserDetails) authenticated.getPrincipal();

        String jwt = jwtUtil.generateJwt(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(jwt, refreshToken));
    }

    @GetMapping("/access-token")
    public ResponseEntity<AuthResponse> generateAccessToken(@RequestHeader("refresh-token") String refreshToken) {

        String userName = jwtUtil.extractUsername(refreshToken);

        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

        if (!jwtUtil.validateJwt(refreshToken, userDetails)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String jwt = jwtUtil.generateJwt(userDetails);

        return ResponseEntity.ok(new AuthResponse(jwt, refreshToken));
    }

    @PostMapping("/register")
    public ResponseEntity<Users> register(@RequestBody @Valid UserDto userDto) {

        Set<in.ashar.spring_security.entity.Roles> roleSet = new HashSet<>();

        for (Roles role : userDto.getRoles()) {
            rolesRepository.findByRole(role.name()).ifPresent(roleSet::add);
        }

        Users user = new Users();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRoles(new HashSet<>(roleSet));

        Users save = usersRepository.save(user);
        return ResponseEntity.ok(save);

    }

}
