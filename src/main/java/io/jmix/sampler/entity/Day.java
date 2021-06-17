package io.jmix.sampler.entity;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;

public enum Day implements EnumClass<String> {

    SUNDAY("sun"),
    MONDAY("mon"),
    TUESDAY("tue"),
    WEDNESDAY("wed"),
    THURSDAY("thu"),
    FRIDAY("fri"),
    SATURDAY("sat");

    private String id;

    Day(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static Day fromId(String id) {
        for (Day at : Day.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}