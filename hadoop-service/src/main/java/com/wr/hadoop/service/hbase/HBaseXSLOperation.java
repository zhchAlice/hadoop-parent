package com.wr.hadoop.service.hbase;

import com.wr.hadoop.common.DrinkBrandInfo;
import org.apache.hadoop.hbase.client.Put;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author: Alice
 * @email: chenzhangzju@yahoo.com
 * @date: 2017/1/5.
 */
public class HBaseXSLOperation {

    private static HBaseOperation hBaseOperation = new HBaseOperation();

    //brand-info表的表结构字段
    private static final String TABLE_BRAND = "brand-info";
    private static final byte[] COLUMNEFAMILY_BRANDINFO = "brandinfo".getBytes();
    private static final byte[] COLUMN_BRAND = "brand".getBytes();
    private static final byte[] COLUMN_CATEGORY = "category".getBytes();

    //user-info表的表结构字段
    private static final String TABLE_USER_INFO = "user-info";
    private static final byte[] COLUMNEFAMILY_USERINFO = "userinfo".getBytes();
    private static final byte[] COLUMN_NAME = "name".getBytes();
    private static final byte[] COLUMN_EMAIL = "email".getBytes();
    private static final byte[] COLUMN_PHONE = "phone".getBytes();
    private static final byte[] COLUMN_CITY = "city".getBytes();
    private static final byte[] COLUMN_ZONE = "zone".getBytes();
    private static final byte[] COLUMN_MIN = "min".getBytes();
    private static final byte[] COLUMN_MAX = "max".getBytes();
    private static final byte[] COLUMN_ORDERNUM = "ordernum".getBytes();
    private static final byte[] COLUMN_PROVINCE = "province".getBytes();
    private static final byte[] COLUMNEFAMILY_ORDERINFO = "orderinfo".getBytes();
    private static final byte[] COLUMN_ORDERINDEXS = "orderindexs".getBytes();

    //cate-sale表的表结构字段
    private static final String TABLE_CATEGORY_SALE = "cate-sale";
    private static final byte[] COLUMNEFAMILY_CATESALE = "catesale".getBytes();
    private static final byte[] COLUMN_SALENUM = "salenum".getBytes();

    //zone-sale表的表结构字段
    private static final String TABLE_ZONE_SALE = "zone-sale";
    private static final byte[] COLUMNEFAMILY_ZONESALE = "zonesale".getBytes();


    //存储所有品牌的酒的信息（包含品牌-分类-数量）
    private static Map<String, DrinkBrandInfo> drinkBrandInfoMap = new HashMap<>();
    //用户购买酒的统计信息，key为酒品牌，value为购买了该品牌酒的用户数量
    private static Map<String, Integer> cateStsInfo = new HashMap<>();
    //用户购买酒的统计信息，key为省+市+区的组合信息，value为购买了酒在该区域的销量信息
    private static Map<String, Integer> zoneStsInfo = new HashMap<>();


    /**
     * 读取预置的winebrand.txt，缓存所有酒和品牌信息
     */
    private static void readWineBrand() {
        String fileName = "E:\\03.BigData\\winebrand.txt";
        File file = new File(fileName);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            int curIndex = 0;
            String tempString;
            boolean isFirstLine = true;
            String curCategory = null;
            while ((tempString = reader.readLine()) != null) {
                if (isFirstLine) {  //文本的第一个字符表示其字符编码，这里舍弃
                    tempString = tempString.substring(1);
                    isFirstLine = false;
                }
                if (tempString.startsWith("[")) {
                    curCategory = tempString.substring(tempString.indexOf("[") + 1, tempString.indexOf("]"));
                } else {
                    drinkBrandInfoMap.put(tempString, new DrinkBrandInfo(tempString, curCategory, curIndex++));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将酒的品牌，分类和编号信息存储到HBase中
     *
     * @throws IOException
     */
    private static void writeBrandInfoToHBase() throws IOException {
        hBaseOperation.createTableIfNotExists(TABLE_BRAND,
                new String[]{new String(COLUMNEFAMILY_BRANDINFO)});
        List<Put> puts = new ArrayList<>();
        for (DrinkBrandInfo drinkBrandInfo : drinkBrandInfoMap.values()) {
            Put put = new Put(String.valueOf(drinkBrandInfo.getIndex()).getBytes());
            put.addColumn(COLUMNEFAMILY_BRANDINFO, COLUMN_CATEGORY, drinkBrandInfo.getCategory().getBytes());
            put.addColumn(COLUMNEFAMILY_BRANDINFO, COLUMN_BRAND, drinkBrandInfo.getBrand().getBytes());
            puts.add(put);
        }
        hBaseOperation.insertRows(TABLE_BRAND, puts);
    }

    /**
     * 处理XSL数据
     *
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static void readSealInfo() throws IOException, InvalidFormatException {
        Workbook wb = WorkbookFactory.create(new File("E:\\03.BigData\\dataset_xsl.xls"));
        //依次处理每张表
        for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
            Sheet sheet = wb.getSheetAt(sheetIndex);
            if (null == sheet || sheet.getLastRowNum()==0) {
                return;
            }
            List<Put> puts = new ArrayList<>();
            for (int rowIndex = 1; rowIndex < sheet.getLastRowNum() + 1; rowIndex++) {
                puts.add(hanleOneRow(sheet.getRow(rowIndex), rowIndex));
            }
            writeUserInfoToHBase(puts);
            writeCategorySaleInfoToHBase();
            writeZoneSaleInfoToHBase();
        }
    }

    /**
     * 将用户基本信息写入HBase中
     *
     * @throws IOException
     */
    private static void writeUserInfoToHBase(List<Put> puts) throws IOException {
        hBaseOperation.createTableIfNotExists(TABLE_USER_INFO,
                new String[]{new String(COLUMNEFAMILY_USERINFO), new String(COLUMNEFAMILY_ORDERINFO)});
        hBaseOperation.insertRows(TABLE_USER_INFO, puts);
    }

    /**
     * 写入分类销量信息到HBase中
     *
     * @throws IOException
     */
    private static void writeCategorySaleInfoToHBase() throws IOException {
        hBaseOperation.createTableIfNotExists(TABLE_CATEGORY_SALE,
                new String[]{new String(COLUMNEFAMILY_CATESALE)});
        List<Put> puts = new ArrayList<>();
        for (String key : cateStsInfo.keySet()) {
            Put put = new Put(key.getBytes());
            put.addColumn(COLUMNEFAMILY_CATESALE, COLUMN_CATEGORY, drinkBrandInfoMap.get(key).getCategory().getBytes());
            put.addColumn(COLUMNEFAMILY_CATESALE, COLUMN_SALENUM, String.valueOf(cateStsInfo.get(key)).getBytes());
            puts.add(put);
        }
        hBaseOperation.insertRows(TABLE_CATEGORY_SALE, puts);
    }

    /**
     * 写入地区销量信息到hBase中
     *
     * @throws IOException
     */
    private static void writeZoneSaleInfoToHBase() throws IOException {
        hBaseOperation.createTableIfNotExists(TABLE_ZONE_SALE,
                new String[]{new String(COLUMNEFAMILY_ZONESALE)});
        List<Put> puts = new ArrayList<>();
        for (String key : zoneStsInfo.keySet()) {
            Put put = new Put(key.getBytes());
            put.addColumn(COLUMNEFAMILY_ZONESALE, COLUMN_SALENUM, String.valueOf(zoneStsInfo.get(key)).getBytes());
            puts.add(put);
        }
        hBaseOperation.insertRows(TABLE_ZONE_SALE, puts);
    }


    /**
     * 处理每一行
     *
     * @param row
     */
    private static Put hanleOneRow(Row row, int rowIndex) {
        if (null == row) {
            return null;
        }
        String name = row.getCell(0).getStringCellValue();
        String email = row.getCell(1) == null ? null : row.getCell(1).getStringCellValue();
        String phoneNumber = row.getCell(2) == null ? null : row.getCell(2).getStringCellValue();

        double minPurchase = row.getCell(3) == null ? null : row.getCell(3).getNumericCellValue();
        double maxPurchase = row.getCell(4) == null ? null : row.getCell(4).getNumericCellValue();
        String[] brands = row.getCell(6).getStringCellValue().split("，");
        int orderNum = row.getCell(7) == null ? null : Double.valueOf(row.getCell(7).getNumericCellValue()).intValue();
        String province = row.getCell(8) == null ? null : row.getCell(8).getStringCellValue();
        String city = row.getCell(9) == null ? null : row.getCell(9).getStringCellValue();
        String zone = row.getCell(10) == null ? null : row.getCell(10).getStringCellValue();

        String brandsIndexs = convertToBrandIndexList(brands);
        String preZoneKey = new StringBuffer(province).append("_").append(city).append("_").append(zone).toString();


        handleCateSts(brands);
        handleZoneSts(brands, preZoneKey);

        Put put = new Put(String.valueOf(rowIndex).getBytes());
        put.addColumn(COLUMNEFAMILY_USERINFO, COLUMN_NAME, name.getBytes());
        put.addColumn(COLUMNEFAMILY_USERINFO, COLUMN_EMAIL, email.getBytes());
        put.addColumn(COLUMNEFAMILY_USERINFO, COLUMN_PHONE, phoneNumber.getBytes());
        put.addColumn(COLUMNEFAMILY_USERINFO, COLUMN_PROVINCE, province.getBytes());
        put.addColumn(COLUMNEFAMILY_USERINFO, COLUMN_CITY, city.getBytes());
        put.addColumn(COLUMNEFAMILY_USERINFO, COLUMN_ZONE, zone.getBytes());
        put.addColumn(COLUMNEFAMILY_USERINFO, COLUMN_MIN, String.valueOf(minPurchase).getBytes());
        put.addColumn(COLUMNEFAMILY_USERINFO, COLUMN_MAX, String.valueOf(maxPurchase).getBytes());
        put.addColumn(COLUMNEFAMILY_USERINFO, COLUMN_ORDERNUM, String.valueOf(orderNum).getBytes());

        put.addColumn(COLUMNEFAMILY_ORDERINFO, COLUMN_ORDERINDEXS, brandsIndexs.getBytes());
        return put;
    }

    /**
     * 统计每个酒品牌的购买人数
     *
     * @param brands
     */
    private static void handleCateSts(String[] brands) {
        if (brands == null || brands.length == 0) {
            return;
        }
        for (int i = 0; i < brands.length; i++) {
            if (!cateStsInfo.containsKey(brands[i])) {
                cateStsInfo.put(brands[i], 1);
            } else {
                cateStsInfo.put(brands[i], cateStsInfo.get(brands[i]).intValue() + 1);
            }
        }
    }

    private static void handleZoneSts(String[] brands, String preKey) {
        if (brands == null || brands.length == 0) {
            return;
        }
        for (int i = 0; i < brands.length; i++) {
            String key = preKey + "_" + brands[i];
            if (!zoneStsInfo.containsKey(key)) {
                zoneStsInfo.put(key, 1);
            } else {
                zoneStsInfo.put(key, zoneStsInfo.get(key).intValue() + 1);
            }
        }
    }

    private static String convertToBrandIndexList(String[] brands) {
        if (brands.length == 0) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < brands.length - 1; i++) {
            stringBuffer.append(drinkBrandInfoMap.get(brands[i]).getIndex()).append(",");
        }
        stringBuffer.append(drinkBrandInfoMap.get(brands[brands.length - 1]).getIndex());
        return stringBuffer.toString();
    }

    public static void main(String[] args) throws IOException, InvalidFormatException {
        readWineBrand();
        writeBrandInfoToHBase();
        readSealInfo();
    }
}
