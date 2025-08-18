package in.ashar.filehandling.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AadhaarData {

    private String name;
    private LocalDate dob;
    private String address;
    private String gender;
    private String aadhaarId;
}
