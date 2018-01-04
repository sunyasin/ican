package org.ideacreation.can.app.activity.fragment;

public enum MainTabEnum {
    PROFILE("Профиль"),
    LENTA("Лента"),
    MESSAGES("Сообщения"),
    SEARCH("Поиск");

    private String label;

    MainTabEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}