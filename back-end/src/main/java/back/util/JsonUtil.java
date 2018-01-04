package back.util;

import com.fasterxml.jackson.databind.JsonNode;

import org.ideacreation.can.common.exceptions.BackendException;

import java.io.IOException;

/**
 * Created by yas on 27.01.2017.
 */
public class JsonUtil {
    public static final String JSON_FIELD_DATA = "data";
    public static final String JSON_FIELD_ERROR = "error";

    // check 'data' or 'error' field exists
    public static JsonNode parseDataField(JsonNode node) throws BackendException, IOException {

        ValueValidator.checkNullThenThrow("json node is null", node);
        if (node.has(JSON_FIELD_ERROR)) {
            throw new BackendException(node.get(JSON_FIELD_ERROR).asText());
        } else if (!node.has(JSON_FIELD_DATA)) {
            throw new BackendException(JSON_FIELD_ERROR + " not found");
        }
        return node.get(JSON_FIELD_DATA);
    }
}
