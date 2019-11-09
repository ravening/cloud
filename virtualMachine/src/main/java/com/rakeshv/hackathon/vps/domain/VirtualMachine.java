package com.rakeshv.hackathon.vps.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A VirtualMachine.
 */
@Entity
@Table(name = "virtual_machine")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class VirtualMachine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "accountid", precision = 21, scale = 2)
    private BigDecimal accountid;

    @Column(name = "account_name")
    private String accountName;

    @Column(name = "cpu", precision = 21, scale = 2)
    private BigDecimal cpu;

    @Column(name = "ram", precision = 21, scale = 2)
    private BigDecimal ram;

    @Column(name = "disk", precision = 21, scale = 2)
    private BigDecimal disk;

    @Column(name = "traffic", precision = 21, scale = 2)
    private BigDecimal traffic;

    @Column(name = "template")
    private String template;

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

    public VirtualMachine name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAccountid() {
        return accountid;
    }

    public VirtualMachine accountid(BigDecimal accountid) {
        this.accountid = accountid;
        return this;
    }

    public void setAccountid(BigDecimal accountid) {
        this.accountid = accountid;
    }

    public String getAccountName() {
        return accountName;
    }

    public VirtualMachine accountName(String accountName) {
        this.accountName = accountName;
        return this;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public BigDecimal getCpu() {
        return cpu;
    }

    public VirtualMachine cpu(BigDecimal cpu) {
        this.cpu = cpu;
        return this;
    }

    public void setCpu(BigDecimal cpu) {
        this.cpu = cpu;
    }

    public BigDecimal getRam() {
        return ram;
    }

    public VirtualMachine ram(BigDecimal ram) {
        this.ram = ram;
        return this;
    }

    public void setRam(BigDecimal ram) {
        this.ram = ram;
    }

    public BigDecimal getDisk() {
        return disk;
    }

    public VirtualMachine disk(BigDecimal disk) {
        this.disk = disk;
        return this;
    }

    public void setDisk(BigDecimal disk) {
        this.disk = disk;
    }

    public BigDecimal getTraffic() {
        return traffic;
    }

    public VirtualMachine traffic(BigDecimal traffic) {
        this.traffic = traffic;
        return this;
    }

    public void setTraffic(BigDecimal traffic) {
        this.traffic = traffic;
    }

    public String getTemplate() {
        return template;
    }

    public VirtualMachine template(String template) {
        this.template = template;
        return this;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VirtualMachine)) {
            return false;
        }
        return id != null && id.equals(((VirtualMachine) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "VirtualMachine{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", accountid=" + getAccountid() +
            ", accountName='" + getAccountName() + "'" +
            ", cpu=" + getCpu() +
            ", ram=" + getRam() +
            ", disk=" + getDisk() +
            ", traffic=" + getTraffic() +
            ", template='" + getTemplate() + "'" +
            "}";
    }
}
