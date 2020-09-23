package com.levi.export.database.word.service.impl;

import com.levi.export.database.word.domain.ColumnStructure;
import com.levi.export.database.word.domain.DbConfig;
import com.levi.export.database.word.domain.TableStructure;
import com.levi.export.database.word.util.CommonUtil;
import com.levi.export.database.word.util.SqlUtil;
import com.levi.export.database.word.util.WordUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Mysql2WordServiceImpl extends BaseDB2WordServiceImpl {
    /**
     * word文件名称
     */
    private static final String WORD_FILE_NAME = "数据库表结构(MYSQL).docx";
    /**
     * 查询数据库名称列表 sql
     */
    private static final String DB_NAME_LIST_SQL = "show databases";
    /**
     * 查询表名称列表 sql
     */
    private static final String DB_TABLE_STRUCTURE_SQL = "SELECT table_name as tableName, table_type as tableType, ENGINE as tableEngine" +
            ",table_collation as tableCollation,table_comment as tableComment, create_options as tableCreateOptions" +
            " FROM information_schema.TABLES WHERE table_schema = '%s'";
    /**
     * 查询表结构 sql
     */
    private static final String DB_COLUMN_STRUCTURE_SQL = "SELECT ordinal_position as columnOrdinalPosition,column_name as columnName, column_type as columnType" +
            ", column_key as columnKey, extra as columnExtra,is_nullable as columnIsNullable, column_default as columnDefault, column_comment as columnComment" +
            ",data_type as columnType,character_maximum_length as columnLength"
            + " FROM information_schema.columns WHERE table_schema = '%s' and table_name= '%s' ";

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
    public String exportDatabase2Word(Connection connection,DbConfig dbConfig) {
        if (connection == null) {
            return "配置错误";
        }
        //验参
        if (!checkExportWordParam(dbConfig)) {
            return "配置错误";
        }
        String tableStructureSql = String.format(DB_TABLE_STRUCTURE_SQL, dbConfig.getDbName());
        List<TableStructure> tableStructureList = getTableStructureList(connection, tableStructureSql);
        if (CommonUtil.isEmpty(tableStructureList)) {
            return "数据库结构为空";
        }
        for (int i = 0; i < tableStructureList.size(); i++) {
            TableStructure tableStructure=tableStructureList.get(i);
            String columnStructureSql = String.format(DB_COLUMN_STRUCTURE_SQL, dbConfig.getDbName(), tableStructure.getTableName());
            List<ColumnStructure> columnStructureList = getColumnStructureList(connection, columnStructureSql);
            tableStructure.setTableColumnStructureList(columnStructureList);
            tableStructure.setTableNo(i+1+"");
        }
        String path = dbConfig.getWordSavePath() + "/" + WORD_FILE_NAME;
        WordUtil.exportDB2Word(getTableStructureRenderDataList(tableStructureList), path);
        return null;
    }

    @Override
    public List<String> getDbNameList(Connection connection) {
        ResultSet set = SqlUtil.getResultSet(connection, DB_NAME_LIST_SQL);
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
