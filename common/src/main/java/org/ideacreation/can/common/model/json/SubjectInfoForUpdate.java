package org.ideacreation.can.common.model.json;

import java.util.List;

public class SubjectInfoForUpdate {


    private Integer id;

    private String name;

    private String address;

    private String login;

    private String pass;

    private String email;

    private String description;

    private String contacts;

    private Boolean isAutoBonusActive;

    private List<TagInfo> subscribedTags;

    public List<TagInfo> getSubscribedTags() {
        return subscribedTags;
    }

    public void setSubscribedTags(List<TagInfo> subscribedTags) {
        this.subscribedTags = subscribedTags;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public Boolean getAutoBonusActive() {
        return isAutoBonusActive;
    }

    public void setAutoBonusActive(Boolean autoBonusActive) {
        isAutoBonusActive = autoBonusActive;
    }
}
