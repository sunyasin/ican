package org.ideacreation.can.common.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BackendError {

    @JsonProperty("code")
    private int code;

    @JsonProperty("message")
    private String message;

    public BackendError() {
    }

    public BackendError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "BackendError{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
