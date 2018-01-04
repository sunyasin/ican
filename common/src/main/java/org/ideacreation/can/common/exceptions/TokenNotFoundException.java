package org.ideacreation.can.common.exceptions;

import org.ideacreation.can.common.model.enums.ErrorDescription;

/**
 */
public class TokenNotFoundException extends BackendException {

    public TokenNotFoundException() {
        super("token not found");
    }

    @Override
    public int hashCode() {
        return ErrorDescription.E_AUTH.getErrorCode();
    }
}
