package back.model.json_bak;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class BoardMessageInfo extends BoardMessage {

    private Integer boardId;
    private Integer subjId;
    private int likes;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private Date created;

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

}
