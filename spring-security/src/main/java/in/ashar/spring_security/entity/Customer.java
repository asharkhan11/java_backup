package in.ashar.spring_security.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;
    private String customerName;
    private String customerDOB;
    private String customerAddress;
    private String customerPhoneNumber;

    @ManyToOne
    @JoinColumn(name = "branchId", nullable = false)
    @JsonBackReference("branch-customer")
    private Branch branch;

}
