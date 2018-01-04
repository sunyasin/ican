package org.ideacreation.can.app.adapter;

/**
 */

public class ProfileListRowItem {
    private Integer profileId;
    private String name;
    private String imageFile;
    private boolean isFavorite;
    private Integer unreadCount;

    @Override
    public String toString() {
        return "ProfileListRowItem{" +
                "profileId=" + profileId +
                ", name='" + name + '\'' +
                ", imageFile='" + imageFile + '\'' +
                ", isBookmarked=" + isFavorite +
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

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public Integer getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(Integer unreadCount) {
        this.unreadCount = unreadCount;
    }
}
