package io.jmix.uisamples.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.jspecify.annotations.Nullable;

import java.time.LocalDate;
import java.util.UUID;

@Table(name = "KANBAN_TASK", indexes = {
        @Index(name = "IDX_KANBAN_TASK_ASSIGNEE", columnList = "ASSIGNEE_ID")
})
@Entity
@JmixEntity
public class KanbanTask {

    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @InstanceName
    @Column(name = "NAME", nullable = false)
    @NotNull
    private String name;

    @Column(name = "STATUS", nullable = false)
    @NotNull
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ASSIGNEE_ID")
    private KanbanUser assignee;

    @Column(name = "COLOR")
    private String color;

    @Max(100)
    @Min(0)
    @Column(name = "PROGRESS")
    private Integer progress;

    @Column(name = "PRIORITY")
    private String priority;

    @Column(name = "DUE_DATE")
    private LocalDate dueDate;

    @Column(name = "TAGS")
    private String tags;

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    @Nullable
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public KanbanUser getAssignee() {
        return assignee;
    }

    public void setAssignee(KanbanUser assignee) {
        this.assignee = assignee;
    }

    @Nullable
    public TaskPriority getPriority() {
        return priority == null ? null : TaskPriority.fromId(priority);
    }

    public void setPriority(@Nullable TaskPriority priority) {
        this.priority = priority == null ? null : priority.getId();
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Nullable
    public String getTags() {
        return tags;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public TaskStatus getStatus() {
        return status == null ? null : TaskStatus.fromId(status);
    }

    public void setStatus(TaskStatus status) {
        this.status = status.getId();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
