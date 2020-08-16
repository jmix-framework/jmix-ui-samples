package io.jmix.sampler.entity;

import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.data.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "SAMPLER_COUNTRY_GROWTH")
@Entity(name = "sampler_CountryGrowth")
public class CountryGrowth extends StandardEntity {
    private static final long serialVersionUID = 5590699887119355446L;

    @InstanceName
    @Column(name = "COUNTRY", nullable = false)
    protected String country;

    @Column(name = "FLAG")
    private String flag;

    @Column(name = "YEAR2014", nullable = false)
    protected Double year2014;

    @Column(name = "YEAR2015", nullable = false)
    protected Double year2015;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setYear2014(Double year2014) {
        this.year2014 = year2014;
    }

    public Double getYear2014() {
        return year2014;
    }

    public void setYear2015(Double year2015) {
        this.year2015 = year2015;
    }

    public Double getYear2015() {
        return year2015;
    }
}
