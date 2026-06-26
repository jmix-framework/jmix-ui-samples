package io.jmix.uisamples.entity;

import io.jmix.core.FileRef;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.jspecify.annotations.Nullable;

import java.util.UUID;

@Table(name = "KANBAN_USER")
@Entity
@JmixEntity
public class KanbanUser {

    @Id
    @Column(name = "ID", nullable = false)
    @JmixGeneratedValue
    private UUID id;

    @InstanceName
    @Column(name = "NAME", length = 50, nullable = false)
    private String name;

    @Column(name = "AVATAR", length = 1024)
    private FileRef avatar;

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public FileRef getAvatar() {
        return avatar;
    }

    public void setAvatar(FileRef avatar) {
        this.avatar = avatar;
    }
}
