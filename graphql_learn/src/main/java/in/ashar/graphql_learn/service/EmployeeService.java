package in.ashar.graphql_learn.service;

import in.ashar.graphql_learn.entity.Employee;
import in.ashar.graphql_learn.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public Flux<Employee> getEmployees(){
        return employeeRepository.findAll();
    }

    public Mono<Employee> addEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public Mono<Employee> employeeById(String id){
        return employeeRepository.findById(id);
    }

    public Mono<Void> deleteEmployeeById(String id){
        return employeeRepository.deleteById(id);
    }

}
