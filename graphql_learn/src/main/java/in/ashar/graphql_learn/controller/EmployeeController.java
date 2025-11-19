package in.ashar.graphql_learn.controller;

import in.ashar.graphql_learn.entity.Employee;
import in.ashar.graphql_learn.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Controller
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @MutationMapping
    public Mono<Employee> create(@Argument("employee") Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @QueryMapping
    public Mono<Employee> getById(@Argument String id) {
        return employeeService.employeeById(id);
    }

    @QueryMapping
    public Flux<Employee> getAll() {
        return employeeService.getEmployees();
    }

    @MutationMapping
    public Mono<Void> delete(@Argument String id) {
        return employeeService.deleteEmployeeById(id);
    }
}