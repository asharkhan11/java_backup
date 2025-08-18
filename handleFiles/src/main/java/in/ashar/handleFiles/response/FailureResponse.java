package in.ashar.handleFiles.response;

import in.ashar.handleFiles.utility.ResponseEnum;

public class FailureResponse implements Response{
    private ResponseEnum status;
    private String detail;
}
