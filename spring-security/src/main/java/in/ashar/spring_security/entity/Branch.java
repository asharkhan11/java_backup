package in.ashar.spring_security.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int branchId;

    @NotBlank
    private String ifsc;
    @NotBlank
    private String branchName;
    @NotBlank
    private String branchAddress;
    @PositiveOrZero
    @NotNull
    private long amount;

    @OneToOne(mappedBy = "branch")
    @JsonManagedReference("branch-manager")
    private Manager manager;

    @ManyToOne
    @JoinColumn(name = "bank_id", nullable = false)
    @JsonBackReference("bank-branch")
    @NotEmpty
    private Bank bank;

    @OneToMany(mappedBy = "branch")
    @JsonManagedReference("branch-customer")
    private List<Customer> customers;

}
