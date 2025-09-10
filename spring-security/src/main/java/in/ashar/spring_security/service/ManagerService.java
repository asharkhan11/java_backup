package in.ashar.spring_security.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.ashar.spring_security.dto.ManagerDto;
import in.ashar.spring_security.entity.Branch;
import in.ashar.spring_security.entity.Manager;
import in.ashar.spring_security.entity.Users;
import in.ashar.spring_security.repository.BranchRepository;
import in.ashar.spring_security.repository.ManagerRepository;
import in.ashar.spring_security.repository.RolesRepository;
import in.ashar.spring_security.repository.UsersRepository;
import in.ashar.spring_security.utility.Credentials;
import in.ashar.spring_security.utility.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ManagerService {

    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RolesRepository rolesRepository;


    @Transactional
    public Manager createManager(ManagerDto managerDto) {

        Branch branch = branchRepository.findById(managerDto.getBranchId()).orElseThrow(RuntimeException::new);
        Manager manager = objectMapper.convertValue(managerDto, Manager.class);
        manager.setBranch(branch);

        Credentials credentials = objectMapper.convertValue(managerDto, Credentials.class);
        Users user = new Users();
        user.setUsername(credentials.getUsername());
        user.setPassword(passwordEncoder.encode(credentials.getPassword()));

        in.ashar.spring_security.entity.Roles role = rolesRepository.findByRole(Roles.MANAGER.name()).orElseThrow(RuntimeException::new);
        user.getRoles().add(role);
        user.setRoles(user.getRoles());

        usersRepository.save(user);
        return managerRepository.save(manager);
    }

    public Manager getManagerById(int id) {
        return managerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manager not found with id: " + id));
    }

    public List<Manager> getAllManagers() {
        return managerRepository.findAll();
    }

    public Manager updateManager(int id, ManagerDto managerDto) {
//        Manager existing = managerRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Manager not found with id: " + id));
//        // TODO: set fields from manager to existing if partial update is needed
//        return managerRepository.save(manager);
        return null;
    }

    public void deleteManager(int id) {
        managerRepository.deleteById(id);
    }
}