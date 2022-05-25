package io.jmix.sampler.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Table(name = "SAMPLER_TASK")
@Entity(name = "sampler_Task")
@JmixEntity
public class Task {

    @Id
    @Column(name = "ID")
    @JmixGeneratedValue
    protected UUID id;

    @InstanceName
    @Column(name = "NAME", nullable = false)
    protected String name;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DUE_DATE")
    protected Date dueDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ASSIGNEE_ID")
    protected Customer assignee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_TASK_ID")
    protected Task parentTask;

    public Task() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setParentTask(Task parentTask) {
        this.parentTask = parentTask;
    }

    public Task getParentTask() {
        return parentTask;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setAssignee(Customer assignee) {
        this.assignee = assignee;
    }

    public Customer getAssignee() {
        return assignee;
    }
}
