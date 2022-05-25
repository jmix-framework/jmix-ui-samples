package io.jmix.sampler.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Table(name = "SAMPLER_COLOR")
@Entity(name = "sampler_Color")
@JmixEntity
public class Color {

    @Id
    @Column(name = "ID")
    @JmixGeneratedValue
    protected UUID id;

    @Column(name = "NAME")
    @InstanceName
    protected String name;

    @Column(name = "HEX")
    protected String hex;

    public Color() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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
