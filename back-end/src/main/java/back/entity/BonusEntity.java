package back.entity;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bonus")
public class BonusEntity {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id", nullable = false)
    private Integer id;

    //  @ManyToOne
//  @JoinColumn(name = "subj_id", referencedColumnName = "id", nullable = false)
//  private SubjectEntity user;
    @Column(name = "subj_id", nullable = false)
    private Integer subjId;

    @Column(name = "bonus_amount", nullable = false)
    private Integer bonusAmount;

    @Column(nullable = true)
    private Date created;

    @Column(name = "from_subj", nullable = false)
    private Integer bonusProviderId;

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

    public Integer getBonusAmount() {
        return bonusAmount;
    }

    public void setBonusAmount(Integer bonusAmount) {
        this.bonusAmount = bonusAmount;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Integer getBonusProviderId() {
        return bonusProviderId;
    }

    public void setBonusProviderId(Integer bonusProviderId) {
        this.bonusProviderId = bonusProviderId;
    }
}
