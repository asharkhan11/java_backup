package in.ashar.demo3.controller;

import in.ashar.demo3.dto.CompanyDto;
import in.ashar.demo3.entity.Company;
import in.ashar.demo3.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping
    public ResponseEntity<Company> create(@RequestBody CompanyDto companyDto) {
        return ResponseEntity.ok(companyService.create(companyDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getById(@PathVariable int id) {
        return ResponseEntity.ok(companyService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<Company>> getAll() {
        return ResponseEntity.ok(companyService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Company> update(@PathVariable int id) {
        return ResponseEntity.ok(companyService.update(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        return ResponseEntity.noContent().build();
    }
}
