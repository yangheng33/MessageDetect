package com.detect.amar.simple.db.parse;

import com.detect.amar.simple.db.declaration.SFieldConstraint;
import com.detect.amar.simple.db.declaration.SFieldType;

/**
 * Created by amar on 15/8/25.
 */
public class FieldTemplate {

    private String fieldName;
    private SFieldConstraint sFieldConstraint;
    private SFieldType sFieldType;
    private String defaultValue;

    public String parseDDL()
    {
        StringBuilder ddl = new StringBuilder("");

        ddl.append(fieldName).append(" ");



        return ddl.toString();
    }

    public String parseDefault() {
        String def = "";
        switch (sFieldType) {
            case Boolean:
                def = defaultValue;
                break;

            case Date:
                // TODO: 15/8/25
                break;

            case Text:
                def = "\'" + defaultValue +"\'";
                break;

            case Int:
                def = defaultValue;
                break;

            case Float:
                def = defaultValue;
                break;
        }
        return def;
    }

}
