package in.ashar.demo3.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.processing.Exclude;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "employee")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int companyId;
    private String companyName;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Employee> employee;

}
