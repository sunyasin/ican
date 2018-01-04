package org.ideacreation.can.common.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import static org.ideacreation.can.common.CommonConst.TOKEN_HEADER_NAME;

/**
 */
public class Token {
    @JsonProperty(TOKEN_HEADER_NAME)
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
