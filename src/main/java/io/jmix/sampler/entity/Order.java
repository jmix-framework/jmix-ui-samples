package io.jmix.sampler.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.InstanceName;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Table(name = "SAMPLER_ORDER")
@Entity(name = "sampler_Order")
public class Order {
    private static final long serialVersionUID = 5425074246531061156L;

    @Id
    @Column(name = "ID")
    @JmixGeneratedValue
    protected UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    protected Customer customer;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_", nullable = false)
    @InstanceName
    protected Date date;

    @Column(name = "AMOUNT")
    protected BigDecimal amount;

    @Column(name = "DESCRIPTION")
    protected String description;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "order")
    protected Set<OrderItem> items;

    public Order() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setItems(Set<OrderItem> items) {
        this.items = items;
    }

    public Set<OrderItem> getItems() {
        return items;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
