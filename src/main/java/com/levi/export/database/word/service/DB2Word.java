package com.levi.export.database.word.service;

import com.levi.export.database.word.domain.DbConfig;

import java.sql.Connection;

public interface DB2Word {
    /**
     * 得到数据库链接
     * @param dbConfig
     * @return
     */
    Connection getConnection(DbConfig dbConfig);


}
