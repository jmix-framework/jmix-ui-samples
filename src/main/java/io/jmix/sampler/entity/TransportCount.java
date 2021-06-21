package io.jmix.sampler.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@JmixEntity
@Table(name = "SAMPLER_TRANSPORT_COUNT")
@Entity(name = "sampler_TransportCount")
public class TransportCount {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @NotNull
    @Column(name = "YEAR_", nullable = false)
    private Integer year;

    @NotNull
    @Column(name = "CARS", nullable = false)
    private Integer cars;

    @NotNull
    @Column(name = "MOTORCYCLES", nullable = false)
    private Integer motorcycles;

    @NotNull
    @Column(name = "BICYCLES", nullable = false)
    private Integer bicycles;

    public Integer getBicycles() {
        return bicycles;
    }

    public void setBicycles(Integer bicycles) {
        this.bicycles = bicycles;
    }

    public Integer getMotorcycles() {
        return motorcycles;
    }

    public void setMotorcycles(Integer motorcycles) {
        this.motorcycles = motorcycles;
    }

    public Integer getCars() {
        return cars;
    }

    public void setCars(Integer cars) {
        this.cars = cars;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}