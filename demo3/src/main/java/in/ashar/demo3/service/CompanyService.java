package in.ashar.demo3.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import in.ashar.demo3.dto.CompanyDto;
import in.ashar.demo3.entity.Company;
import in.ashar.demo3.entity.Employee;
import in.ashar.demo3.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ObjectMapper objectMapper;



    public void anyMethod(String json) throws JsonProcessingException {


    }



    public Company create(CompanyDto companyDto) {

        Company company = objectMapper.convertValue(companyDto,Company.class);

        List<Employee> employees = new ArrayList<>();
        List<String> employeeNames = companyDto.getEmployeeNames();
        for (String employeeName : employeeNames) {
            Employee employee = new Employee();
            employee.setEmployeeName(employeeName);
            employee.setCompany(company);
            employees.add(employee);
        }
        company.setEmployee(employees);



        System.out.println(company);
        return companyRepository.save(company);
    }

    public Company getById(int id) {
        return companyRepository.findById(id).orElseThrow(()-> new RuntimeException("Id not found"));
    }

    public List<Company> getAll() {

        return companyRepository.findAll();

    }

    public Company update(int id) {
        Company company1 = companyRepository.findById(id).orElseThrow(() -> new RuntimeException("Id not found"));

        List<Employee> employees = company1.getEmployee();

        employees.remove(0);

        return companyRepository.save(company1);
    }


}
