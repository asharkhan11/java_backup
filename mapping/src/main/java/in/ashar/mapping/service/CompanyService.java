package in.ashar.mapping.service;

import in.ashar.mapping.entity.Company;
import in.ashar.mapping.exception.NotFoundException;
import in.ashar.mapping.repository.CompanyRepository;
import in.ashar.mapping.response.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository){
        this.companyRepository = companyRepository;
    }

    public ResponseEntity<SuccessResponse<Company>> createCompany(Company company){
        Company saved = companyRepository.save(company);
        SuccessResponse<Company> res = new SuccessResponse<>("SUCCESS","company created",saved);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    public ResponseEntity<SuccessResponse<List<Company>>> getAllCompanies() {
        List<Company> companies = companyRepository.findAll();
        SuccessResponse<List<Company>> res = new SuccessResponse<>();
        if(companies.isEmpty()){
            res.setStatus("NOT FOUND");
            res.setDetails("No companies found");
            res.setData(companies);
        }
        else{
            res.setStatus("FOUND");
            res.setDetails("total companies found : "+companies.size());
            res.setData(companies);
        }

        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    public ResponseEntity<SuccessResponse<Company>> getCompanyById(int companyId) {

        Optional<Company> co = companyRepository.findById(companyId);

        if(co.isEmpty()) throw new NotFoundException("Company not found");

        SuccessResponse<Company> res = new SuccessResponse<>("FOUND","company found with id : "+companyId,co.get());

        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    public ResponseEntity<SuccessResponse<String>> deleteCompanyById(int companyId){

        if(!companyRepository.existsById(companyId)) throw new NotFoundException("Company not found");

        companyRepository.deleteById(companyId);

        SuccessResponse<String> res = new SuccessResponse<>("DELETED","company deleted successfully","");

        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    public ResponseEntity<SuccessResponse<Company>> updateCompanyDetails(int companyId, Company company){

        Optional<Company> co = companyRepository.findById(companyId);

        if(co.isEmpty()) throw new NotFoundException("Company not found");

        Company updatedCompany = co.get();

        if(company.getCompanyName() != null && !company.getCompanyName().isEmpty()){
            updatedCompany.setCompanyName(company.getCompanyName());
        }
        if(company.getCompanyAddress() != null && !company.getCompanyAddress().isEmpty()){
            updatedCompany.setCompanyAddress(company.getCompanyAddress());
        }
        if(company.getAnnualRevenue() != 0){
            updatedCompany.setAnnualRevenue(company.getAnnualRevenue());
        }

        Company saved = companyRepository.save(updatedCompany);

        SuccessResponse<Company> res = new SuccessResponse<>("UPDATED","Company details updated",saved);

        return new ResponseEntity<>(res,HttpStatus.OK);

    }
}
