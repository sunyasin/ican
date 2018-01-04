package org.ideacreation.can.common.model.enums;

/**
 * типы групп в ленте
 */
public enum GroupType {
    PRIVATE,
    BOOKMARKED,
    SUBSCRIBED,
    TAGGED;

    public static GroupType getByInt(int id) throws IllegalArgumentException {
        switch (id) {
            case 0:
                return PRIVATE;
            case 1:
                return BOOKMARKED;
            case 2:
                return SUBSCRIBED;
            case 3:
                return TAGGED;
        }
        throw new IllegalArgumentException("no GroupType enum value for requested id " + id);
    }
}
