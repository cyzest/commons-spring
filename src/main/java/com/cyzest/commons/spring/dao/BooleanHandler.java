package com.cyzest.commons.spring.dao;

import com.cyzest.commons.spring.model.BooleanFlag;
import org.apache.ibatis.type.MappedTypes;

/**
 * MyBatis BooleanFlag Handler
 */
@MappedTypes(BooleanFlag.class)
public class BooleanHandler extends EnumTypeHandler<BooleanFlag> {

    public BooleanHandler() {
        super(BooleanFlag.class);
    }

}
