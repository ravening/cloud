package com.rakeshv.hackathon.orders.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A OrderCsrp.
 */
@Entity
@Table(name = "order_csrp")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OrderCsrp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "csrppack", precision = 21, scale = 2)
    private BigDecimal csrppack;

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

    public OrderCsrp name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCsrppack() {
        return csrppack;
    }

    public OrderCsrp csrppack(BigDecimal csrppack) {
        this.csrppack = csrppack;
        return this;
    }

    public void setCsrppack(BigDecimal csrppack) {
        this.csrppack = csrppack;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderCsrp)) {
            return false;
        }
        return id != null && id.equals(((OrderCsrp) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OrderCsrp{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", csrppack=" + getCsrppack() +
            "}";
    }
}
