package in.ashar.mapping.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuccessResponse<T>{
    private String status;
    private String details;
    private T data;
}
