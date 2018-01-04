package org.ideacreation.can.common.model.json;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class BoardMessageInfo extends BoardMessage {

    private Integer boardId;
    private Integer subjId;
    private String subjName;
    private int likes;
    private Boolean isSubscribed;
    private Boolean isBookmarked;
    private Boolean isShared;
    private Boolean wasRead;
    private Integer viewerBonuses;

    public Boolean getWasRead() {
        return wasRead;
    }

    public void setWasRead(Boolean wasRead) {
        this.wasRead = wasRead;
    }

    public Integer getViewerBonuses() {
        return viewerBonuses;
    }

    public void setViewerBonuses(Integer viewerBonuses) {
        this.viewerBonuses = viewerBonuses;
    }

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private Date created;

    public String getSubjName() {
        return subjName;
    }

    public void setSubjName(String subjName) {
        this.subjName = subjName;
    }

    public Integer getBoardId() {
        return boardId;
    }

    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Integer getSubjId() {
        return subjId;
    }

    public void setSubjId(Integer subjId) {
        this.subjId = subjId;
    }

    public Boolean getSubscribed() {
        return isSubscribed;
    }

    public void setSubscribed(Boolean subscribed) {
        isSubscribed = subscribed;
    }

    public Boolean getBookmarked() {
        return isBookmarked;
    }

    public void setBookmarked(Boolean bookmarked) {
        isBookmarked = bookmarked;
    }

    public Boolean getShared() {
        return isShared;
    }

    public void setShared(Boolean shared) {
        isShared = shared;
    }
}
