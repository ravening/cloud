package com.rakeshv.hackathon.admindashboard.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A Csrp.
 */
@Entity
@Table(name = "csrp")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Csrp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "domain_name")
    private String domainName;

    @Column(name = "cpu", precision = 21, scale = 2)
    private BigDecimal cpu;

    @Column(name = "ram", precision = 21, scale = 2)
    private BigDecimal ram;

    @Column(name = "storage", precision = 21, scale = 2)
    private BigDecimal storage;

    @Column(name = "traffic", precision = 21, scale = 2)
    private BigDecimal traffic;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDomainName() {
        return domainName;
    }

    public Csrp domainName(String domainName) {
        this.domainName = domainName;
        return this;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public BigDecimal getCpu() {
        return cpu;
    }

    public Csrp cpu(BigDecimal cpu) {
        this.cpu = cpu;
        return this;
    }

    public void setCpu(BigDecimal cpu) {
        this.cpu = cpu;
    }

    public BigDecimal getRam() {
        return ram;
    }

    public Csrp ram(BigDecimal ram) {
        this.ram = ram;
        return this;
    }

    public void setRam(BigDecimal ram) {
        this.ram = ram;
    }

    public BigDecimal getStorage() {
        return storage;
    }

    public Csrp storage(BigDecimal storage) {
        this.storage = storage;
        return this;
    }

    public void setStorage(BigDecimal storage) {
        this.storage = storage;
    }

    public BigDecimal getTraffic() {
        return traffic;
    }

    public Csrp traffic(BigDecimal traffic) {
        this.traffic = traffic;
        return this;
    }

    public void setTraffic(BigDecimal traffic) {
        this.traffic = traffic;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Csrp)) {
            return false;
        }
        return id != null && id.equals(((Csrp) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Csrp{" +
            "id=" + getId() +
            ", domainName='" + getDomainName() + "'" +
            ", cpu=" + getCpu() +
            ", ram=" + getRam() +
            ", storage=" + getStorage() +
            ", traffic=" + getTraffic() +
            "}";
    }
}
