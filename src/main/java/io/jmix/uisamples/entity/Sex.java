package io.jmix.uisamples.entity;

import io.jmix.core.metamodel.datatype.EnumClass;
import org.jspecify.annotations.Nullable;

public enum Sex implements EnumClass<String> {

    MALE("male"),
    FEMALE("female");

    private final String id;

    Sex(String value) {
        this.id = value;
    }

    @Override
    public String getId() {
        return id;
    }

    @Nullable
    public static Sex fromId(String id) {
        for (Sex at : Sex.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}
