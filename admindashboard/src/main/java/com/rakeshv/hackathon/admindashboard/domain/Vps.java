package com.rakeshv.hackathon.admindashboard.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A Vps.
 */
@Entity
@Table(name = "vps")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Vps implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

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

    public Vps name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCpu() {
        return cpu;
    }

    public Vps cpu(BigDecimal cpu) {
        this.cpu = cpu;
        return this;
    }

    public void setCpu(BigDecimal cpu) {
        this.cpu = cpu;
    }

    public BigDecimal getRam() {
        return ram;
    }

    public Vps ram(BigDecimal ram) {
        this.ram = ram;
        return this;
    }

    public void setRam(BigDecimal ram) {
        this.ram = ram;
    }

    public BigDecimal getDisk() {
        return disk;
    }

    public Vps disk(BigDecimal disk) {
        this.disk = disk;
        return this;
    }

    public void setDisk(BigDecimal disk) {
        this.disk = disk;
    }

    public BigDecimal getTraffic() {
        return traffic;
    }

    public Vps traffic(BigDecimal traffic) {
        this.traffic = traffic;
        return this;
    }

    public void setTraffic(BigDecimal traffic) {
        this.traffic = traffic;
    }

    public String getTemplate() {
        return template;
    }

    public Vps template(String template) {
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
        if (!(o instanceof Vps)) {
            return false;
        }
        return id != null && id.equals(((Vps) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Vps{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", cpu=" + getCpu() +
            ", ram=" + getRam() +
            ", disk=" + getDisk() +
            ", traffic=" + getTraffic() +
            ", template='" + getTemplate() + "'" +
            "}";
    }
}
