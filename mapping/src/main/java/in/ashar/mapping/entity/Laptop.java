package in.ashar.mapping.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import in.ashar.mapping.utility.enums.Performance;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Laptop {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int laptopId;

    private String laptopBrand;
    private Performance performance;

    @ManyToOne
    @JoinColumn(name = "companyId",nullable = false)
    @JsonBackReference("company-laptop")
    private Company company;

    @OneToOne(mappedBy = "laptop")
    @JsonManagedReference("laptop-employee")
    private Employee employee;
}
