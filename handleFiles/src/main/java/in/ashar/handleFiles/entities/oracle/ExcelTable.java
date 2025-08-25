package in.ashar.handleFiles.entities.oracle;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelTable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private int userId;
    private String firstName;
    private String lastName;
    private String gender;
    private String country;
    private int age;
    private String dateOfJoining;
}
