package com.app.vpgroup.ghinhocungmemopad.model;

import com.orm.SugarRecord;

import java.io.Serializable;

public class MemoPad extends SugarRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    public long created_date;
    public long update_date;
    public String content;
    public boolean deleted;


    public MemoPad() {
        super();
    }

    public MemoPad(String content) {
        this.content = content;
    }
}
