package io.jmix.uisamples.entity;

import io.jmix.core.metamodel.datatype.EnumClass;
import org.jspecify.annotations.Nullable;

public enum Day implements EnumClass<String> {

    SUNDAY("sun"),
    MONDAY("mon"),
    TUESDAY("tue"),
    WEDNESDAY("wed"),
    THURSDAY("thu"),
    FRIDAY("fri"),
    SATURDAY("sat");

    private final String id;

    Day(String value) {
        this.id = value;
    }

    @Override
    public String getId() {
        return id;
    }

    @Nullable
    public static Day fromId(String id) {
        for (Day day : Day.values()) {
            if (day.getId().equals(id)) {
                return day;
            }
        }
        return null;
    }
}