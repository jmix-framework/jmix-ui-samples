package io.jmix.sampler.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@JmixEntity
@Table(name = "SAMPLER_DATE_SEGMENT")
@Entity(name = "sampler_DateSegment")
public class DateSegment {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "START_")
    @Temporal(TemporalType.DATE)
    private Date start;

    @Column(name = "END_")
    @Temporal(TemporalType.DATE)
    private Date end;

    @Column(name = "COLOR")
    private String color;

    @Column(name = "TASK_")
    private String task;

    @JoinColumn(name = "DATE_TASK_SPAN_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private DateTaskSpan dateTaskSpan;

    public DateTaskSpan getDateTaskSpan() {
        return dateTaskSpan;
    }

    public void setDateTaskSpan(DateTaskSpan dateTaskSpan) {
        this.dateTaskSpan = dateTaskSpan;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}