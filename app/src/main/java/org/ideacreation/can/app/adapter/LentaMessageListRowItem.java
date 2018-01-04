package org.ideacreation.can.app.adapter;

import org.ideacreation.can.common.model.enums.MessageType;

import java.util.Date;

/**
 */

public class LentaMessageListRowItem {
    private int boardMsgId;
    private int messageId;
    private String text;
    private String imageFile;
    private MessageType messageType;
    private String messageTypeIcon;
    private int bonusesForRepost;
    private Date publishDate;
    private String authorName;
    private int authorId;
    private Date eventDate;
    private Boolean isBookmarked;
    private Boolean isSubscribed;
    private Boolean isShared;
    private Boolean wasRead;
    private Integer bonusesCount;

    public Integer getBonusesCount() {
        return bonusesCount;
    }

    public void setBonusesCount(Integer bonusesCount) {
        this.bonusesCount = bonusesCount;
    }

    public Boolean getBookmarked() {
        return isBookmarked;
    }

    public void setBookmarked(Boolean bookmarked) {
        isBookmarked = bookmarked;
    }

    public Boolean getSubscribed() {
        return isSubscribed;
    }

    public void setSubscribed(Boolean subscribed) {
        isSubscribed = subscribed;
    }

    public Boolean getShared() {
        return isShared;
    }

    public void setShared(Boolean shared) {
        isShared = shared;
    }

    public Boolean getWasRead() {
        return wasRead;
    }

    public void setWasRead(Boolean wasRead) {
        this.wasRead = wasRead;
    }

    public int getBoardMsgId() {
        return boardMsgId;
    }

    public void setBoardMsgId(int boardMsgId) {
        this.boardMsgId = boardMsgId;
    }

    public int getBonusesForRepost() {
        return bonusesForRepost;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public void setBonusesForRepost(int bonusesForRepost) {
        this.bonusesForRepost = bonusesForRepost;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getMessageTypeIcon() {
        return messageTypeIcon;
    }

    public void setMessageTypeIcon(String messageTypeIcon) {
        this.messageTypeIcon = messageTypeIcon;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

}
