package com.levi.export.database.word.domain;

import java.io.Serializable;

/**
 * 字段结构
 */
public class ColumnStructure implements Serializable {
    /**
     * 字段排序
     */
    private String columnOrdinalPosition;
    /**
     * 是否为空
     */
    private String columnIsNullable;
    /**
     * 字段默认值
     */
    private String columnDefault;
    /**
     * 字段数据类型
     */
    private String columnType;
    /**
     * 字段长度
     */
    private String columnLength;
    /**
     * 字段注释
     */
    private String columnComment;
    /**
     * 字段名称
     */
    private String columnName;

    /**
     * 字段键
     */
    private String columnKey;

    /**
     * 字段额外信息
     */
    private String columnExtra;

    public String getColumnOrdinalPosition() {
        return columnOrdinalPosition;
    }

    public void setColumnOrdinalPosition(String columnOrdinalPosition) {
        this.columnOrdinalPosition = columnOrdinalPosition;
    }

    public String getColumnIsNullable() {
        return columnIsNullable;
    }

    public void setColumnIsNullable(String columnIsNullable) {
        this.columnIsNullable = columnIsNullable;
    }

    public String getColumnDefault() {
        return columnDefault;
    }

    public void setColumnDefault(String columnDefault) {
        this.columnDefault = columnDefault;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getColumnLength() {
        return columnLength;
    }

    public void setColumnLength(String columnLength) {
        this.columnLength = columnLength;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnKey() {
        return columnKey;
    }

    public void setColumnKey(String columnKey) {
        this.columnKey = columnKey;
    }

    public String getColumnExtra() {
        return columnExtra;
    }

    public void setColumnExtra(String columnExtra) {
        this.columnExtra = columnExtra;
    }
}
