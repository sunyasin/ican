package org.ideacreation.can.common.model.json;


import com.fasterxml.jackson.annotation.JsonFormat;

import org.ideacreation.can.common.model.enums.ProfileType;

import java.util.Date;
import java.util.List;

public class SubjectInfo extends SubjectInfoForUpdate {

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date created;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date updated;

    private ProfileType type;

    private List<SubjectInfoShort> subscriptionList;

    public List<SubjectInfoShort> getSubscriptionList() {
        return subscriptionList;
    }

    public void setSubscriptionList(List<SubjectInfoShort> subscriptionList) {
        this.subscriptionList = subscriptionList;
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


    public ProfileType getType() {
        return type;
    }

    public void setType(ProfileType type) {
        this.type = type;
    }

}
