package io.jmix.sampler.entity;

import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.data.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Table(name = "SAMPLER_ORDER_ITEM")
@Entity(name = "sampler_OrderItem")
public class OrderItem extends StandardEntity {
    private static final long serialVersionUID = 7404462282086544503L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    protected Product product;

    @Column(name = "QUANTITY", precision = 19, scale = 3)
    protected BigDecimal quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    protected Order order;

    @InstanceName
    public String getInstanceName() {
        return product + " " + quantity;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }
}
