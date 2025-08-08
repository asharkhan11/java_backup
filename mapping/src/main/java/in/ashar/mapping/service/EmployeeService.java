package in.ashar.mapping.service;

import in.ashar.mapping.entity.Employee;
import in.ashar.mapping.exception.NotFoundException;
import in.ashar.mapping.repository.EmployeeRepository;
import in.ashar.mapping.response.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    public ResponseEntity<SuccessResponse<Employee>> createEmployee(Employee employee) {

        Employee saved = employeeRepository.save(employee);

        SuccessResponse<Employee> res = new SuccessResponse<>("SUCCESS","Employee created",saved);

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    public ResponseEntity<SuccessResponse<Employee>> getEmployeeById(int employeeId){

        Optional<Employee> eo = employeeRepository.findById(employeeId);

        if(eo.isEmpty()) throw new NotFoundException("Employee not found");

        SuccessResponse<Employee> res = new SuccessResponse<>("FOUND","Employee found with id : "+employeeId, eo.get());

        return new ResponseEntity<>(res,HttpStatus.OK);

    }

    public ResponseEntity<SuccessResponse<List<Employee>>> getAllEmployees(){
        List<Employee> employees = employeeRepository.findAll();

        if(employees.isEmpty()) throw new NotFoundException("Employees not found");

        SuccessResponse<List<Employee>> res = new SuccessResponse<>("FOUND","Total Employees found : "+employees.size(),employees);

        return new ResponseEntity<>(res,HttpStatus.OK);

    }

    public ResponseEntity<SuccessResponse<Employee>> updateEmployeeDetails(int employeeId, Employee employee){

        Optional<Employee> eo = employeeRepository.findById(employeeId);

        if(eo.isEmpty()) throw new NotFoundException("Employee not found");

        Employee updatedEmployee = eo.get();

        if(employee.getEmployeeName() != null && !employee.getEmployeeName().isBlank()){
            updatedEmployee.setEmployeeName(employee.getEmployeeName());
        }
        if(employee.getEmployeeAddress() != null && !employee.getEmployeeAddress().isBlank()){
            updatedEmployee.setEmployeeAddress(employee.getEmployeeAddress());
        }
        if(employee.getSalary() > 0){
            updatedEmployee.setSalary(employee.getSalary());
        }
        if(employee.getCompany() != null){
            updatedEmployee.setCompany(employee.getCompany());
        }

        Employee saved = employeeRepository.save(updatedEmployee);

        SuccessResponse<Employee> res = new SuccessResponse<>("UPDATED","Employee updated successfully",updatedEmployee);

        return new ResponseEntity<>(res,HttpStatus.OK);


    }

    public ResponseEntity<SuccessResponse<String>> deleteEmployeeById(int employeeId){
        if(!employeeRepository.existsById(employeeId)) throw new NotFoundException("Employee not found");

        employeeRepository.deleteById(employeeId);

        SuccessResponse<String> res = new SuccessResponse<>("DELETED","Employee deleted successfully","");

        return new ResponseEntity<>(res,HttpStatus.OK);
    }
}
