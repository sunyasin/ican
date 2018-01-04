package org.ideacreation.can.common.model.json;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.ideacreation.can.common.model.enums.MessageType;

import java.sql.Time;
import java.util.Date;


public class BoardMessage {

    private Integer msgId;
    private MessageType type;
    private String message;
    private String imageFile;
    private Integer tagId;
    private Integer bonusTell;
    private Integer bonusRead;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date eventDate;

    @JsonFormat(pattern = "hh:mm")
    private Time eventTime;

    public Integer getBonusTell() {
        return bonusTell;
    }

    public void setBonusTell(Integer bonusTell) {
        this.bonusTell = bonusTell;
    }

    public Integer getBonusRead() {
        return bonusRead;
    }

    public void setBonusRead(Integer bonusRead) {
        this.bonusRead = bonusRead;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public Time getEventTime() {
        return eventTime;
    }

    public void setEventTime(Time eventTime) {
        this.eventTime = eventTime;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public Integer getMsgId() {
        return msgId;
    }

    public void setMsgId(Integer msgId) {
        this.msgId = msgId;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
