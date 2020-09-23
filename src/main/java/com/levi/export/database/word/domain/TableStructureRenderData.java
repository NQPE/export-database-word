package com.levi.export.database.word.domain;

import com.deepoove.poi.data.MiniTableRenderData;

import java.io.Serializable;
import java.util.List;

/**
 * 表结构渲染对象
 */
public class TableStructureRenderData implements Serializable {
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
     * 表字段table渲染
     */
    MiniTableRenderData tableColumnTableRenderData;

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

    public MiniTableRenderData getTableColumnTableRenderData() {
        return tableColumnTableRenderData;
    }

    public void setTableColumnTableRenderData(MiniTableRenderData tableColumnTableRenderData) {
        this.tableColumnTableRenderData = tableColumnTableRenderData;
    }
}
