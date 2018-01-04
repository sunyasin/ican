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
@Table(name = "subj_tag")
public class SubscribedTagEntity {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id", nullable = false)
    private Integer id;

//  @Column(name="subj_id",nullable = false)
//  private Integer subjId;

    @ManyToOne
    @JoinColumn(name = "subj_id", nullable = false)
    private SubjectEntity subjectEntity;

    @ManyToOne
    @JoinColumn(name = "tag_id", nullable = false)
    private TagEntity tagEntity;

//  public Integer getSubjId() {
//    return subjId;
//  }
//
//  public void setSubjId(Integer subjId) {
//    this.subjId = subjId;
//  }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SubjectEntity getSubjectEntity() {
        return subjectEntity;
    }

    public void setSubjectEntity(SubjectEntity subjectEntity) {
        this.subjectEntity = subjectEntity;
    }

    public TagEntity getTagEntity() {
        return tagEntity;
    }

    public void setTagEntity(TagEntity tagEntity) {
        this.tagEntity = tagEntity;
    }


}
