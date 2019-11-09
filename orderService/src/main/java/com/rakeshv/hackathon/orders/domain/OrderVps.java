package com.rakeshv.hackathon.orders.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A OrderVps.
 */
@Entity
@Table(name = "order_vps")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OrderVps implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "vpspack", precision = 21, scale = 2)
    private BigDecimal vpspack;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public OrderVps name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getVpspack() {
        return vpspack;
    }

    public OrderVps vpspack(BigDecimal vpspack) {
        this.vpspack = vpspack;
        return this;
    }

    public void setVpspack(BigDecimal vpspack) {
        this.vpspack = vpspack;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderVps)) {
            return false;
        }
        return id != null && id.equals(((OrderVps) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OrderVps{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", vpspack=" + getVpspack() +
            "}";
    }
}
