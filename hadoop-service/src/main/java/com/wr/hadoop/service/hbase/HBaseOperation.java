package com.wr.hadoop.service.hbase;


import com.wr.hadoop.common.HBaseTableRowData;
import com.wr.hadoop.common.HBaseTableRowData.HBaseRowData;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class HBaseOperation {
    public static Configuration configuration;
    public static Connection connection;
    public static Admin admin;

    public HBaseOperation() {
        configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.property.clientPort", "2181");
        configuration.set("zookeeper.znode.parent", "/hbase-other");
        configuration.set("hbase.zookeeper.quorum", "zk01,zk02,zk03");
        configuration.set("hbase.rootdir","hdfs://app4:9000/hbase-other");
        configuration.set("hbase.client.write.buffer","5242880");
    }

    public static void init() throws IOException {
        connection = ConnectionFactory.createConnection(configuration);
        admin = connection.getAdmin();
    }

    public static void close() throws IOException {
        admin.close();
        connection.close();
    }

    public static boolean isTableExist(String tableName) throws IOException {
        init();
        TableName tn = TableName.valueOf(tableName);
        return admin.tableExists(tn);
    }

    /**
     * 创建数据表
     * @param tableName
     * @param colFamilies
     * @throws IOException
     */
    public static void createTable(String tableName, String[] colFamilies) throws IOException {
        init();
        TableName tn = TableName.valueOf(tableName);
        HTableDescriptor hTableDescriptor = new HTableDescriptor(tn);
        for (String colFamily : colFamilies){
            HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(colFamily);
            hTableDescriptor.addFamily(hColumnDescriptor);
        }
        admin.createTable(hTableDescriptor);
        close();
    }

    public static void createTableIfNotExists(String tableName, String[] colFamilies) throws IOException {
        if (!isTableExist(tableName)) {
            createTable(tableName, colFamilies);
        }
    }

    /**
     * 删除表
     * @param tableName
     * @throws IOException
     */
    public static void deleteTable(String tableName) throws IOException {
        init();
        TableName tn = TableName.valueOf(tableName);
        if (admin.tableExists(tn)) {
            admin.disableTable(tn);
            admin.deleteTable(tn);
        }
        close();
    }

    /**
     * 获取所有数据表名称
     * @return
     * @throws IOException
     */
    public static List<String> listTableNames() throws IOException {
        init();
        List<String> tables = new ArrayList<String>();
        TableName[] tableNames = admin.listTableNames();
        for (TableName tn : tableNames) {
            tables.add(tn.getNameAsString());
        }
        close();
        return tables;
    }

    /**
     * 插入或更新数据
     * @param tableName
     * @param colFamily
     * @param colume
     * @param rowkey
     * @param value
     * @throws IOException
     */
    public static void insertRow(String tableName, String colFamily, String colume, String rowkey, byte[] value)
            throws IOException {
        init();
        TableName tn = TableName.valueOf(tableName);
        Table table = connection.getTable(tn);
        Put put = new Put(Bytes.toBytes(rowkey));
        put.addColumn(Bytes.toBytes(colFamily), Bytes.toBytes(colume), value);
        table.put(put);
        table.close();
        close();
    }

    /**
     * 插入或更新数据
     * @param tableName
     * @param put
     * @throws IOException
     */
    public static void insertRow(String tableName, Put put) throws IOException {
        init();
        TableName tn = TableName.valueOf(tableName);
        Table table = connection.getTable(tn);
        table.put(put);
        table.close();
        close();
    }

    public static void insertRows(String tableName, List<Put> puts) throws IOException {
        init();
        TableName tn = TableName.valueOf(tableName);
        Table table = connection.getTable(tn);
        table.put(puts);
        table.close();
        close();
    }

    /**
     * 删除数据
     * @param tableName
     * @param colFamily
     * @param colume
     * @param rowkey
     * @throws IOException
     */
    public static void deleteRow(String tableName, String colFamily, String colume, String rowkey) throws IOException {
        init();
        TableName tn = TableName.valueOf(tableName);
        Table table = connection.getTable(tn);
        Delete delete = new Delete(Bytes.toBytes(rowkey));
        if (!StringUtils.isEmpty(colFamily) && !StringUtils.isEmpty(colume)) {
            delete.addColumn(Bytes.toBytes(colFamily), Bytes.toBytes(colume));
        } else if (!StringUtils.isEmpty(colFamily)) {
            delete.addFamily(Bytes.toBytes(colFamily));
        }
        table.delete(delete);
        table.close();
        close();
    }


    /**
     * 获取指定数据
     * @param tableName
     * @param colFamily
     * @param colume
     * @param rowkey
     * @return
     * @throws IOException
     */
    public static HBaseTableRowData getData(String tableName,String colFamily,String colume, String rowkey)throws  IOException{
        init();
        Table table = connection.getTable(TableName.valueOf(tableName));
        Get get = new Get(Bytes.toBytes(rowkey));

        if (!StringUtils.isEmpty(colFamily) && !StringUtils.isEmpty(colume)) {
            get.addColumn(Bytes.toBytes(colFamily),Bytes.toBytes(colume));  //获取指定列数据
        } else if (!StringUtils.isEmpty(colFamily)) {
            get.addFamily(Bytes.toBytes(colFamily));
        }
        Cell[] cells = table.get(get).rawCells();
        HBaseTableRowData tableRowData = new HBaseTableRowData(tableName, rowkey);
        for (Cell cell : cells) {
            HBaseRowData rowData = new HBaseRowData();
            rowData.setColumnFamily(new String(CellUtil.cloneFamily(cell)));
            rowData.setColumn(new String(CellUtil.cloneQualifier(cell)));
            rowData.setValue(new String(CellUtil.cloneValue(cell)));
            tableRowData.getRowValue().add(rowData);
        }
        table.close();
        close();
        return tableRowData;
    }

    public static void main(String[] args) throws IOException {
        HBaseOperation hBaseOperation = new HBaseOperation();
        if(hBaseOperation.isTableExist("user-behavior")) {
            System.out.println("table exist");
        } else {
            System.out.println("table not exist");
        }
    }
}
