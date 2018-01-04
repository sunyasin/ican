package back.entity;

import org.hibernate.annotations.GenericGenerator;
import org.ideacreation.can.common.model.enums.MessageType;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "message")
public class MessageEntity {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Enumerated
    @Column(nullable = false)
    private MessageType type;

    @Column(name = "msg")
    private String message;

    @Column(name = "img")
    private String image;

    @Column(name = "event_date")
    private Date eventDate;

    @Column(name = "event_time")
    private Time eventTime;

    @Column(name = "bonus_read")
    private Integer bonusReadAmount;

    @Column(name = "bonus_tell")
    private Integer bonusTellAmount;

    @Column(name = "tag_id")
    private Integer tagId;

    @OneToMany(mappedBy = "messageEntity", cascade = CascadeType.MERGE, orphanRemoval = false)
    private List<MessageRefEntity> messageRefEntityList = new ArrayList<>(0);

    public Integer getBonusReadAmount() {
        return bonusReadAmount;
    }

    public void setBonusReadAmount(Integer bonusReadAmount) {
        this.bonusReadAmount = bonusReadAmount;
    }

    public Integer getBonusTellAmount() {
        return bonusTellAmount;
    }

    public void setBonusTellAmount(Integer bonusTellAmount) {
        this.bonusTellAmount = bonusTellAmount;
    }

    public List<MessageRefEntity> getMessageRefEntityList() {
        return messageRefEntityList;
    }

    public void setMessageRefEntityList(List<MessageRefEntity> messageRefEntityList) {
        this.messageRefEntityList = messageRefEntityList;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }
}
