package io.jmix.sampler.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@JmixEntity
@Table(name = "SAMPLER_TIP_INFO")
@Entity(name = "sampler_TipInfo")
public class TipInfo {

    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @NotNull
    @Column(name = "TOTAL_BILL", nullable = false, precision = 19, scale = 2)
    private BigDecimal totalBill;

    @NotNull
    @Column(name = "TIP", nullable = false, precision = 19, scale = 2)
    private BigDecimal tip;

    @NotNull
    @Column(name = "SMOKER", nullable = false)
    private Boolean smoker = false;

    @NotNull
    @Column(name = "SIZE_", nullable = false)
    private Integer size;

    @NotNull
    @Column(name = "SEX", nullable = false)
    private String sex;

    @NotNull
    @Column(name = "DAY_", nullable = false)
    private String day;

    @NotNull
    @Column(name = "TIME_", nullable = false)
    private String time;

    public Time getTime() {
        return time == null ? null : Time.fromId(time);
    }

    public void setTime(Time time) {
        this.time = time == null ? null : time.getId();
    }

    public Day getDay() {
        return day == null ? null : Day.fromId(day);
    }

    public void setDay(Day day) {
        this.day = day == null ? null : day.getId();
    }

    public Sex getSex() {
        return sex == null ? null : Sex.fromId(sex);
    }

    public void setSex(Sex sex) {
        this.sex = sex == null ? null : sex.getId();
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Boolean getSmoker() {
        return smoker;
    }

    public void setSmoker(Boolean smoker) {
        this.smoker = smoker;
    }

    public BigDecimal getTip() {
        return tip;
    }

    public void setTip(BigDecimal tip) {
        this.tip = tip;
    }

    public BigDecimal getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(BigDecimal totalBill) {
        this.totalBill = totalBill;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}