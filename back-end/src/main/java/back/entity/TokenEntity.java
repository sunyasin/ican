package back.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tokens")
public class TokenEntity {

    @Id
    @Column(name = "subj_id", nullable = false)
    private Integer subjId;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private Date updated;

    @OneToOne
    @JoinColumn(name = "subj_id", referencedColumnName = "id", nullable = false)
    private SubjectEntity user;

    @Override
    public String toString() {
        return "TokenEntity{" +
                "id=" + subjId +
                ", token='" + token + '\'' +
                ", updated=" + updated +
                ", user=" + user +
                '}';
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Integer getSubjId() {
        return subjId;
    }

    public void setSubjId(Integer subjId) {
        this.subjId = subjId;
    }

    public SubjectEntity getUser() {
        return user;
    }

    public void setUser(SubjectEntity user) {
        this.user = user;
    }
}
