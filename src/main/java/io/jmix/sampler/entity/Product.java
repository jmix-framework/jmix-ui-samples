package io.jmix.sampler.entity;

import io.jmix.core.entity.annotation.CurrencyValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.data.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Table(name = "SAMPLER_PRODUCT")
@Entity(name = "sampler_Product")
public class Product extends StandardEntity {
    private static final long serialVersionUID = 4256660269544840258L;

    @Column(name = "NAME", nullable = false)
    @InstanceName
    protected String name;

    @Column(name = "PRICE", nullable = false)
    @CurrencyValue(currency = "$")
    protected BigDecimal price;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
