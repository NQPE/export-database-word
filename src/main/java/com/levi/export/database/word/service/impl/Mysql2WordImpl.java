package com.levi.export.database.word.service.impl;

import com.levi.export.database.word.domain.DbConfig;
import com.levi.export.database.word.service.DB2Word;

import java.sql.Connection;

public class Mysql2WordImpl implements DB2Word {
    @Override
    public Connection getConnection(DbConfig dbConfig) {
        return null;
    }
}
