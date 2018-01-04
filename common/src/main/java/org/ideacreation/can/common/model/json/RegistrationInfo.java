package org.ideacreation.can.common.model.json;

import org.ideacreation.can.common.model.enums.ProfileType;

/**
 *
 */
public class RegistrationInfo {
    private String login;
    private String password;
    private ProfileType accountType;
    private String name;
    private String address;
    private String mapCoordinates;

    public RegistrationInfo() {
    }

    public RegistrationInfo(String login, String password, ProfileType accountType, String name, String address, String mapCoordinates) {
        this.login = login;
        this.password = password;
        this.accountType = accountType;
        this.name = name;
        this.address = address;
        this.mapCoordinates = mapCoordinates;
    }

    @Override
    public String toString() {
        return "RegistrationInfo{" +
                "accountType=" + accountType +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", mapCoordinates='" + mapCoordinates + '\'' +
                '}';
    }

    public ProfileType getAccountType() {
        return accountType;
    }

    public void setAccountType(ProfileType accountType) {
        this.accountType = accountType;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMapCoordinates() {
        return mapCoordinates;
    }

    public void setMapCoordinates(String mapCoordinates) {
        this.mapCoordinates = mapCoordinates;
    }
}
