package com.levi.export.database.word.service.impl;

import com.levi.export.database.word.domain.DbConfig;
import com.levi.export.database.word.util.SqlUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Mysql2WordServiceImpl extends BaseDB2WordServiceImpl {
    @Override
    public Connection getConnection(DbConfig dbConfig) {
        if (!checkDbConnectionParam(dbConfig)) {
            return null;
        }
        if (!dbConfig.getSshEnable()) {
            //普通链接
            return SqlUtil.getConnectionByMysql(dbConfig.getHost(), dbConfig.getPort(), dbConfig.getUsername(), dbConfig.getPassword());
        }
        //SSH链接
        return SqlUtil.getConnectionToSSHByMysql(dbConfig.getHost(), dbConfig.getPort(), dbConfig.getUsername(), dbConfig.getPassword()
                , dbConfig.getSshHost(), dbConfig.getSshPort(), dbConfig.getSshUsername(), dbConfig.getSshPassword());
    }

    @Override
    public void exportDatabase2Word(DbConfig dbConfig) {

    }

    @Override
    public List<String> getDbNameList(Connection connection) {
        ResultSet set = SqlUtil.getResultSet(connection, "show databases");
        List<String> list = new ArrayList<String>();
        if (set == null) {
            return list;
        }
        try {
            while (set.next()) {
                list.add(set.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
