package in.ashar.authorization_server.controller;

import in.ashar.authorization_server.entity.UserCredential;
import in.ashar.authorization_server.service.UserCredentialService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserCredentialController {

    private final UserCredentialService service;

    @PostMapping("/register")
    public UserCredential registerUser(@RequestParam String username,
                                       @RequestParam String password,
                                       @RequestParam(defaultValue = "false") boolean admin) {
        return service.registerUser(username, password, admin);
    }

    @GetMapping("/{username}")
    public UserCredential getUser(@PathVariable String username) {
        return service.findByUsername(username);
    }
}
