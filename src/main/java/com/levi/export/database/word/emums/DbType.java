package com.levi.export.database.word.emums;

import java.util.ArrayList;
import java.util.List;

public enum DbType {
    MYSQL(1, "MySql");
    int dbType;
    String dbDesc;

    DbType(int dbType, String dbDesc) {
        this.dbType = dbType;
        this.dbDesc = dbDesc;
    }

    public int getDbType() {
        return dbType;
    }

    public void setDbType(int dbType) {
        this.dbType = dbType;
    }

    public String getDbDesc() {
        return dbDesc;
    }

    public void setDbDesc(String dbDesc) {
        this.dbDesc = dbDesc;
    }

    public static DbType getDbTypeByType(int dbType){
        for (DbType value : values()) {
            if (value.dbType==dbType){
                return value;
            }
        }
        return null;
    }

    public static DbType getDbTypeByDesc(String dbDesc){
        for (DbType value : values()) {
            if (value.dbDesc.equals(dbDesc)){
                return value;
            }
        }
        return null;
    }

    public static List<String> getListDesc(){
        List<String> list=new ArrayList<>();
        for (DbType value : values()) {
            list.add(value.getDbDesc());
        }
        return list;
    }
}
