package com.detect.amar.simple.db.parse;

import com.detect.amar.simple.db.declaration.SField;
import com.detect.amar.simple.db.declaration.SFieldConstraint;
import com.detect.amar.simple.db.declaration.SFieldType;
import com.detect.amar.simple.db.declaration.STable;
import com.detect.amar.simple.db.ecxeption.SParseException;

import java.lang.reflect.Field;

/**
 * Created by amar on 15/8/25.
 */
public class ParseClassForSql {

    public String createTable(Class tableClass) {
        String sql = "";

        return sql;
    }

    public <T> void analyzeClass(Class<T> tableClass) throws SParseException {

        StringBuilder sql = new StringBuilder("");
        if (tableClass.isAnnotationPresent(STable.class)) {

            STable sTable = tableClass.getAnnotation(STable.class);
            System.out.println("is stable:"+sTable.value());

            Field[] fields = tableClass.getDeclaredFields();
            for (Field field : fields) {
                if(field.isAnnotationPresent( SField.class)){
                    SField sField = field.getAnnotation(SField.class);
                    String defaultValue = sField.defaultValue();
                    SFieldConstraint sFieldConstraint =  sField.constraint();
                    SFieldType sFieldType = sField.type();
                    String name = sField.name();
                    System.out.println(name+","+sFieldConstraint.toString()+","+sFieldType.toString()+","+defaultValue);
                }
                else {
                    System.out.println("no");
                }
            }

        } else {
            System.out.println("is not stable");
        }

    }
}
