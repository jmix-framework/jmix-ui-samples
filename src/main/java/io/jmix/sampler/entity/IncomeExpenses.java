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
@Table(name = "SAMPLER_INCOME_EXPENSES")
@Entity(name = "sampler_IncomeExpenses")
public class IncomeExpenses {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @NotNull
    @Column(name = "YEAR_", nullable = false)
    private Integer year;

    @NotNull
    @Column(name = "INCOME", nullable = false)
    private Double income;

    @NotNull
    @Column(name = "EXPENSES", nullable = false)
    private Double expenses;

    @Column(name = "ALPHA")
    private Double alpha;

    @Column(name = "DASH_LENGTH_LINE")
    private Integer dashLengthLine;

    @Column(name = "DASH_LENGTH_COLUMN")
    private Integer dashLengthColumn;

    @Column(name = "ADDITIONAL")
    private String additional;

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    public Integer getDashLengthColumn() {
        return dashLengthColumn;
    }

    public void setDashLengthColumn(Integer dashLengthColumn) {
        this.dashLengthColumn = dashLengthColumn;
    }

    public Integer getDashLengthLine() {
        return dashLengthLine;
    }

    public void setDashLengthLine(Integer dashLengthLine) {
        this.dashLengthLine = dashLengthLine;
    }

    public Double getAlpha() {
        return alpha;
    }

    public void setAlpha(Double alpha) {
        this.alpha = alpha;
    }

    public Double getExpenses() {
        return expenses;
    }

    public void setExpenses(Double expenses) {
        this.expenses = expenses;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
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