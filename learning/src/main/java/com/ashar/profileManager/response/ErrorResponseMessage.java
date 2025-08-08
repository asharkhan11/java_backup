package com.ashar.profileManager.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponseMessage{

    private String reason;
    private String message;

}
