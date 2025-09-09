package in.ashar.spring_security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManagerDto {

    private String managerName;
    private String managerAddress;
    private String managerSalary;
    private int branchId;
    private String username;
    private String password;

}
