package com.clinked.articleservice.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {

    private Integer statusCode;
    private Object data;
    private ErrorMessage errorMessage;

    public ApiResponse(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public ApiResponse(Integer statusCode, Object data) {
        this.statusCode = statusCode;
        this.data = data;
    }

    public ApiResponse(ErrorMessage errorMessage) { this.errorMessage = errorMessage; }
}
