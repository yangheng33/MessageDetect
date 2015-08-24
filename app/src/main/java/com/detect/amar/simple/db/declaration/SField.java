package com.detect.amar.simple.db.declaration;

/**
 * Created by SAM on 2015/8/24.
 */
public @interface SField {
    String name() default "";
    SFieldType type() default SFieldType.Text;
    SFieldConstraint constraint() default SFieldConstraint.AllowNull;
    String defaultValue() default "";
}
