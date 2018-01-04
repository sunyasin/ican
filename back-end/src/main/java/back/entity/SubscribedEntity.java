package back.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "subj_subscribe")
public class SubscribedEntity {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "subscriber", nullable = false)
    private SubjectEntity subscriber;

    @ManyToOne
    @JoinColumn(name = "subscribe_on", nullable = false)
    private SubjectEntity subscribeOn;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = true)
    private SubjectGroupEntity group;

    public SubjectEntity getSubscribeOn() {
        return subscribeOn;
    }

    public SubjectGroupEntity getGroup() {
        return group;
    }

    public void setGroup(SubjectGroupEntity group) {
        this.group = group;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SubjectEntity getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(SubjectEntity subscriber) {
        this.subscriber = subscriber;
    }

    public void setSubscribeOn(SubjectEntity subscribeOn) {
        this.subscribeOn = subscribeOn;
    }
}
