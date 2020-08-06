package io.jmix.sampler.entity;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;

public enum CalendarEventStyle implements EnumClass<String> {

    RED("event-red"),
    GREEN("event-green"),
    BLUE("event-blue"),
    YELLOW("event-yellow");

    private String id;

    CalendarEventStyle(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static CalendarEventStyle fromId(String id) {
        for (CalendarEventStyle at : CalendarEventStyle.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}
