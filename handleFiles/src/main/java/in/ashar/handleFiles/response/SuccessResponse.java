package in.ashar.handleFiles.response;

import in.ashar.handleFiles.utility.ResponseEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuccessResponse<T> implements Response{

    private ResponseEnum status;
    private String detail;
    private T response;
}
