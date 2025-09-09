package in.ashar.spring_security.controller;

import in.ashar.spring_security.entity.Bank;
import in.ashar.spring_security.service.BankService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/bank")
public class BankController {

    @Autowired
    private BankService bankService;

    @PostMapping
    public ResponseEntity<Bank> create(@RequestBody @Valid Bank bank) {
        // TODO: implement create
        return ResponseEntity.ok(bankService.createBank(bank));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bank> getById(@PathVariable int id) {
        // TODO: implement read by ID
        return ResponseEntity.ok(bankService.getBankById(id));
    }

    @GetMapping
    public ResponseEntity<List<Bank>> getAll() {

        // TODO: implement read all
        return ResponseEntity.ok(bankService.getAllBanks());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bank> update(@PathVariable int id, @RequestBody @Valid Bank bank) {
        // TODO: implement update
        return ResponseEntity.ok(bankService.updateBank(id,bank));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        // TODO: implement delete
        bankService.deleteBank(id);
        return ResponseEntity.noContent().build();
    }
}
