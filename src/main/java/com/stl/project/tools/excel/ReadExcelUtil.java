package com.stl.project.tools.excel;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ReadExcelUtil {
    private static Logger logger = org.apache.log4j.Logger.getLogger(ReadExcelUtil.class);
    /*
    * 文件输入流
    * */
    public InputStream inputFile(String path){
        InputStream is = null;
        try {
            is = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return is;
    }
    /*
     * 根据不同的文件创建不同的对象，
     * stream 输入文件流
     * ext 文件后缀名
     * */
    public Workbook createWorkBook(InputStream stream, String ext) {
        Object workbook = null;
        try {
            if (".xls".equals(ext)) {
                workbook = new HSSFWorkbook(stream);
            } else if (".xlsx".equals(ext)) {
                workbook = new XSSFWorkbook(stream);
            } else {
                logger.info("读取文件后缀名不为xls,xlsx，返回空");
            }
        } catch (IOException var5) {
            logger.warn("excel文件读取io异常:" + var5.getMessage());
            var5.printStackTrace();
        }
        return (Workbook)workbook;
    }
    /*
    * 通过sheet名字获取sheet
    * */
    public Sheet getSheet(Workbook workBook, String sheetName) {
        return workBook.getSheet(sheetName);
    }
    /*
    * 通过sheet下标获取sheet
    * */
    public Sheet getSheet(Workbook workBook, Integer index) {
        return workBook.getSheetAt(index);
    }
    /*
    *通过行数得到某行
    * row:需要得到的第某行，从0开始
    * */
    private Row getRow(Sheet sheet, int rowNum){
        return sheet.getRow(rowNum);
    }
    /*
    * 得到某个sheet页里面的总行数
    * */
    private int getSheetRowNum(Sheet sheet) {
        return sheet.getLastRowNum();
    }
    /*
    * 得到sheet里面的值
    * Sheet:sheet
    * titleRowNum:标题行从1开始
    * 默认标题行下面为valueRow
    * */
    public List<Map<String,String>> getSheetContent(Sheet sheet, int titleRowNum){
        int rowIndex = 0;//行标记
        int colIndex = 0;//列标记
        int lastCellNum = 0;//最后一个格子数
        //得到sheet里面的总行数
        int rowNum = getSheetRowNum(sheet) + 1;
        List<Map<String, String>> list = new ArrayList<>();
        //得到标题行
        Row keyRow = getRow(sheet,titleRowNum);
        try{
            for (int i = titleRowNum + 1; i < rowNum; i++){
                Map<String,String> map = new HashMap<>();
                Row valueRow = getRow(sheet,i);
                if (keyRow != null){
                    lastCellNum = keyRow.getLastCellNum();//得到总列数
                }else {
                    logger.warn("sheet[" + sheet.getSheetName() + "]不存在标题行");
                }
                if (valueRow == null){
                    logger.warn("sheet[" + sheet.getSheetName() + "]不存在数据行");
                }
                //将cell里面的值取出来，放进map
                for (int cellIndex = 0; cellIndex < lastCellNum; cellIndex++){
                    String key = getCellValue(keyRow.getCell(cellIndex));
                    String value = getCellValue(valueRow.getCell(cellIndex));
                    map.put(key,value);
                }
                list.add(map);
            }
        }catch (Exception e){
            logger.error("sheet[" + sheet.getSheetName() + "]数据读取失败");
        }
        return list;
    }
    /*
    * 得到某个cell里面的值
    * */
    private String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        } else if (cell.getCellType() == 1) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == 4) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == 2) {
            return cell.getCellFormula();
        } else if (cell.getCellType() == 0) {
            DecimalFormat df = new DecimalFormat("0");
            String whatYourWant = df.format(cell.getNumericCellValue());
            return String.valueOf(whatYourWant);
        } else {
            return "";
        }
    }
}
