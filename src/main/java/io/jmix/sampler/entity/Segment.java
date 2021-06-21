package io.jmix.sampler.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import java.util.UUID;

@JmixEntity
@Table(name = "SAMPLER_SEGMENT")
@Entity(name = "sampler_Segment")
public class Segment {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "START_")
    private Integer start;

    @Column(name = "DURATION")
    private Integer duration;

    @Column(name = "COLOR")
    private String color;

    @Column(name = "TASK_")
    private String task;

    @Column(name = "INDEX_")
    private Integer index;

    @JoinColumn(name = "TASK_SPAN_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private TaskSpan taskSpan;

    public TaskSpan getTaskSpan() {
        return taskSpan;
    }

    public void setTaskSpan(TaskSpan taskSpan) {
        this.taskSpan = taskSpan;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}