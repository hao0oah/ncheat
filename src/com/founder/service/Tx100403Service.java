package com.founder.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.founder.beans.Com;
import com.founder.beans.ReportRecord;
import com.founder.database.RecordDao;
import com.founder.database.Tx100403Dao;
import com.founder.tools.DateUtil;
import com.founder.tools.LogFileUtil;
import com.founder.tools.XMLUtil;

import cfca.safeguard.Result;
import cfca.safeguard.api.bank.ClientEnvironment;
import cfca.safeguard.api.bank.Constants;
import cfca.safeguard.api.bank.SGBusiness;
import cfca.safeguard.api.bank.bean.tx.upstream.Tx100403;
import cfca.safeguard.api.bank.util.ResultUtil;
import cfca.safeguard.tx.business.bank.TxUnusualOpencard_Accounts;

/**
 * 可疑名单上报-异常开卡
 * @date 2016-07-19
 */
@Service("tx100403")
public class Tx100403Service {
	public final Logger log = Logger.getLogger(this.getClass());
	private SGBusiness sgBusiness;
	
	@Resource
	private Tx100403Dao tx100403Dao;
	
	@Resource
	private RecordDao recordDao;
	
    public Tx100403Service() throws Exception {
    	ClientEnvironment.initTxClientEnvironment(Com.CONFIG_PATH);
		sgBusiness = new SGBusiness();
	}

    /**
     * @param tx100403
     * @return 直接发送-true;手工发送-false;
     */
	public boolean execute(Tx100403 tx100403) throws Exception {
        String transSerialNumber = Com.BANK_CODE+Com.TRUST_CODE+Com.APP_ID+Com.getRandomNo(28-2);
        String fromTGOrganizationId="";

        log.info("[可疑账户事件类型编号为："+tx100403.getFeatureCode()+"]");
        
        tx100403.setTransSerialNumber(transSerialNumber);
        tx100403.setApplicationID(Com.N0403+Com.getRandomNo(32));
        tx100403.setBankID(Com.BANK_CODE);
        
        String requestXML = sgBusiness.tx100403(tx100403,fromTGOrganizationId);
        log.info("发送报文：\n"+XMLUtil.formatXml(requestXML));
        
        if(!Com.DEBUG){
        	String responseXML = sgBusiness.sendPackagedRequestXML(requestXML);
            log.info("返回报文：\n"+XMLUtil.formatXml(responseXML));
            Result result = ResultUtil.chageXMLToResult(responseXML);

            if (Constants.SUCCESS_CODE_VALUE.equals(result.getCode())) {
            	LogFileUtil.writeLog("100403_suc_"+tx100403.getFeatureCode(), XMLUtil.formatXml(requestXML));
                log.info("[可疑名单上报-(异常开卡)成功]");
            } else {
            	LogFileUtil.writeLog("100403_fail_"+tx100403.getFeatureCode(), XMLUtil.formatXml(requestXML));
                log.info("[可疑名单上报-(异常开卡)失败,错误码=" + result.getCode() + ",错误信息=" + result.getDescription()+"]");
            }
            return true;
        } else {
        	String filename = LogFileUtil.writeLog("100403_"+tx100403.getFeatureCode(), XMLUtil.formatXml(requestXML));
        	log.info("[DEBUG模式，已将报文存储在("+filename+")请手动发送！]");
        	return false;
        }
        
	}
	
	/**
	 * 获取所有频繁开户客户号
	 */
	public List<String> getF1001grCusts(Map<String,?> param){
		return tx100403Dao.getF1001grCusts(param);
	}
	public List<String> getF1001dgCusts(Map<String,?> param){
		return tx100403Dao.getF1001dgCusts(param);
	}
	
	/**
	 * 获取所有频繁开户客户开户日期
	 */
	public List<String> getF1001grDays(Map<String,?> param){
		return tx100403Dao.getF1001grDays(param);
	}
	public List<String> getF1001dgDays(Map<String,?> param){
		return tx100403Dao.getF1001dgDays(param);
	}
	
	/**
	 * 获取户可疑客户信息
	 */
	public Tx100403 getFgrCust(Map<String,?> param){
		return tx100403Dao.getFgrCust(param);
	}
	public Tx100403 getFdgCust(Map<String,?> param){
		return tx100403Dao.getFdgCust(param);
	}
	
	
	/**
	 * 1001 - 获取可疑客户卡号信息 
	 */
	public List<TxUnusualOpencard_Accounts> getF1001grAcntList(Map<String,?> param){
		return tx100403Dao.getF1001grAcntList(param);
	}
	
	
	/**
	 * 获取可疑客户卡号信息 
	 */
	public List<TxUnusualOpencard_Accounts> getFgrAcntList(Map<String,?> param){
		return tx100403Dao.getFgrAcntList(param);
	}
	public List<TxUnusualOpencard_Accounts> getFdgAcntList(Map<String,?> param){
		return tx100403Dao.getFdgAcntList(param);
	}
	
	/**
	 * 是否是频繁开户客户，day为连续几天。
	 * 把所有符合条件的开户日放入list中
	 */
	public boolean isF1001Cust(List<String> dates,List<String> list,int day){
		int len = dates.size();
		for(int i=0;i<len&&i+2<len;i++){
			String dt1 = dates.get(i);
			String dt = dates.get(i+1);
			String dt2 = dates.get(i+2);
			
			Date d1 = DateUtil.parse(dt1,"yyyy/MM/dd");
			Date d2 = DateUtil.parse(dt2,"yyyy/MM/dd");
			if(DateUtil.compare(d1, d2) < day){
				if(!list.contains(dt1)) list.add(dt1);
				if(!list.contains(dt)) list.add(dt);
				if(!list.contains(dt2)) list.add(dt2);
			}
		}
		
		return list.size()>0;
	}
	
	
	/**
	 * 获取所有累计开户超过指定数目的客户身份证号
	 */
	public List<String> getF1002grCusts(Map<String,?> param){
		return tx100403Dao.getF1002grCusts(param);
	}
	public List<String> getF1002dgCusts(Map<String,?> param){
		return tx100403Dao.getF1002dgCusts(param);
	}
	
	
	/**
	 * 获取当日开卡的客户身份证号
	 */
	public List<String> getFgrCards(Map<String,?> param){
		return tx100403Dao.getFgrCards(param);
	}
	
	/**
	 * 根据客户身份证号，获取总共开卡数
	 */
	public int getFgrCount(String zjbh){
		return tx100403Dao.getFgrCount(zjbh);
	}
	
	/**
	 * 获取某日某客户是否开卡
	 */
	public boolean getFgrIsCount(Map<String,?> param){
		return tx100403Dao.getFgrIsCount(param)>0;
	}
	
	/**
	 * 将可疑客户插入表中
	 */
	public void recordCust(ReportRecord record){
		recordDao.insert(record);
	}
	
	/**
	 * 客户是否已经上传过
	 */
	public boolean isRecorded(Map<String,?> param){
		return recordDao.selectByCust(param)!=null;
	}

}
