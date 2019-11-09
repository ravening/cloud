package com.rakeshv.hackathon.csrp.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A PrivateCloud.
 */
@Entity
@Table(name = "private_cloud")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PrivateCloud implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "domain_name")
    private String domainName;

    @Column(name = "accountid", precision = 21, scale = 2)
    private BigDecimal accountid;

    @Column(name = "account_name")
    private String accountName;

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

    public PrivateCloud domainName(String domainName) {
        this.domainName = domainName;
        return this;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public BigDecimal getAccountid() {
        return accountid;
    }

    public PrivateCloud accountid(BigDecimal accountid) {
        this.accountid = accountid;
        return this;
    }

    public void setAccountid(BigDecimal accountid) {
        this.accountid = accountid;
    }

    public String getAccountName() {
        return accountName;
    }

    public PrivateCloud accountName(String accountName) {
        this.accountName = accountName;
        return this;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public BigDecimal getCpu() {
        return cpu;
    }

    public PrivateCloud cpu(BigDecimal cpu) {
        this.cpu = cpu;
        return this;
    }

    public void setCpu(BigDecimal cpu) {
        this.cpu = cpu;
    }

    public BigDecimal getRam() {
        return ram;
    }

    public PrivateCloud ram(BigDecimal ram) {
        this.ram = ram;
        return this;
    }

    public void setRam(BigDecimal ram) {
        this.ram = ram;
    }

    public BigDecimal getStorage() {
        return storage;
    }

    public PrivateCloud storage(BigDecimal storage) {
        this.storage = storage;
        return this;
    }

    public void setStorage(BigDecimal storage) {
        this.storage = storage;
    }

    public BigDecimal getTraffic() {
        return traffic;
    }

    public PrivateCloud traffic(BigDecimal traffic) {
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
        if (!(o instanceof PrivateCloud)) {
            return false;
        }
        return id != null && id.equals(((PrivateCloud) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PrivateCloud{" +
            "id=" + getId() +
            ", domainName='" + getDomainName() + "'" +
            ", accountid=" + getAccountid() +
            ", accountName='" + getAccountName() + "'" +
            ", cpu=" + getCpu() +
            ", ram=" + getRam() +
            ", storage=" + getStorage() +
            ", traffic=" + getTraffic() +
            "}";
    }
}
