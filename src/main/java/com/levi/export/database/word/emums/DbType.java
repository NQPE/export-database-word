package com.levi.export.database.word.emums;

public enum DbType {
    MYSQL(1, "MySql");
    int dbType;
    String dbDesc;

    DbType(int dbType, String dbDesc) {
        this.dbType = dbType;
        this.dbDesc = dbDesc;
    }

}
