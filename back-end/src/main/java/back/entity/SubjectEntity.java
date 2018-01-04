package back.entity;

import org.hibernate.annotations.GenericGenerator;
import org.ideacreation.can.common.model.enums.ProfileType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "subj")
public class SubjectEntity {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String address;

    @Column
    private Date created;

    @Column
    private Date updated;

    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String pass;

    @Column
    private String email;

    @Column
    private String description;

    @Column
    private String contacts;

    @Column(name = "auto_bonus_active")
    private Boolean isAutoBonusActive;

    @Enumerated
    @Column(nullable = false)
    private ProfileType type;

    @Column(nullable = true)
    private String picture;

    @OneToMany(mappedBy = "subjectEntity", cascade = CascadeType.MERGE, orphanRemoval = false, fetch = FetchType.EAGER)
    private List<SubscribedTagEntity> tagList = new ArrayList<>(0);

    @OneToMany(mappedBy = "subscriber", cascade = CascadeType.MERGE, orphanRemoval = false, fetch = FetchType.EAGER)
    private List<SubscribedEntity> subscriptionList = new ArrayList<>(0);

/* todo add later
  partner_id INTEGER, -- ид партнера
  partner_code CHARACTER VARYING(16)

  @Column(name="enabled", nullable = false)
  private Boolean enabled;

  @Column(name="is_pwd_valid", nullable = false)
  private Boolean isPasswordValid;

  @Column(name="date_joined", nullable = false)
  private Date dateJoined;
  */

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public ProfileType getType() {
        return type;
    }

    public void setType(ProfileType type) {
        this.type = type;
    }

    public List<SubscribedEntity> getSubscriptionList() {
        return subscriptionList;
    }

    public void setSubscriptionList(List<SubscribedEntity> subscriptionList) {
        this.subscriptionList = subscriptionList;
    }

    public Boolean getAutoBonusActive() {
        return isAutoBonusActive;
    }

    public void setAutoBonusActive(Boolean autoBonusActive) {
        isAutoBonusActive = autoBonusActive;
    }

    public List<SubscribedTagEntity> getTagList() {
        return tagList;
    }

    public void setTagList(List<SubscribedTagEntity> tagList) {
        this.tagList = tagList;
    }
}
