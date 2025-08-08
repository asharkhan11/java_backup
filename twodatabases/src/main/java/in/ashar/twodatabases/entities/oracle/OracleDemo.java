package in.ashar.twodatabases.entities.oracle;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OracleDemo {

    @Id
    private int id;
    private int james;
}
