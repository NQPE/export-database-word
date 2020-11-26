package com.levi.export.database.word.manager;

import com.levi.export.database.word.emums.DbType;
import com.levi.export.database.word.service.DB2WordService;
import com.levi.export.database.word.service.impl.Mysql2Word1ServiceImpl;
import com.levi.export.database.word.service.impl.Mysql2WordServiceImpl;

public class DB2WordManager {
    //初始化mysql2Word
//    private static DB2WordService mysql2Word = new Mysql2WordServiceImpl();
    private static DB2WordService mysql2Word = new Mysql2Word1ServiceImpl();

    /**
     * 策略获取DB2Word
     *
     * @param dbDesc
     * @return
     */
    public static DB2WordService getDB2WordService(String dbDesc) {
        DbType dbType=DbType.getDbTypeByDesc(dbDesc);
        return getDB2WordService(dbType);
    }

    /**
     * 策略获取DB2Word
     *
     * @param dbType
     * @return
     */
    public static DB2WordService getDB2WordService(DbType dbType) {
        if (dbType == null) {
            return null;
        }
        DB2WordService db2WordService = null;
        switch (dbType) {
            //mysql
            case MYSQL:
                db2WordService = mysql2Word;
                break;
            default:
                break;
        }
        return db2WordService;
    }
}
