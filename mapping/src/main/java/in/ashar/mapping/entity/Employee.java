package in.ashar.mapping.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Check(constraints = "salary>0")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int employeeId;
    @Column(nullable = false)
    private String employeeName;
    @Column(nullable = false)
    private String employeeAddress;
    @Column(nullable = false)
    private long salary;

    @ManyToOne
    @JoinColumn(name = "companyId",nullable = false)
    @JsonBackReference("company-employee")
    private Company company;

    @OneToOne
    @JoinColumn(name = "laptopId",unique = true)
    @JsonBackReference("laptop-employee")
    private Laptop laptop;
}
