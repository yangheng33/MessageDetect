package com.detect.amar.simple.db.condition;

import android.support.v4.util.ArrayMap;

import java.util.Date;

/**
 * Created by SAM on 2015/8/24.
 */
public class SingleCondition {
    private String fieldName;
    private ArrayMap conditionMap = new ArrayMap();

    public SingleCondition() {
    }

    public SingleCondition(String fieldName) {
        this.fieldName = fieldName;
    }

    // long
    public SingleCondition between(long param1, long param2) {
        return this;
    }

    public SingleCondition greatThan(long param) {
        return this;
    }

    public SingleCondition lessThan(long param) {
        return this;
    }

    public SingleCondition equal(long param) {
        return this;
    }

    // float
    public SingleCondition between(float param1, float param2) {
        return this;
    }

    public SingleCondition greatThan(float param) {
        return this;
    }

    public SingleCondition lessThan(float param) {
        return this;
    }

    public SingleCondition equal(float param) {
        return this;
    }

    // date
    public SingleCondition between(Date param1, Date param2) {
        return between(param1.getTime(),param2.getTime());
    }

    public SingleCondition greatThan(Date param) {
        return greatThan(param.getTime());
    }

    public SingleCondition lessThan(Date param) {
        return lessThan(param.getTime());
    }

    public SingleCondition equal(Date param) {
        return equal(param.getTime());
    }
    // text
    public SingleCondition like(String param) {
        return this;
    }

    public SingleCondition equal(String param) {
        return this;
    }


    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String toSQL() {
        return "";
    }
}
