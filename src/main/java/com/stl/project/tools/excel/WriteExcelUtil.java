package com.stl.project.tools.excel;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class WriteExcelUtil {
    private static Logger logger = org.apache.log4j.Logger.getLogger(WriteExcelUtil.class);
    public WriteExcelUtil(){}
    /*
    *
    * */
    public Workbook createWorkBook(String fileType){
        Object workbook = null;
        if (".xls".equals(fileType)){
            workbook = new HSSFWorkbook();
        }else if (".xlsx".equals(fileType)){
            workbook = new XSSFWorkbook();
        }else {
            logger.error("文件类型[" + fileType + "]不正确");
        }
        return (Workbook) workbook;
    }
    /**
     * 创建新的sheet
     * sheetName:Sheet名称
     */
    public Sheet createSheet(Workbook workbook, String sheetName) {
        Sheet sheet = null;
        try {
            sheet = workbook.createSheet(sheetName);
        } catch (Exception var5) {
            var5.printStackTrace();
        }
        return sheet;
    }
    /**
     * 创建Row
     * rowNum:创建的行数
     */
    public Row createRow(Sheet sheet, int rowNum) {
        Row row = null;
        try {
            row = sheet.createRow(rowNum);
        } catch (Exception var5) {
            var5.printStackTrace();
        }
        return row;
    }
    /*
     * 创建数据格
     * 创建的cell的下标
     * colNum:下标
     * */
    public Cell createCell(Row row, int colNum) {
        Cell cell = null;
        try {
            cell = row.createCell(colNum);
        } catch (Exception var5) {
            var5.printStackTrace();
        }
        return cell;
    }
    /*
     * 创建新的格子，并输入初始化值
     * celNum：写到第几个值
     * values：值（数组）
     * */
    public void writeValueToCell(Row row, int celStarNum, int celEndNum, String[] valus){
        HSSFRichTextString textString;
        for (int j = 0,i = celStarNum; i <= celEndNum; i++,j++){
            textString = new HSSFRichTextString(valus[j]);
            row.createCell(i).setCellValue(textString);
        }
    }
    /*
    * 设置行高
    *
    * */
    public void setAllRowHeight(Sheet sheet,double h){
        sheet.setDefaultRowHeight((short)(h*256));
    }
    /*
    * 设置列宽
    * i:需要设置列宽的数组
    * i1:对应设置列的宽度
    * */
    public void setColWidth(Sheet sheet,int[] i, int[] i1){
        for (int j = 0; j < i.length; j++){
            sheet.setColumnWidth(i[j],256*i1[j]);
        }
    }
    /*
     * 保存xls/xlsx
     * */
    public void saveWorkBook(Workbook workbook, String fileName) {
        try {
            File file = new File(fileName);
            OutputStream os = new FileOutputStream(file);
            workbook.write(os);
            os.flush();
            os.close();
        } catch (Exception var5) {
            logger.error("文档保存失败：" + var5.getMessage());
        }
    }
}
