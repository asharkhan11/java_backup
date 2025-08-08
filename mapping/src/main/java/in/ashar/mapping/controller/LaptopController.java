package in.ashar.mapping.controller;

import in.ashar.mapping.entity.Laptop;
import in.ashar.mapping.response.SuccessResponse;
import in.ashar.mapping.service.LaptopService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/laptop")
public class LaptopController {
    private final LaptopService laptopService;

    public LaptopController(LaptopService laptopService){
        this.laptopService = laptopService;
    }

    @PostMapping("/create")
    public ResponseEntity<SuccessResponse<Laptop>> createLaptop(@RequestBody Laptop laptop){
        return laptopService.createLaptop(laptop);
    }

    @GetMapping("/get/all")
    public ResponseEntity<SuccessResponse<List<Laptop>>> getAllLaptops(){
        return laptopService.getAllLaptops();
    }

    @GetMapping("/get/{laptopId}")
    public ResponseEntity<SuccessResponse<Laptop>> getLaptopById(@PathVariable("laptopId") int laptopId){
        return laptopService.getLaptopById(laptopId);
    }

    @PutMapping("/update/{laptopId}")
    public ResponseEntity<SuccessResponse<Laptop>> updateLaptopDetails(@PathVariable("laptopId") int laptopId, @RequestBody Laptop laptop){
        return laptopService.updateLaptopDetails(laptopId, laptop);
    }

    @DeleteMapping("/delete/{laptopId}")
    public ResponseEntity<SuccessResponse<String>> deleteLaptopById(@PathVariable("laptopId") int laptopId){
        return laptopService.deleteLaptopById(laptopId);
    }
}
