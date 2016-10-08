package com.wr.hadoop.api.module;

import com.wr.hadoop.common.HBaseTableRowData;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/14.
 */
public class TablesResponse {

    @JsonProperty("table")
    private List<String> table;

    public TablesResponse(List<String> table) {
        this.table = table;
    }

    public List<String> getTable() {
        return table;
    }

    public void setTable(List<String> table) {
        this.table = table;
    }


    /*public TablesResponse() {
        table = new ArrayList<HBaseTableRowData>();
    }

    public TablesResponse(List<HBaseTableRowData> table) {
        this.table = table;
    }

    public List<HBaseTableRowData> getTable() {
        return table;
    }

    public void setTable(List<HBaseTableRowData> table) {
        this.table = table;
    }*/
}
