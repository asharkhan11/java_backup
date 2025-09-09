package in.ashar.spring_security.controller;

import in.ashar.spring_security.dto.ManagerDto;
import in.ashar.spring_security.entity.Manager;
import in.ashar.spring_security.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @PostMapping
    public ResponseEntity<Manager> create(@RequestBody ManagerDto managerDto) {
        return ResponseEntity.ok(managerService.createManager(managerDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Manager> getById(@PathVariable int id) {
        return ResponseEntity.ok(managerService.getManagerById(id));
    }

    @GetMapping
    public ResponseEntity<List<Manager>> getAll() {
        return ResponseEntity.ok(managerService.getAllManagers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Manager> update(@PathVariable int id, @RequestBody ManagerDto managerDto) {
        return ResponseEntity.ok(managerService.updateManager(id, managerDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        managerService.deleteManager(id);
        return ResponseEntity.noContent().build();
    }
}