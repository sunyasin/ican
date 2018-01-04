package org.ideacreation.can.app.activity.fragment;

public enum TabState {
    LENTA_GROUPED("Группе"),
    LENTA_GROUP_MESSAGES("сообщения в Группе"),
    LENTA_GROUP_PROFILES("Профайлы в группе"),
    LENTA_BOOKMARKED("Закладки"),
    LENTA_SUBSCRIBED_PROFILES("Профили в Подписках"),
    LENTA_SUBSCRIBED_MESSAGES("Сообщения в Подписках"),
    LENTA_TAG_PROFILES("профайлы по тэгу"),
    LENTA_TAG_MESSAGES("Сообщения по тэгу"),
    VIEW_MESSAGE("просмотр сообщения"),
    PROFILE_BUSSINESS_SELF(""),
    PROFILE_BUSSINESS_GUEST(""),
    PROFILE_PERSON_SELF(""),
    PROFILE_PERSON_GUEST("");

    private String label;

    TabState(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}