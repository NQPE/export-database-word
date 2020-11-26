package com.levi.export.database.word.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 表结构
 */
public class TableStructure implements Serializable {
    /**
     * 表名称
     */
    private String tableName;

    /**
     * 数据库引擎
     */
    private String tableEngine;
    /**
     * 表编码
     */
    private String tableCollation;
    /**
     * 表类型
     */
    private String tableType;
    /**
     * 表注释
     */
    private String tableComment;
    /**
     * 创建选项
     */
    private String tableCreateOptions;

    /**
     * 表文档中排序
     */
    private String tableNo;

    /**
     * 表的ddl语句
     */
    private String tableDDL;

    /**
     * 表的创建时间
     */
    private String tableCreatTime;

    /**
     * 表字段列表
     */
    List<ColumnStructure> tableColumnStructureList;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableEngine() {
        return tableEngine;
    }

    public void setTableEngine(String tableEngine) {
        this.tableEngine = tableEngine;
    }

    public String getTableCollation() {
        return tableCollation;
    }

    public void setTableCollation(String tableCollation) {
        this.tableCollation = tableCollation;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public String getTableCreateOptions() {
        return tableCreateOptions;
    }

    public void setTableCreateOptions(String tableCreateOptions) {
        this.tableCreateOptions = tableCreateOptions;
    }

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public String getTableDDL() {
        return tableDDL;
    }

    public void setTableDDL(String tableDDL) {
        this.tableDDL = tableDDL;
    }

    public String getTableCreatTime() {
        return tableCreatTime;
    }

    public void setTableCreatTime(String tableCreatTime) {
        this.tableCreatTime = tableCreatTime;
    }

    public List<ColumnStructure> getTableColumnStructureList() {
        return tableColumnStructureList;
    }

    public void setTableColumnStructureList(List<ColumnStructure> tableColumnStructureList) {
        this.tableColumnStructureList = tableColumnStructureList;
    }
}
