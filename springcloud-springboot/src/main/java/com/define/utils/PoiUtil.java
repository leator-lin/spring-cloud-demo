package com.define.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.cache.MapCache;
import com.define.domain.DemoData;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.junit.Test;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PoiUtil {
    private static String PATH = "C:\\IdeaProjects\\spring-cloud-demo\\springcloud-springboot\\src";
    public static void main(String[] args) throws Exception {
//        testBigData03();
//        testBigData07();
        simpleWrite();
    }

    //xls写入65536条数据
    @Test
    public static void testBigData03() throws Exception {
        //新建一个工作簿
        Workbook workbook = new HSSFWorkbook();
        //创建一个sheet
        Sheet sheet = workbook.createSheet("测试03excel");
        //写入前时间
        long before = System.currentTimeMillis();
        for(int i=0;i<65536;i++){
            //创建行
            Row row = sheet.createRow(i);
            for(int j=0;j<10;j++){
                //创建列，形成单元格
                Cell cell = row.createCell(j);
                //set值
                cell.setCellValue("第"+i+"行"+":"+j);
            }
        }
        //创建输出流,03版后缀xls
        FileOutputStream fos = new FileOutputStream(PATH+"WriteBigData03.xls");
        //工作簿写入流
        workbook.write(fos);
        //写入后时间
        long after = System.currentTimeMillis();
        long time = (after-before)/1000;
        System.out.println("用时"+time+"秒");
        //关闭流
        fos.close();
    }

    @Test
    //速度慢
    public static void testBigData07() throws Exception {
//        Workbook workbook = new XSSFWorkbook();
        Workbook workbook = new SXSSFWorkbook();
        Sheet sheet = workbook.createSheet("测试07excel");
        long before = System.currentTimeMillis();
        for(int i=0;i<200000;i++){
            Row row = sheet.createRow(i);
            for(int j=0;j<10;j++){
                Cell cell = row.createCell(j);
                cell.setCellValue("第"+i+"行"+":"+j);
            }
        }
        FileOutputStream fos = new FileOutputStream(PATH+"WriteBigData07.xlsx");
        workbook.write(fos);
        ((SXSSFWorkbook)workbook).dispose();
        long after = System.currentTimeMillis();
        long time = (after-before)/1000;
        System.out.println("用时"+time+"秒");
        fos.close();
    }

    @Test
    public static void simpleWrite() {
        // 写法1
        String fileName = PATH + "srceasyExcel.xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.read().readCache(new MapCache());
        EasyExcel.write(fileName, DemoData.class).sheet("模板").doWrite(data());
    }

    //写入数据、这里可以通过视图层获取数据写入到excel
    //这里测试的是20w数据量
    private static List<DemoData> data() {
        List<DemoData> list = new ArrayList<>();
        long before = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            DemoData data = new DemoData();
            data.setDate(new Date());
            data.setPhone("18888888888");
            data.setRound(2);
            data.setTime("00:00:00");
            data.setNumber("123456789012345678");
            data.setMsg("{\"是本人\":\"INTENT01009\"}");
            data.setRecord("http://30.99.216.157/callresult/downloadRecord?call=BB4826DD-3EDD-4749-AAFB-1015A64AC933");
            data.setText("哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈\n" +
                    "哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈\n" +
                    "哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈\n" +
                    "哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈\n" +
                    "哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈\n" +
                    "哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈\n" +
                    "哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈\n" +
                    "哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈\n" +
                    "哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈\n" +
                    "哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈\n" +
                    "哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈\n" +
                    "哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈\n" +
                    "哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈\n" +
                    "哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈\n" +
                    "哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈\n" +
                    "哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈\n" +
                    "哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈\n" +
                    "哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈\n" +
                    "哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈\n" +
                    "哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈\n" +
                    "哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈\n" +
                    "哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈");
            list.add(data);
        }
        long after = System.currentTimeMillis();
        System.out.println("用时："+(after-before)+"ms");
        return list;
    }
}
