package com.founder.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import com.founder.beans.Com;

public class LogFileUtil {
	
	private static String filepath = null;
	
	static{
		filepath = new PropetyUtil(Com.FILE_PATH).getValue("filepath");
	}
	
	public static String writeLog(String file,String str){
		File path = new File(filepath+DateUtil.getNowDate()+File.separator+file.substring(file.indexOf("_")+1));
		if(!path.exists()){
			path.mkdirs();
		}
		
		String filename = path.getAbsolutePath()+File.separator+file+"_"+DateUtil.getNow("HHmmssSSS")+".xml";
		File logfile = new File(filename);
		
		try {
			PrintWriter pw = new PrintWriter(logfile,"GBK");
			pw.println(str);
			pw.flush();
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return filename;
	}

}
