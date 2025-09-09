package in.ashar.demo3.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {

    private String companyName;
    private List<String> employeeNames;

}
