package in.ashar.spring_security.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.ashar.spring_security.dto.CustomerDto;
import in.ashar.spring_security.entity.Branch;
import in.ashar.spring_security.entity.Customer;
import in.ashar.spring_security.entity.Users;
import in.ashar.spring_security.repository.BranchRepository;
import in.ashar.spring_security.repository.CustomerRepository;
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
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RolesRepository rolesRepository;

    @Transactional
    public Customer createCustomer(CustomerDto customerDto) {

        Branch branch = branchRepository.findById(customerDto.getBranchId()).orElseThrow(RuntimeException::new);
        Customer customer = objectMapper.convertValue(customerDto, Customer.class);
        customer.setBranch(branch);

        Credentials credential = objectMapper.convertValue(customerDto, Credentials.class);
        Users user = new Users();
        user.setUsername(credential.getUsername());
        user.setPassword(passwordEncoder.encode(credential.getPassword()));

        in.ashar.spring_security.entity.Roles role = rolesRepository.findByRole(Roles.CUSTOMER.name()).orElseThrow(RuntimeException::new);
        user.getRoles().add(role);
        user.setRoles(user.getRoles());

        usersRepository.save(user);

        return customerRepository.save(customer);
    }

    public Customer getCustomerById(int id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer updateCustomer(int id, CustomerDto customerDto) {
//        Customer existing = customerRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
//        // TODO: set fields from customer to existing if partial update is needed
//        return customerRepository.save(customer);
        return null;
    }

    public void deleteCustomer(int id) {
        customerRepository.deleteById(id);
    }
}