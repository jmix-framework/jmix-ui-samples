package io.jmix.sampler.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.JmixId;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.JmixProperty;

import java.util.Date;
import java.util.UUID;

@JmixEntity
public class DateValueVolume {
    @JmixGeneratedValue
    @JmixId
    private UUID id;

    @JmixProperty(mandatory = true)
    private Date date;

    @JmixProperty(mandatory = true)
    private Long value;

    @JmixProperty(mandatory = true)
    private Long volume;

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @InstanceName
    @DependsOnProperties({"date", "value", "volume"})
    public String getInstanceName() {
        return String.format("%s %s %s", date, value, volume);
    }

    @Override
    public String toString() {
        return "DateValueVolume{" +
                "date=" + date +
                ", value=" + value +
                ", volume=" + volume +
                '}';
    }
}