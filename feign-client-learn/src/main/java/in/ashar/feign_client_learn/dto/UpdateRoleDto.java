package in.ashar.feign_client_learn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRoleDto {

    private String oldRoleName;
    private String newRoleName;

}
