package org.ideacreation.can.common.model.enums;

import org.ideacreation.can.common.exceptions.BackendException;
import org.ideacreation.can.common.model.json.ApiResponse;
import org.ideacreation.can.common.model.json.BackendError;

/**
 */
public enum ErrorDescription {
    SUCC(0, "ok"),
    E_AUTH(1, "ошибка авторизации"),
    E_COMMON(2, "ошибка"),
    E_NULL(3, "ошибка - входные null значени"),
    E_MSG_NOT_FOUND(4, ""),
    E_MSG_TEXT_REQUIRED(5, ""),
    E_MSG_TYPE_REQUIRED(6, ""),
    E_CATEGORY_NOT_FOUND(7, ""),
    E_DUPLICATE_LOGIN(8, ""),
    E_GROUP_NOT_FOUND(9, ""),
    E_PROFILE_NOT_FOUND(10, ""),
    E_WRONG_GROUP_OWNER(11, ""),
    E_GROUPNAME_ALREADY_EXISTS(12, ""),
    E_TAG_NOT_FOUND(13, ""),
    E_FILE_UPLOAD(14, ""),
    E_FILE_NOT_FOUND(15, ""),
    E_MSG_TAG(16, ""),
    E_FILE_UPLOAD_EMPTY(17, ""),
    E_EVENT_TYPE_REQUIRED(18, "Операция возможна с собщениями типа EVENT");


    private final int errorCode;
    private final String message;

    ErrorDescription(int errorCode, String msg) {
        this.errorCode = errorCode;
        this.message = msg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public BackendError getError() {
        return new BackendError(errorCode, message);
    }

    public BackendError getError(String message) {
        return new BackendError(errorCode, message);
    }

    public String getMessage() {
        return message;
    }

    public void throwException() {
        throw new BackendException(this);
    }

    public void throwExceptionWithMessage(String message) {

        throw new BackendException(this, message);
    }

    public ApiResponse getErrorResponse(String message) {

        return ApiResponse.error(message, errorCode);
    }

    public void throwExceptionIfNull(Object... objects) {
        for (Object obj : objects) {
            if (obj == null) {
                throw new BackendException(this);
            }
        }
    }

}
