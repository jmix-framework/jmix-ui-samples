package io.jmix.sampler.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Table(name = "SAMPLER_CALENDAR_EVENT")
@Entity(name = "sampler_CalendarEvent")
@JmixEntity
public class CalendarEvent {

    private static final long serialVersionUID = 8854732567865981389L;

    @Id
    @Column(name = "ID")
    @JmixGeneratedValue
    protected UUID id;

    @InstanceName
    @Column(name = "CAPTION")
    protected String caption;

    @Column(name = "DESCRIPTION")
    protected String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "START_DATE")
    protected Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END_DATE")
    protected Date endDate;

    @Column(name = "STYLENAME")
    protected String stylename;

    public CalendarEvent() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStylename() {
        return stylename;
    }

    public void setStylename(String stylename) {
        this.stylename = stylename;
    }
}
