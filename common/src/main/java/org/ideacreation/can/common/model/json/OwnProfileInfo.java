package org.ideacreation.can.common.model.json;

import org.ideacreation.can.common.model.enums.ProfileType;

public class OwnProfileInfo {


    private Integer id;
    private String picture;
    private String name;
    private String address;
    private String description;
    private String contacts;
    private Integer bonuses;
    private Integer subscriberCount;
    private Integer bonusesInList;
    private ProfileType type;

    @Override
    public String toString() {
        return "OwnProfileInfo{" +
                "id=" + id +
                ", picture='" + picture + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                ", contacts='" + contacts + '\'' +
                ", bonuses=" + bonuses +
                ", subscriberCount=" + subscriberCount +
                ", bonusesInList=" + bonusesInList +
                ", type=" + type +
                '}';
    }

    public Integer getBonusesInList() {
        return bonusesInList;
    }

    public void setBonusesInList(Integer bonusesInList) {
        this.bonusesInList = bonusesInList;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Integer getBonuses() {
        return bonuses;
    }

    public void setBonuses(Integer bonuses) {
        this.bonuses = bonuses;
    }

    public Integer getSubscriberCount() {
        return subscriberCount;
    }

    public void setSubscriberCount(Integer subscriberCount) {
        this.subscriberCount = subscriberCount;
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

    public ProfileType getType() {
        return type;
    }

    public void setType(ProfileType type) {
        this.type = type;
    }
}
