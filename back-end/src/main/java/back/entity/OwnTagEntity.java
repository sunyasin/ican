package back.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

//убрать - теги брать из сообщений
//@Entity
//@Table(name = "subj_own_tags")
public class OwnTagEntity {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "subj_id", nullable = false)
    private Integer subjId;

    @ManyToOne
    @JoinColumn(name = "tag_id", nullable = false)
    private TagEntity tagEntity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSubjId() {
        return subjId;
    }

    public void setSubjId(Integer subjId) {
        this.subjId = subjId;
    }

    public TagEntity getTagEntity() {
        return tagEntity;
    }

    public void setTagEntity(TagEntity tagEntity) {
        this.tagEntity = tagEntity;
    }


}
