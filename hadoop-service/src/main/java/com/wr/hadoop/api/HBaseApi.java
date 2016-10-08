package com.wr.hadoop.api;

import com.wr.hadoop.api.module.RestReturn;
import com.wr.hadoop.api.module.TablesResponse;
import com.wr.hadoop.common.HBaseTableRowData;
import com.wr.hadoop.common.HBaseTableRowData.HBaseRowData;
import com.wr.hadoop.service.hbase.HBaseOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/13.
 */
@Controller
@RequestMapping(value="/hbase")
public class HBaseApi {
    private static Log log = LogFactory.getLog("service");

    @Autowired
    private HBaseOperation hBaseOperation;

    @RequestMapping(value="/table", method= RequestMethod.POST)
    @ResponseBody
    public RestReturn<String> createTable(@RequestBody HBaseTableRowData request) {
        RestReturn<String> result = new RestReturn<String>();
        if (null == request || StringUtils.isEmpty(request.getTableName()) || request.getColFamilies().size() == 0) {
            result.addError("invalid request");
            return result;
        }
        String[] colFamilies = new String[request.getColFamilies().size()];
        try {
            hBaseOperation.createTable(request.getTableName(), (request.getColFamilies().toArray(colFamilies)));
        } catch (IOException e) {
            result.addError(e.getLocalizedMessage());
            log.error(e.getLocalizedMessage());
            return result;
        }
        result.setModel(request.getTableName());
        return result;
    }

    @RequestMapping(value="/table", method= RequestMethod.DELETE)
    @ResponseBody
    public RestReturn<String> deleteTable(@RequestBody HBaseTableRowData request) {
        RestReturn<String> result = new RestReturn<String>();
        if (null == request || StringUtils.isEmpty(request.getTableName())) {
            result.addError("invalid request");
            return result;
        }
        try {
            hBaseOperation.deleteTable(request.getTableName());
        } catch (IOException e) {
            result.addError(e.getLocalizedMessage());
            log.error(e.getLocalizedMessage());
            return result;
        }
        result.setModel(request.getTableName());
        return result;
    }

    @RequestMapping(value="/table", method= RequestMethod.GET)
    @ResponseBody
    public RestReturn<TablesResponse> getTableNames(){
        RestReturn<TablesResponse> result = new RestReturn<TablesResponse>();
        List<String> tableNames = null;
        try {
            tableNames = hBaseOperation.listTableNames();
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
            result.addError(e.getLocalizedMessage());
            return result;
        }
        /*List<HBaseTableRowData> tables = new ArrayList<HBaseTableRowData>();
        for (String tableName : tableNames) {
            HBaseTableRowData rowData = new HBaseTableRowData(tableName);
            tables.add(rowData);
        }*/
        TablesResponse model = new TablesResponse(tableNames);
        result.setModel(model);
        return result;
    }

    @RequestMapping(value="/table/row", method= RequestMethod.POST)
    @ResponseBody
    public RestReturn<String> insertRow(@RequestBody HBaseTableRowData request) {
        RestReturn<String> result = new RestReturn<String>();

        String tableName = request.getTableName();
        String rowKey = request.getRowkey();
        HBaseRowData rowValue = request.getRowValue().get(0);
        String colFamily = rowValue.getColumnFamily();
        String column = rowValue.getColumn();
        String value = rowValue.getValue();
        /*try {
            //hBaseOperation.insertRow(tableName, colFamily, column, rowKey, value);
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
            result.addError(e.getLocalizedMessage());
            return result;
        }*/
        result.setModel(rowKey);
        return result;
    }

    @RequestMapping(value="/table/row", method= RequestMethod.DELETE)
    @ResponseBody
    public RestReturn<String> deleteRow(@RequestBody HBaseTableRowData request) {
        RestReturn<String> result = new RestReturn<String>();
        HBaseRowData rowValue = request.getRowValue().get(0);
        try {
            hBaseOperation.deleteRow(request.getTableName(), rowValue.getColumnFamily(), rowValue.getColumn(),
                    request.getRowkey());
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
            result.addError(e.getLocalizedMessage());
            return result;
        }
        result.setModel(request.getRowkey());
        return result;
    }

    @RequestMapping(value="/table/{tableName}/row/{rowKey}", method= RequestMethod.GET)
    @ResponseBody
    public RestReturn<HBaseTableRowData> getRow(@PathVariable String tableName,@PathVariable String rowKey) {
        RestReturn<HBaseTableRowData> result = new RestReturn<HBaseTableRowData>();
        HBaseTableRowData rowData = null;
        try {
            rowData = hBaseOperation.getData(tableName, null, null, rowKey);
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
            result.addError(e.getLocalizedMessage());
            return result;
        }
        result.setModel(rowData);
        return result;
    }
}
