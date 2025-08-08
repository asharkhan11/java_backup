package in.ashar.mapping.controller;

import in.ashar.mapping.entity.Employee;
import in.ashar.mapping.entity.Employee;
import in.ashar.mapping.response.SuccessResponse;
import in.ashar.mapping.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @PostMapping("/create")
    public ResponseEntity<SuccessResponse<Employee>> createEmployee(@RequestBody Employee employee){
        return employeeService.createEmployee(employee);
    }

    @GetMapping("/get/all")
    public ResponseEntity<SuccessResponse<List<Employee>>> getAllEmployees(){
        return employeeService.getAllEmployees();
    }

    @GetMapping("/get/{employeeId}")
    public ResponseEntity<SuccessResponse<Employee>> getEmployeeById(@PathVariable("employeeId") int employeeId){
        return employeeService.getEmployeeById(employeeId);
    }

    @PutMapping("/update/{employeeId}")
    public ResponseEntity<SuccessResponse<Employee>> updateEmployeeDetails(@PathVariable("employeeId") int employeeId, @RequestBody Employee employee){
        return employeeService.updateEmployeeDetails(employeeId, employee);
    }

    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity<SuccessResponse<String>> deleteEmployeeById(@PathVariable("employeeId") int employeeId){
        return employeeService.deleteEmployeeById(employeeId);
    }
}
