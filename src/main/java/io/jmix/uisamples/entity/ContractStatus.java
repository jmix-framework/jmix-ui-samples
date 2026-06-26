package io.jmix.uisamples.entity;

import io.jmix.core.metamodel.datatype.EnumClass;
import org.jspecify.annotations.Nullable;

public enum ContractStatus implements EnumClass<String> {

    DRAFT("D"),
    APPROVED("A"),
    REJECTED("R");

    private final String id;

    ContractStatus(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static ContractStatus fromId(String id) {
        for (ContractStatus at : ContractStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}