package in.ashar.mapping.service;

import in.ashar.mapping.entity.Laptop;
import in.ashar.mapping.entity.Laptop;
import in.ashar.mapping.exception.NotFoundException;
import in.ashar.mapping.repository.LaptopRepository;
import in.ashar.mapping.response.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LaptopService {
    private final LaptopRepository laptopRepository;

    public LaptopService(LaptopRepository laptopRepository){
        this.laptopRepository = laptopRepository;
    }

    public ResponseEntity<SuccessResponse<Laptop>> createLaptop(Laptop laptop) {

        Laptop saved = laptopRepository.save(laptop);

        SuccessResponse<Laptop> res = new SuccessResponse<>("SUCCESS","Laptop created",saved);

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    public ResponseEntity<SuccessResponse<Laptop>> getLaptopById(int laptopId){

        Optional<Laptop> eo = laptopRepository.findById(laptopId);

        if(eo.isEmpty()) throw new NotFoundException("Laptop not found");

        SuccessResponse<Laptop> res = new SuccessResponse<>("FOUND","Laptop found with id : "+laptopId, eo.get());

        return new ResponseEntity<>(res,HttpStatus.OK);

    }

    public ResponseEntity<SuccessResponse<List<Laptop>>> getAllLaptops(){
        List<Laptop> laptops = laptopRepository.findAll();

        if(laptops.isEmpty()) throw new NotFoundException("Laptops not found");

        SuccessResponse<List<Laptop>> res = new SuccessResponse<>("FOUND","Total Laptops found : "+laptops.size(),laptops);

        return new ResponseEntity<>(res,HttpStatus.OK);

    }

    public ResponseEntity<SuccessResponse<Laptop>> updateLaptopDetails(int laptopId, Laptop laptop){

        Optional<Laptop> eo = laptopRepository.findById(laptopId);

        if(eo.isEmpty()) throw new NotFoundException("Laptop not found");

        Laptop updatedLaptop = eo.get();

        if(laptop.getLaptopBrand() != null && !laptop.getLaptopBrand().isBlank()){
            updatedLaptop.setLaptopBrand(laptop.getLaptopBrand());
        }
        if(laptop.getPerformance() != null){
            updatedLaptop.setPerformance(laptop.getPerformance());
        }
        if(laptop.getCompany() != null){
            updatedLaptop.setCompany(laptop.getCompany());
        }
        if(laptop.getEmployee() != null){
            updatedLaptop.setEmployee(laptop.getEmployee());
        }

        Laptop saved = laptopRepository.save(updatedLaptop);

        SuccessResponse<Laptop> res = new SuccessResponse<>("UPDATED","Laptop updated successfully",updatedLaptop);

        return new ResponseEntity<>(res,HttpStatus.OK);


    }

    public ResponseEntity<SuccessResponse<String>> deleteLaptopById(int laptopId){
        if(!laptopRepository.existsById(laptopId)) throw new NotFoundException("Laptop not found");

        laptopRepository.deleteById(laptopId);

        SuccessResponse<String> res = new SuccessResponse<>("DELETED","Laptop deleted successfully","");

        return new ResponseEntity<>(res,HttpStatus.OK);
    }
}
