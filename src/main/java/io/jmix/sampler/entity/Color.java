package io.jmix.sampler.entity;

import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.data.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "SAMPLER_COLOR")
@Entity(name = "sampler_Color")
public class Color extends StandardEntity {
    private static final long serialVersionUID = -1981383843541262219L;

    @Column(name = "NAME")
    @InstanceName
    protected String name;

    @Column(name = "HEX")
    protected String hex;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public String getHex() {
        return hex;
    }
}
