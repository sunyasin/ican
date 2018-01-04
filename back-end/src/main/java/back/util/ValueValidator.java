package back.util;

import org.ideacreation.can.common.exceptions.BackendException;

/**
 * Created by yas on 19.01.2017.
 */
public class ValueValidator {

    public static void checkNullThenThrow(String message, Object obj) throws BackendException {
        if (obj == null) {
            throw new BackendException(message);
        }
    }

}
