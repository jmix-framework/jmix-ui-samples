package io.jmix.sampler.bean;

import io.jmix.data.persistence.DbmsType;
import org.springframework.orm.jpa.vendor.Database;

public class SamplerDbmsType extends DbmsType {

    @Override
    public String getType(String storeName) {
        return Database.HSQL.name();
    }
}
