package org.ideacreation.can.common.exceptions;

import org.ideacreation.can.common.model.enums.ErrorDescription;

/**
 */
public class BackendException extends RuntimeException {

    private ErrorDescription error;

    public BackendException(ErrorDescription error) {
        super(error.getMessage());
        this.error = error;
    }

    public BackendException(String s) {
        super(s);
    }

    public BackendException(ErrorDescription error, String s) {
        super(s);
        this.error = error;
    }

    public ErrorDescription getError() {
        return error;
    }

    @Override
    public int hashCode() {
        return error.getErrorCode();
    }
}
