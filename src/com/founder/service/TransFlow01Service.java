package com.founder.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.founder.beans.Com;
import com.founder.beans.TransFlow01;
import com.founder.database2.TransFlow01Dao;
import com.founder.tools.DateUtil;
import com.founder.tools.PropetyUtil;
import com.founder.tools.XlsUtil2;

/**
 * 获取个人/对公交易信息
 */
@Service("transflowService")
public class TransFlow01Service {
	public final Logger log = Logger.getLogger(this.getClass());

	private static final int MAX_ROWS = 50;		//一次从数据库中取出多少条数据
	
	@Resource
	private TransFlow01Dao transDao;
	
	/**
	 * 获取个人交易明细，并写入xls文件
	 * @return xls文件路径
	 */
	public String reportgrTrans01(Map<String,Object> param) throws RowsExceededException, WriteException, IOException{
		int count = transDao.listgrTrans01Count(param);
		if(count == 0){
			return null;
		}
		
		File file = makeFilePath();
		XlsUtil2 xls = new XlsUtil2(XlsUtil2.COLUMS1,XlsUtil2.SIZE1);
		
		int num = 1;
		param.put("limit", MAX_ROWS);
		for(int i=0;i<count;i+=MAX_ROWS){
			param.put("begin", i);
			List<TransFlow01> trans = transDao.listgrTrans01(param);
			for (TransFlow01 tran : trans) {
				xls.write1(num++, tran);
			}
		}
		xls.closeAndSave(file);
		return file.getAbsolutePath();
	}
	
	/**
	 * 获取对公交易明细，并写入xls文件
	 * @return xls文件路径
	 */
	public String reportdgTrans01(Map<String,Object> param) throws RowsExceededException, WriteException, IOException{
		int count = transDao.listdgTrans01Count(param);
		if(count == 0){
			return null;
		}
		
		File file = makeFilePath();
		XlsUtil2 xls = new XlsUtil2(XlsUtil2.COLUMS1,XlsUtil2.SIZE1);
		
		int num = 1;
		param.put("limit", MAX_ROWS);
		for(int i=0;i<count;i+=MAX_ROWS){
			param.put("begin", i);
			List<TransFlow01> trans = transDao.listdgTrans01(param);
			for (TransFlow01 tran : trans) {
				xls.write1(num++, tran);
			}
		}
		xls.closeAndSave(file);
		return file.getAbsolutePath();
	}
	
	/**
	 * 在系统指定目录生成空的xls文件
	 * @return xls文件
	 */
	public File makeFilePath() throws IOException{
		//创建目录
		String filepath = new PropetyUtil(Com.FILE_PATH).getValue("xlspath");
		File tmp = new File(filepath);
		if(!tmp.exists()) tmp.mkdirs();
		
		//创建文件
		filepath += DateUtil.getNow("yyyyMMddHHmmssSSS")+".xls";
		File file = new File(filepath);
		if(!file.exists()) file.createNewFile();
		log.info("[xls文件路径："+filepath+"]");
		
		return file;
	}
	
	/**
	 * 获取所有个人账户余额，并写入xls文件
	 * @return xls文件路径
	 */
	public int reportgrTrans02(File upfile,int start,int end) throws BiffException, IOException, WriteException{
		//表头所在的位置是(start-1)
		XlsUtil2 xls = new XlsUtil2(upfile,start-1);
		List<String[]> lists = xls.readSheet2(start,end);
		
		int result = 0;
		Map<String,String> params = new HashMap<String, String>();
		for (int i = start-1; i < end; i++) {
			String name = lists.get(i-start+1)[0];
			params.put("name", name);
			String idcard = lists.get(i-start+1)[1];
			if(idcard == null) continue;
			
			params.put("idcard", idcard.replaceAll("[^\\d^X]", ""));
			log.info("[name="+name+",idcard="+idcard+"]");
			
			//查询余额，并写入xls
			String balance = null;
			Map<String,Object> ret = transDao.getgrBalance(params);
			if(ret == null || ret.get("balance") == null){
				balance = "无";
			}else{
				balance = ret.get("balance").toString();
				result++;
			}

			xls.write2(i, balance);
		}
		xls.closeAndSave(upfile);
		
		return result;
	}
	
	/**
	 * 获取所有对公账户余额，并写入xls文件
	 * @return xls文件路径
	 */
	public int reportdgTrans02(File upfile,int start,int end){
		
		return 0;
	}

}
