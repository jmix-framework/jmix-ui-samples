package io.jmix.sampler.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.maps.Geometry;
import io.jmix.maps.converter.wkt.PointWKTConverter;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;
import java.util.UUID;

@JmixEntity
@Table(name = "SAMPLER_LOCATION")
@Entity(name = "sampler_Location")
public class Location {

    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "POINT")
    @Geometry
    @Convert(converter = PointWKTConverter.class)
    protected Point point;

    @Column(name = "INTENSITY")
    private Double intensity;

    public Double getIntensity() {
        return intensity;
    }

    public void setIntensity(Double intensity) {
        this.intensity = intensity;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}