package com.detect.amar.simple.db;

import com.detect.amar.simple.db.declaration.SField;
import com.detect.amar.simple.db.declaration.SFieldConstraint;
import com.detect.amar.simple.db.declaration.SFieldType;
import com.detect.amar.simple.db.declaration.STable;

/**
 * Created by amar on 15/8/25.
 */

@STable("messageRecord")
public class MessageExample {

    @SField(name = "id", type = SFieldType.Text, constraint = SFieldConstraint.IsPrimaryKeyAutoincrement)
    private String id;
    @SField(name = "fromNumber", type = SFieldType.Text, constraint = SFieldConstraint.NotAllow)
    private String fromNumber;
    @SField(name = "toNumber", type = SFieldType.Text, constraint = SFieldConstraint.NotAllow)
    private String toNumber;
    @SField(name = "info", type = SFieldType.Text, constraint = SFieldConstraint.NotAllow)
    private String info;
    @SField(name = "originDate", type = SFieldType.Date, constraint = SFieldConstraint.NotAllow)
    private String originDate;
    @SField(name = "receiveDate", type = SFieldType.Date, constraint = SFieldConstraint.NotAllow)
    private String receiveDate;
    @SField(name = "sign", type = SFieldType.Text, constraint = SFieldConstraint.NotAllow)
    private String sign;
    @SField(name = "isTrans", type = SFieldType.Boolean, constraint = SFieldConstraint.AllowNull)
    private String isTrans;
    @SField(name = "sendDate", type = SFieldType.Date, constraint = SFieldConstraint.AllowNull)
    private String sendDate;


}
