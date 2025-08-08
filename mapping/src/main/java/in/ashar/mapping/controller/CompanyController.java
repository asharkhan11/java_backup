package in.ashar.mapping.controller;

import in.ashar.mapping.entity.Company;
import in.ashar.mapping.response.SuccessResponse;
import in.ashar.mapping.service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService){
        this.companyService = companyService;
    }

    @PostMapping("/create")
    public ResponseEntity<SuccessResponse<Company>> createCompany(@RequestBody Company company){
        return companyService.createCompany(company);
    }

    @GetMapping("/get/all")
    public ResponseEntity<SuccessResponse<List<Company>>> getAllCompanies(){
        return companyService.getAllCompanies();
    }

    @GetMapping("/get/{companyId}")
    public ResponseEntity<SuccessResponse<Company>> getCompanyById(@PathVariable("companyId") int companyId){
        return companyService.getCompanyById(companyId);
    }

    @PutMapping("/update/{companyId}")
    public ResponseEntity<SuccessResponse<Company>> updateCompanyDetails(@PathVariable("companyId") int companyId, @RequestBody Company company){
        return companyService.updateCompanyDetails(companyId, company);
    }

    @DeleteMapping("/delete/{companyId}")
    public ResponseEntity<SuccessResponse<String>> deleteCompanyById(@PathVariable("companyId") int companyId){
        return companyService.deleteCompanyById(companyId);
    }



}
