package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import org.junit.Test;

import com.founder.beans.Com;
import com.founder.beans.TransFlow01;
import com.founder.tools.DateUtil;
import com.founder.tools.LogFileUtil;
import com.founder.tools.PropetyUtil;
import com.founder.tools.XlsUtil2;

public class UtilTest {
	
	/**
	 * UUID测试
	 */
	@Test
	public void UUIDTest(){
		String str = UUID.randomUUID().toString();
		System.out.println(str);
		long st2 = UUID.randomUUID().getLeastSignificantBits();
		System.out.println(st2);
		long st3 = UUID.randomUUID().getMostSignificantBits();
		System.out.println(st3);
	}
	
	/**
	 * 测试获取指定位数的随机数
	 */
	@Test
	public void getRandomNoTest() throws Exception{
		int len = 0;
		String str = Com.getRandomNo(len);
		System.out.println(str);
		len = 5;
		str = Com.getRandomNo(len);
		System.out.println(str);
		len = 14;
		str = Com.getRandomNo(len);
		System.out.println(str);
		len = 17;
		str = Com.getRandomNo(len);
		System.out.println(str);
		len = 28;
		str = Com.getRandomNo(len);
		System.out.println(str);
		len = 32;
		str = Com.getRandomNo(len);
		System.out.println(str);
	}
	
	/**
	 * 测试两个日期的相差天数
	 */
	@Test
	public void compareDateTest(){
		Date d1 = DateUtil.parse("20160701");
		Date d2 = DateUtil.parse("20170708");
		int a = DateUtil.compare(d2, d1);
		System.out.println(a);
		
		d1 = new Date();
		d2 = DateUtil.parse("20160725");
		a = DateUtil.compare(d1, d2);
		System.out.println(a);
	}
	
	@Test
	public void testCommon01(){
		String s = "100.00";
		Double a = Double.parseDouble(s);
		System.out.println(a.intValue());
		
		int b = Integer.parseInt(s.substring(0,s.indexOf(".")));
		System.out.println(b);
	}

	@Test
	public void testFileUtil(){
		LogFileUtil.writeLog("100403", "<book></book>");
	}
	
	@Test
	public void testProP(){
		String filepath = new PropetyUtil(Com.FILE_PATH).getValue("xlspath");
		filepath += DateUtil.getNow("yyyyMMddHHmmssSSS")+".xls";
		System.out.println("[文件路径："+filepath+"]");
		
	}
	
	@Test
	public void testFRie(){
		String filepath = "D:/logs/xls/20160822225843089.xls";
		System.out.println(File.separator);
		String tmp  = filepath.substring(filepath.lastIndexOf(File.separator)+1);
		System.out.println(tmp);
		tmp = filepath.substring(filepath.lastIndexOf("/")+1);
		System.out.println(tmp);
	}
	
	@Test
	public void testMatch(){
		String dates = "2016-08-09";
		System.out.println(dates.replaceAll("[^\\d]",""));
	}
	
	@Test
	public void testMatch2(){
		String idcard = "，12812837912837X.";
		
		System.out.println(idcard.replaceAll("[^\\d^X]", ""));
	}
	
	@Test
	public void testStr222(){
		String filename = "asdf.xls";
		filename = filename.substring(filename.lastIndexOf("."));
		System.out.println(filename);
	}
	
	@Test
	public void testxls(){
		try {
			XlsUtil2 xls = new XlsUtil2(XlsUtil2.COLUMS1, XlsUtil2.SIZE1);
			
			TransFlow01 t1 = new TransFlow01();
			t1.setAcctName("Bob");
			t1.setAcctNo("17364782347234");
			t1.setAcctType(1);
			t1.setAmount("11111");
			t1.setBalance("122132");
			t1.setOpAcctName("Tom");
			
			xls.write1(1, t1);
			File file = makeFilePath();
			xls.closeAndSave(file);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public File makeFilePath() throws IOException{
		//创建目录
		String filepath = new PropetyUtil(Com.FILE_PATH).getValue("xlspath");
		File tmp = new File(filepath);
		if(!tmp.exists()) tmp.mkdirs();
		
		//创建文件
		filepath += DateUtil.getNow("yyyyMMddHHmmssSSS")+".xls";
		File file = new File(filepath);
		if(!file.exists()) file.createNewFile();
		System.out.println("[xls文件路径："+filepath+"]");
		
		return file;
	}
}
