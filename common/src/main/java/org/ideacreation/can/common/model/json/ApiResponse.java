package org.ideacreation.can.common.model.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;

import org.ideacreation.can.common.exceptions.BackendException;
import org.ideacreation.can.common.model.enums.ErrorDescription;

/**
 * General response
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE)
public class ApiResponse<T> {

    private boolean success;
    private T result;
    private BackendError error;

    public T getResult() {
        return result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public BackendError getError() {
        return error;
    }

    public void setError(BackendError error) {
        this.error = error;
    }

    private ApiResponse(T result) {
        this.result = result;
        success = true;
    }

    @JsonCreator
    private ApiResponse() {
    }

    public static <T> ApiResponse<T> error(String message, ErrorDescription errorCode) {
        return errorResponse(message, errorCode.getErrorCode());
    }

    public static <T> ApiResponse<T> error(ErrorDescription error) {
        return errorResponse(error.getMessage(), error.getErrorCode());
    }

    public static <T> ApiResponse<T> error(String message, int errorCode) {
        return errorResponse(message, errorCode);
    }

    public static <T> ApiResponse<T> error(Exception e) {
        if (e instanceof BackendException) {
            return errorResponse(e.getMessage(), e.hashCode());
        }
        return errorResponse(e.toString(), ErrorDescription.E_COMMON.getErrorCode());
    }

    public static <T> ApiResponse<T> instance(T data) {
        return new ApiResponse<>(data);
    }

    public static <T> ApiResponse<T> instance() {
        return new ApiResponse<>(null);
    }

    private static <T> ApiResponse<T> errorResponse(String message, int errorCode) {
        ApiResponse<T> result = new ApiResponse<>(null);
        result.error = new BackendError();
        result.error.setMessage(message);
        result.error.setCode(errorCode);
        result.setSuccess(false);
        return result;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "success=" + success +
                ", result=" + result +
                ", error=" + error +
                '}';
    }
}
