package com.detect.amar.simple.db.condition;

/**
 * Created by SAM on 2015/8/24.
 */
public class ComplexCondition {
    public ComplexCondition and(SingleCondition singleCondition) {
        return this;
    }

    public ComplexCondition or(SingleCondition singleCondition) {
        return this;
    }
}
