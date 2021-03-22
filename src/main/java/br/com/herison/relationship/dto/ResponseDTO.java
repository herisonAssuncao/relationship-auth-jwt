package br.com.herison.relationship.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

public class ResponseDTO {

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Object data;
    private String message;
    private HttpStatus status;

    public Object getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    @JsonIgnore
    public HttpStatus getStatus() {
        return status;
    }

    public ResponseDTO(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public ResponseDTO(Object data, HttpStatus status) {
        this.data = data;
        this.status = status;
    }

    public ResponseDTO(Object data, String message, HttpStatus status) {
        this.data = data;
        this.message = message;
        this.status = status;
    }
}
