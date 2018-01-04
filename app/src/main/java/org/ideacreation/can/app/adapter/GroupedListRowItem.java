package org.ideacreation.can.app.adapter;

import org.ideacreation.can.common.model.enums.GroupType;

/**
 */

public class GroupedListRowItem {
    private String text;
    private String total;
    private String unread;
    private Integer id;
    private GroupType type;
    private String imageFile;
    private Integer position;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public GroupType getType() {
        return type;
    }

    public void setType(GroupType type) {
        this.type = type;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getUnread() {
        return unread;
    }

    public void setUnread(String unread) {
        this.unread = unread;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "GroupedListRowItem{" +
                "text='" + text + '\'' +
                ", total='" + total + '\'' +
                ", unread='" + unread + '\'' +
                ", id=" + id +
                ", type=" + type +
                ", imageFile='" + imageFile + '\'' +
                ", position=" + position +
                '}';
    }
}
