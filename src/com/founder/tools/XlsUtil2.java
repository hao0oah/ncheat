package com.founder.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.founder.beans.TransFlow01;

/**
 * POI实现操作Excel
 * @author Hao
 * @date 2016/09/02
 */
public class XlsUtil2 {

	public static String[] COLUMS1 = {"账号","户名","交易日期","借贷方","发生额","余额","摘要","对方账号","对方户名","对方开户行"};
	public static int[] SIZE1 = {25,15,15,10,20,20,22,25,15,30};
	
	Workbook book1 = null;
	Sheet sheet1 = null;
	
	/**
	 * 生成‘账户所有交易’的xls文件，并填写表头
	 * @param filepath xls文件的全路径
	 * @param headList xls的表头元素
	 * @param headSize xls的列宽
	 */
	public XlsUtil2(String[] headList,int[] headSize) throws FileNotFoundException, IOException{

		book1 = new HSSFWorkbook();
		sheet1 = book1.createSheet("Sheet1");
		
		//生成表头
		Row row = sheet1.createRow(0);
		for(int i=0;i<headList.length;i++){
			sheet1.setColumnWidth(i, headSize[i]*250);
			Cell cell = row.createCell(i);
			cell.setCellValue(headList[i]);
		}
	}
	
	/**
	 * 载入‘账户名和证件号’的xls文件
	 * @param filepath xls文件的全路径
	 * @param index 表头所在行
	 */
	public XlsUtil2(File filepath,int index) throws FileNotFoundException, IOException {
		
		book1 = new HSSFWorkbook(new FileInputStream(filepath));
		sheet1 = book1.getSheetAt(0);
		
		//生成表头
		Row row0 = sheet1.getRow(index-1);
		Cell cell = row0.createCell(2);
		cell.setCellValue("所有余额");
	}
	
	/**
	 * 将交易信息写入xls文件
	 * @param line 所在行
	 * @param tran 交易信息
	 */
	public void write1(int line,TransFlow01 tran) {
		Row row = sheet1.createRow(line);
		Cell cell = row.createCell(0);
		cell.setCellValue(tran.getAcctNo());
		cell = row.createCell(1);
		cell.setCellValue(tran.getAcctName());
		cell = row.createCell(2);
		cell.setCellValue(tran.getTransDate());
		cell = row.createCell(3);
		cell.setCellValue(tran.getAcctType()==1?"借方":"贷方");
		cell = row.createCell(4);
		cell.setCellValue(tran.getAmount());
		cell = row.createCell(5);
		cell.setCellValue(tran.getBalance());
		cell = row.createCell(6);
		cell.setCellValue(tran.getRemark());
		cell = row.createCell(7);
		cell.setCellValue(tran.getOpAcctNo());
		cell = row.createCell(8);
		cell.setCellValue(tran.getOpAcctName());
		cell = row.createCell(9);
		cell.setCellValue(tran.getOpBankName());
	}
	
	/**
	 * 从xls读取信息
	 */
	public List<String[]> readSheet2(int start,int end){
		List<String[]> lists = new ArrayList<String[]>();
		//下表从0开始
		for(int i=start-1;i<end;i++){
			Row row = sheet1.getRow(i);
			//存储每一列的值
			int column = 2;
			String[] values = new String[column];
			for (int j = 0; j < column; j++) {
				Cell cell = row.getCell(j);
				int ctype = cell.getCellType();
				switch(ctype){
				case Cell.CELL_TYPE_BLANK:
					break;
				case Cell.CELL_TYPE_BOOLEAN:
					values[j] = String.valueOf(cell.getBooleanCellValue());
					break;
				case Cell.CELL_TYPE_ERROR:
					values[j] = new String(new byte[]{cell.getErrorCellValue()});
					break;
				case Cell.CELL_TYPE_FORMULA:
					values[j] = cell.getCellFormula();
					break;
				case Cell.CELL_TYPE_NUMERIC:
					values[j] = String.valueOf(cell.getNumericCellValue());
					break;
				case Cell.CELL_TYPE_STRING:
					values[j] = cell.getStringCellValue();
					break;
				}
			}
			lists.add(values);
		}
		return lists;
	}
	
	/**
	 * 将余额写入xls
	 */
	public void write2(int index,String balance){
		Row row = sheet1.getRow(index);
		Cell cell = row.createCell(2);
		cell.setCellValue(balance);
	}
	
	/**
	 * 写入并关闭xls文件
	 */
	public void closeAndSave(File file) throws IOException {
		FileOutputStream fileOut = new FileOutputStream(file);
		book1.write(fileOut); 
		if(book1 != null){
			book1.close();
		}
		fileOut.close();
	}
	
}
