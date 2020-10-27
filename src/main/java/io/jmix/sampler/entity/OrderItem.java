package io.jmix.sampler.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "SAMPLER_ORDER_ITEM")
@Entity(name = "sampler_OrderItem")
@JmixEntity
public class OrderItem {
    private static final long serialVersionUID = 7404462282086544503L;

    @Id
    @Column(name = "ID")
    @JmixGeneratedValue
    protected UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    protected Product product;

    @Column(name = "QUANTITY", precision = 19, scale = 3)
    protected BigDecimal quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    protected Order order;

    public OrderItem() {
        this.id = UUID.randomUUID();
    }

    @InstanceName
    public String getInstanceName() {
        return product.getName() + " " + quantity;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
