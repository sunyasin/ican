package org.ideacreation.can.common.model.json;

/**

 */
public class ProfileItem {
    Integer profileId;
    String name;
    String imageFile;
    Boolean isFavorite;
    Integer unreadCount;

    @Override
    public String toString() {
        return "ProfileItem{" +
                "profileId=" + profileId +
                ", name='" + name + '\'' +
                ", imageFile='" + imageFile + '\'' +
                ", isFavorite=" + isFavorite +
                ", unreadCount=" + unreadCount +
                '}';
    }

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public Boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
    }

    public Integer getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(Integer unreadCount) {
        this.unreadCount = unreadCount;
    }
}
