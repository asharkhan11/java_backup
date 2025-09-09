package in.ashar.spring_security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    private String customerName;
    private String  customerDOB;
    private String customerAddress;
    private String customerPhoneNumber;
    private int branchId;
    private String username;
    private String password;

}
