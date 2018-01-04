package back.model.json_bak;

import org.ideacreation.can.common.model.enums.ProfileType;

public class ForeignProfileInfo {

    private Integer id;
    private String picture;
    private String name;
    private String address;
    private String description;
    private String contacts;
    private ProfileType type;
    private Integer bonuses;
    private Integer actionCount;
    private Integer eventCount;
    private Integer unreadCount;
    private Integer subscriberCount;
    BoardMessageInfo lastMessage;

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Integer getActionCount() {
        return actionCount;
    }

    public void setActionCount(Integer actionCount) {
        this.actionCount = actionCount;
    }

    public Integer getEventCount() {
        return eventCount;
    }

    public void setEventCount(Integer eventCount) {
        this.eventCount = eventCount;
    }

    public Integer getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(Integer unreadCount) {
        this.unreadCount = unreadCount;
    }

    public BoardMessageInfo getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(BoardMessageInfo lastMessage) {
        this.lastMessage = lastMessage;
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
