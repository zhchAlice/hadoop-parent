package com.wr.hadoop.common;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonRootName;

import java.util.ArrayList;
import java.util.List;

public class HBaseTableRowData {
    @JsonProperty("tableName")
    private String tableName;

    @JsonProperty("rowkey")
    private String rowkey;

    @JsonProperty("rowValue")
    private List<HBaseRowData> rowValue;

    public HBaseTableRowData(String tableName) {
        this.tableName = tableName;
    }

    public HBaseTableRowData(String tableName, String rowkey) {
        this.tableName = tableName;
        this.rowkey = rowkey;
        rowValue = new ArrayList<HBaseRowData>();
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getRowkey() {
        return rowkey;
    }

    public void setRowkey(String rowkey) {
        this.rowkey = rowkey;
    }

    public List<HBaseRowData> getRowValue() {
        return rowValue;
    }

    public void setRowValue(List<HBaseRowData> rowValue) {
        this.rowValue = rowValue;
    }

    public List<String> getColFamilies() {
        List<String> colFamilies = new ArrayList<String>();
        for (HBaseRowData data : this.rowValue) {
            colFamilies.add(data.getColumnFamily());
        }
        return colFamilies;
    }

    public static class HBaseRowData {
        @JsonProperty("columnFamily")
        private String columnFamily;

        @JsonProperty("column")
        private String column;

        @JsonProperty("value")
        private String value;

        public HBaseRowData() {
            super();
        }

        public HBaseRowData(String columnFamily, String column, String value) {
            this.columnFamily = columnFamily;
            this.column = column;
            this.value = value;
        }

        public String getColumnFamily() {
            return columnFamily;
        }

        public void setColumnFamily(String columnFamily) {
            this.columnFamily = columnFamily;
        }

        public String getColumn() {
            return column;
        }

        public void setColumn(String column) {
            this.column = column;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
