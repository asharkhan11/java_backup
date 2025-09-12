package in.ashar.feign_client_learn.controller;

import in.ashar.feign_client_learn.dto.RoleDto;
import in.ashar.feign_client_learn.dto.UpdateRoleDto;
import in.ashar.feign_client_learn.feignClient.SecurityClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private SecurityClient securityClient;

    @PostMapping
    public ResponseEntity<RoleDto> createRole(@RequestHeader("Authorization") String token,@RequestBody RoleDto roleDto) {
        return securityClient.createRole(token,roleDto);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteRole(@RequestHeader("Authorization") String token, @RequestBody RoleDto roleDto) {
        return securityClient.deleteRole(token, roleDto);
    }

    @PutMapping
    public ResponseEntity<RoleDto> updateRole(@RequestHeader("Authorization") String token, @RequestBody UpdateRoleDto roleDto) {
        return securityClient.updateRole(token, roleDto);
    }

    @GetMapping
    public ResponseEntity<List<RoleDto>> getAllRoles(@RequestHeader("Authorization") String token) {
        return securityClient.getAllRoles(token);
    }

}
