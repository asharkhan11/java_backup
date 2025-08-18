package in.ashar.handleFiles.response;

import in.ashar.handleFiles.utility.ResponseEnum;

public class SuccessResponse<T> implements Response{

    private ResponseEnum status;
    private String detail;
    private T response;
}
