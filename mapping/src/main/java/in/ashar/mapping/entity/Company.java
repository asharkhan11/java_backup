package in.ashar.mapping.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Check(constraints = "annual_revenue > 0")
public class Company {
    @Id
    @SequenceGenerator(name = "seq_company_id", allocationSize = 30)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_company_id")
    private int companyId;
    @Column(nullable = false)
    private String companyName;
    @Column(nullable = false)
    private String companyAddress;
    @Column(nullable = false)
    private long annualRevenue;

    @OneToMany(mappedBy = "company", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonManagedReference("company-employee")
    private List<Employee> employees;

    @OneToMany(mappedBy = "company", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonManagedReference("company-laptop")
    private List<Laptop> laptops;

}
