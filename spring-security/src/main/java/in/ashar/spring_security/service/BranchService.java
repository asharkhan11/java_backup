package in.ashar.spring_security.service;

import in.ashar.spring_security.entity.Branch;
import in.ashar.spring_security.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchService {

    @Autowired
    private BranchRepository branchRepository;

    public Branch createBranch(Branch branch) {
        return branchRepository.save(branch);
    }

    public Branch getBranchById(int id) {
        return branchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Branch not found with id: " + id));
    }

    public List<Branch> getAllBranchs() {
        return branchRepository.findAll();
    }

    public Branch updateBranch(int id, Branch branch) {
        Branch existing = branchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Branch not found with id: " + id));
        // TODO: set fields from branch to existing if partial update is needed
        return branchRepository.save(branch);
    }

    public void deleteBranch(int id) {
        branchRepository.deleteById(id);
    }
}