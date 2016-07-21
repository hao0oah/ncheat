package com.founder.tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.founder.beans.TransFlow01;

/**
 * jxl实现操作Excel，只能操作256行
 * @author Hao
 * @date 2016/09/01
 */
public class XlsUtil {

	public static String[] COLUMS1 = {"账号","户名","交易日期","借贷方","发生额","余额","摘要","对方账号","对方户名","对方开户行"};
	public static int[] SIZE1 = {25,15,15,10,20,20,22,25,15,30};
	
	WritableWorkbook book1 = null;
	WritableSheet sheet1 = null;
	
	/**
	 * 生成‘账户所有交易’的xls文件，并填写表头
	 * @param filepath xls文件的全路径
	 * @param headList xls的表头元素
	 * @param headSize xls的列宽
	 */
	public XlsUtil(File filepath,String[] headList,int[] headSize) throws IOException, RowsExceededException, WriteException {
		book1 = Workbook.createWorkbook(filepath);
		sheet1 = book1.createSheet("Sheet1", 0);
		//生成表头
		for(int i=0;i<headList.length;i++){
			sheet1.addCell(new Label(i,0,headList[i]));
			sheet1.setColumnView(i, headSize[i]);
		}
	}
	
	/**
	 * 载入‘账户名和证件号’的xls文件
	 * @param filepath xls文件的全路径
	 * @param index 表头所在行
	 */
	public XlsUtil(File filepath,int index) throws BiffException, IOException, WriteException{
		Workbook wbtmp = Workbook.getWorkbook(filepath);
		book1 = Workbook.createWorkbook(filepath,wbtmp);
		sheet1 = book1.getSheet(0);
		
		//生成表头
		WritableCellFormat format = new WritableCellFormat();
		format.setBorder(Border.ALL, BorderLineStyle.THIN,Colour.BLACK);
		sheet1.addCell(new Label(2, index, "所有余额", format));
	}
	
	/**
	 * 将交易信息写入xls文件
	 * @param line 所在行
	 * @param tran 交易信息
	 */
	public void write1(int line,TransFlow01 tran) throws RowsExceededException, WriteException, IOException{
		sheet1.addCell(new Label(0,line,tran.getAcctNo()));
		sheet1.addCell(new Label(1,line,tran.getAcctName()));
		sheet1.addCell(new Label(2,line,tran.getTransDate()));
		sheet1.addCell(new Label(3,line,tran.getAcctType()==1?"借方":"贷方"));
		sheet1.addCell(new Label(4,line,tran.getAmount()));
		sheet1.addCell(new Label(5,line,tran.getBalance()));
		sheet1.addCell(new Label(6,line,tran.getRemark()));
		sheet1.addCell(new Label(7,line,tran.getOpAcctNo()));
		sheet1.addCell(new Label(8,line,tran.getOpAcctName()));
		sheet1.addCell(new Label(9,line,tran.getOpBankName()));
	}
	
	/**
	 * 从xls读取信息
	 */
	public List<String[]> readSheet2(int start,int end){
		List<String[]> lists = new ArrayList<String[]>();
		//忽略表头行，从1开始
		for(int i=start-1;i<end;i++){
			//存储每一列的值
			int column = sheet1.getColumns();
			String[] values = new String[column];
			for (int j = 0; j < column; j++) {
				Cell cell = sheet1.getCell(j,i);
				values[j] = cell.getContents();
			}
			lists.add(values);
		}
		return lists;
	}
	
	/**
	 * 将余额写入xls
	 */
	public void write2(int index,String balance) throws RowsExceededException, WriteException{
		WritableCellFormat format = new WritableCellFormat();
		format.setBorder(Border.ALL, BorderLineStyle.THIN,Colour.BLACK);
		sheet1.addCell(new Label(index,2,balance,format));
	}
	
	/**
	 * 写入并关闭xls文件
	 */
	public void closeBook() throws WriteException, IOException{
		if(book1 != null){
			book1.write();
			book1.close();
		}
	}
	
}
