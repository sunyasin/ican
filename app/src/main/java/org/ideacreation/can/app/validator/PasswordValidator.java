package org.ideacreation.can.app.validator;

/**
 * Created by haspel on 9/30/17.
 */

public class PasswordValidator {
    public boolean validate(String pass) {
        //TODO: Replace this with your own logic
        return pass.length() >= 3;
    }
}
