package in.ashar.spring_security.controller;

import in.ashar.spring_security.entity.Branch;
import in.ashar.spring_security.service.BranchService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/branch")
public class BranchController {

    @Autowired
    private BranchService branchService;

    @PostMapping
    public ResponseEntity<Branch> create(@RequestBody @Valid Branch branch) {
        return ResponseEntity.ok(branchService.createBranch(branch));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Branch> getById(@PathVariable int id) {
        return ResponseEntity.ok(branchService.getBranchById(id));
    }

    @GetMapping
    public ResponseEntity<List<Branch>> getAll() {
        return ResponseEntity.ok(branchService.getAllBranchs());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Branch> update(@PathVariable int id, @RequestBody @Valid Branch branch) {
        return ResponseEntity.ok(branchService.updateBranch(id, branch));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        branchService.deleteBranch(id);
        return ResponseEntity.noContent().build();
    }
}