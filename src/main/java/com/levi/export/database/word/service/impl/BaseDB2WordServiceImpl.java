package com.levi.export.database.word.service.impl;

import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.levi.export.database.word.domain.ColumnStructure;
import com.levi.export.database.word.domain.DbConfig;
import com.levi.export.database.word.domain.TableStructure;
import com.levi.export.database.word.domain.TableStructureRenderData;
import com.levi.export.database.word.service.DB2WordService;
import com.levi.export.database.word.util.CommonUtil;
import com.levi.export.database.word.util.SqlUtil;
import com.levi.export.database.word.util.WordUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDB2WordServiceImpl implements DB2WordService {
    /**
     * 检查DbConnection参数
     *
     * @param dbConfig
     * @return true 通过验证 false未通过验证
     */
    public boolean checkDbConnectionParam(DbConfig dbConfig) {
        if (dbConfig == null
                || CommonUtil.isEmpty(dbConfig.getHost())
                || CommonUtil.isEmpty(dbConfig.getUsername())
                || CommonUtil.isEmpty(dbConfig.getPassword())
                || dbConfig.getPort() == null) {
            return false;
        }
        //ssh链接验证
        if (dbConfig.getSshEnable() && (
                CommonUtil.isEmpty(dbConfig.getSshHost())
                        || CommonUtil.isEmpty(dbConfig.getSshUsername())
                        || CommonUtil.isEmpty(dbConfig.getSshPassword())
                        || dbConfig.getSshPort() == null
        )) {
            return false;
        }
        return true;
    }

    /**
     * 检查导出word参数
     *
     * @param dbConfig
     * @return true 通过验证 false未通过验证
     */
    public boolean checkExportWordParam(DbConfig dbConfig) {
        //验参
        if (CommonUtil.isEmpty(dbConfig.getWordSavePath()) ||
                CommonUtil.isEmpty(dbConfig.getDbName())) {
            return false;
        }
        return true;
    }

    /**
     * 获取表信息列表
     *
     * @param connection
     * @param sql
     * @return
     */
    protected List<TableStructure> getTableStructureList(Connection connection, String sql) {
        List<TableStructure> list = new ArrayList<>();
        ResultSet rs = SqlUtil.getResultSet(connection, sql);
        if (rs == null) {
            return list;
        }
        String columnLabel = null;
        try {
            while (rs.next()) {
                TableStructure item = new TableStructure();
                columnLabel = "tableName";
                if (SqlUtil.isExistColumn(rs, columnLabel)) {
                    item.setTableName(rs.getString(columnLabel));
                }
                columnLabel = "tableEngine";
                if (SqlUtil.isExistColumn(rs, columnLabel)) {
                    item.setTableEngine(rs.getString(columnLabel));
                }
                columnLabel = "tableCollation";
                if (SqlUtil.isExistColumn(rs, columnLabel)) {
                    item.setTableCollation(rs.getString(columnLabel));
                }
                columnLabel = "tableType";
                if (SqlUtil.isExistColumn(rs, columnLabel)) {
                    item.setTableType(rs.getString(columnLabel));
                }
                columnLabel = "tableComment";
                if (SqlUtil.isExistColumn(rs, columnLabel)) {
                    item.setTableComment(rs.getString(columnLabel));
                }
                columnLabel = "tableCreateOptions";
                if (SqlUtil.isExistColumn(rs, columnLabel)) {
                    item.setTableCreateOptions(rs.getString(columnLabel));
                }
                list.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * 获取表字段列表
     *
     * @param connection
     * @param sql
     * @return
     */
    protected List<ColumnStructure> getColumnStructureList(Connection connection, String sql) {
        List<ColumnStructure> list = new ArrayList<>();
        ResultSet rs = SqlUtil.getResultSet(connection, sql);
        if (rs == null) {
            return list;
        }
        String columnLabel = null;
        try {
            while (rs.next()) {
                ColumnStructure item = new ColumnStructure();
                columnLabel = "columnOrdinalPosition";
                if (SqlUtil.isExistColumn(rs, columnLabel)) {
                    item.setColumnOrdinalPosition(rs.getString(columnLabel));
                }
                columnLabel = "columnIsNullable";
                if (SqlUtil.isExistColumn(rs, columnLabel)) {
                    item.setColumnIsNullable(rs.getString(columnLabel));
                }
                columnLabel = "columnDefault";
                if (SqlUtil.isExistColumn(rs, columnLabel)) {
                    item.setColumnDefault(rs.getString(columnLabel));
                }
                columnLabel = "columnType";
                if (SqlUtil.isExistColumn(rs, columnLabel)) {
                    item.setColumnType(rs.getString(columnLabel));
                }
                columnLabel = "columnLength";
                if (SqlUtil.isExistColumn(rs, columnLabel)) {
                    item.setColumnLength(rs.getString(columnLabel));
                }
                columnLabel = "columnComment";
                if (SqlUtil.isExistColumn(rs, columnLabel)) {
                    item.setColumnComment(rs.getString(columnLabel));
                }
                columnLabel = "columnName";
                if (SqlUtil.isExistColumn(rs, columnLabel)) {
                    item.setColumnName(rs.getString(columnLabel));
                }
                columnLabel = "columnKey";
                if (SqlUtil.isExistColumn(rs, columnLabel)) {
                    item.setColumnKey(rs.getString(columnLabel));
                }
                columnLabel = "columnExtra";
                if (SqlUtil.isExistColumn(rs, columnLabel)) {
                    item.setColumnExtra(rs.getString(columnLabel));
                }
                list.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


    /**
     * 得到渲染到docx的数据
     *
     * @param list
     * @return
     */
    protected List<TableStructureRenderData> getTableStructureRenderDataList(List<TableStructure> list) {
        List<TableStructureRenderData> tableStructureRenderDataList = new ArrayList<>();
        if (CommonUtil.isEmpty(list)) {
            return tableStructureRenderDataList;
        }
        for (TableStructure tableStructure : list) {
            TableStructureRenderData item = new TableStructureRenderData();
            item.setTableName(tableStructure.getTableName());
            item.setTableEngine(tableStructure.getTableEngine());
            item.setTableCollation(tableStructure.getTableCollation());
            item.setTableType(tableStructure.getTableType());
            item.setTableComment(tableStructure.getTableComment());
            item.setTableCreateOptions(tableStructure.getTableCreateOptions());
            item.setTableNo(tableStructure.getTableNo());
            //字段表格对象
            item.setTableColumnTableRenderData(getColumnTableRenderData(tableStructure.getTableColumnStructureList()));
            tableStructureRenderDataList.add(item);
        }
        return tableStructureRenderDataList;
    }

    /**
     * 得到字段render
     *
     * @param list
     * @return
     */
    private MiniTableRenderData getColumnTableRenderData(List<ColumnStructure> list) {
        //防止空指针
        if (list == null) {
            list = new ArrayList<>();
        }
        RowRenderData columnHeader = getTableColumnHeader();
        List<RowRenderData> columnList = getWordColumnRowRenderDataList(list);
        MiniTableRenderData columnTable = new MiniTableRenderData(columnHeader, columnList);
        return columnTable;
    }

    /**
     * table的字段列表表头
     *
     * @return RowRenderData
     */
    private RowRenderData getTableColumnHeader() {
        RowRenderData header = RowRenderData.build(
                new TextRenderData("序号", WordUtil.getHeaderStyle()),
                new TextRenderData("字段名称", WordUtil.getHeaderStyle()),
                new TextRenderData("字段描述", WordUtil.getHeaderStyle()),
                new TextRenderData("字段类型", WordUtil.getHeaderStyle()),
                new TextRenderData("长度", WordUtil.getHeaderStyle()),
                new TextRenderData("允许空", WordUtil.getHeaderStyle()),
                new TextRenderData("缺省值", WordUtil.getHeaderStyle()));
        header.setRowStyle(WordUtil.getHeaderTableStyle());
        return header;
    }

    /**
     * 获取表字段列表word渲染
     *
     * @param list
     * @return
     */
    private List<RowRenderData> getWordColumnRowRenderDataList(List<ColumnStructure> list) {
        List<RowRenderData> result = new ArrayList<>();
        if (CommonUtil.isEmpty(list)) {
            return result;
        }
        for (int i = 0; i < list.size(); i++) {
            ColumnStructure item = list.get(i);
            RowRenderData row = RowRenderData.build(
                    new TextRenderData(item.getColumnOrdinalPosition() + ""),
                    new TextRenderData(item.getColumnName() + ""),
                    new TextRenderData(item.getColumnComment() + ""),
                    new TextRenderData(item.getColumnType() + ""),
                    new TextRenderData(item.getColumnLength() + ""),
                    new TextRenderData(item.getColumnIsNullable() + ""),
                    new TextRenderData(item.getColumnDefault() + "")
            );
            if (i % 2 == 0) {
                row.setRowStyle(WordUtil.getBodyTableStyle());
                result.add(row);
            } else {
                result.add(row);
            }
        }
        return result;
    }
}
