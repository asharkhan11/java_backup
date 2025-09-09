package in.ashar.spring_security.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import in.ashar.spring_security.utility.Credentials;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int managerId;
    private String managerName;
    private String managerAddress;
    private String managerSalary;

    @OneToOne
    @JoinColumn(name = "branch_id")
    @JsonBackReference("branch-manager")
    private Branch branch;


}
