package com.levi.export.database.word.service;

import com.levi.export.database.word.domain.DbConfig;
import com.levi.export.database.word.domain.TableStructure;

import java.sql.Connection;
import java.util.List;

public interface DB2WordService {
    /**
     * 得到数据库链接
     * 区分普通链接和ssh链接
     * @param dbConfig
     * @return
     */
    Connection getConnection(DbConfig dbConfig);

    /**
     * 导出数据库为word
     * @param dbConfig
     *
     */
    String exportDatabase2Word(Connection connection,DbConfig dbConfig);

    /**
     * 得到数据库list
     * @param connection
     * @return
     */
    List<String> getDbNameList(Connection connection);

    /**
     * 后置数据
     * @param list
     */
    default void postHandleListTableStructure(List<TableStructure> list){

    }
}
