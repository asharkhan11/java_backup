package in.ashar.feign_client_learn.feignClient;

import in.ashar.feign_client_learn.dto.AuthResponse;
import in.ashar.feign_client_learn.dto.CredentialDto;
import in.ashar.feign_client_learn.dto.RoleDto;
import in.ashar.feign_client_learn.dto.UpdateRoleDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "spring-security")
public interface SecurityClient {

    @PostMapping("/auth/login")
    ResponseEntity<AuthResponse> login(@RequestHeader("username") String username, @RequestHeader("password") String password);


    @PostMapping("/auth/register")
     ResponseEntity<CredentialDto> register(@RequestBody CredentialDto credentialDto);

    @GetMapping("/auth/refresh")
    ResponseEntity<AuthResponse> generateAccessToken(@RequestHeader("refresh-token") String refreshToken);


    /// /////////////////////////////////////////////////////////////////////////

    @PostMapping("/role")
    ResponseEntity<RoleDto> createRole(@RequestHeader("Authorization") String token, @RequestBody RoleDto roleDto);

    @DeleteMapping("/role")
    ResponseEntity<Void> deleteRole(@RequestHeader("Authorization") String token, @RequestBody RoleDto roleDto);

    @PutMapping("/role")
    ResponseEntity<RoleDto> updateRole(@RequestHeader("Authorization") String token, @RequestBody UpdateRoleDto roleDto);

    @GetMapping("/role")
    ResponseEntity<List<RoleDto>> getAllRoles(@RequestHeader("Authorization") String token);


}
