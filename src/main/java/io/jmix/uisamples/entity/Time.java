package io.jmix.uisamples.entity;

import io.jmix.core.metamodel.datatype.EnumClass;
import org.jspecify.annotations.Nullable;

public enum Time implements EnumClass<String> {

    LUNCH("lunch"),
    DINNER("dinner");

    private final String id;

    Time(String value) {
        this.id = value;
    }

    @Override
    public String getId() {
        return id;
    }

    @Nullable
    public static Time fromId(String id) {
        for (Time at : Time.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}
