package io.jmix.uisamples.entity;

import io.jmix.core.metamodel.datatype.EnumClass;
import org.jspecify.annotations.Nullable;

public enum TerminalType implements EnumClass<String> {

    DOMESTIC("D"),
    INTERNATIONAL("I");

    private final String id;

    TerminalType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static TerminalType fromId(String id) {
        for (TerminalType at : TerminalType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}
