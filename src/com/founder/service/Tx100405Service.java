package com.founder.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cfca.safeguard.Result;
import cfca.safeguard.api.bank.ClientEnvironment;
import cfca.safeguard.api.bank.Constants;
import cfca.safeguard.api.bank.SGBusiness;
import cfca.safeguard.api.bank.bean.tx.upstream.Tx100405;
import cfca.safeguard.api.bank.util.ResultUtil;
import cfca.safeguard.tx.business.bank.TxExceptionalEvent_Account;
import cfca.safeguard.tx.business.bank.TxExceptionalEvent_Accounts;
import cfca.safeguard.tx.business.bank.TxExceptionalEvent_Transaction;

import com.founder.beans.Com;
import com.founder.beans.Tx405Detail;
import com.founder.beans.Tx405Item;
import com.founder.database.Item405Dao;
import com.founder.database.Tx100405Dao;
import com.founder.database2.Tx100405Dao2;
import com.founder.tools.DateUtil;
import com.founder.tools.LogFileUtil;
import com.founder.tools.XMLUtil;

/**
 * 可疑名单上报-异常事件
 * @date 2016-07-19
 */
@Service("tx100405")
@Transactional
public class Tx100405Service {
	public final Logger log = Logger.getLogger(this.getClass());
	private SGBusiness sgBusiness;
	
	@Resource
	private Item405Dao item405Dao;
	
	@Resource
	private Tx100405Dao tx100405Dao;
	
	@Resource
	private Tx100405Dao2 tx100405Dao2;
	
    public Tx100405Service() throws Exception {
    	ClientEnvironment.initTxClientEnvironment(Com.CONFIG_PATH);
		sgBusiness = new SGBusiness();
	}

    /**
     * 直接生成xml发送前置机
     */
	public boolean execute2(Tx100405 tx100405) throws Exception {
        String transSerialNumber = Com.BANK_CODE+Com.TRUST_CODE+Com.APP_ID+Com.getRandomNo(28-2);
        String fromTGOrganizationId = "";
        
    	String feature = tx100405.getAccountsList().get(0).getAccountList().get(0).getTransactionList().get(0).getFeatureCode();
        log.info("[可疑账户事件类型编号为："+feature+"]");
        
        tx100405.setBankID(Com.BANK_CODE);
        tx100405.setApplicationID(Com.N0405+Com.getRandomNo(32));
        tx100405.setTransSerialNumber(transSerialNumber);

        String requestXML = sgBusiness.tx100405(tx100405, fromTGOrganizationId);
        log.info("发送报文：\n"+XMLUtil.formatXml(requestXML));
        
        String responseXML = sgBusiness.sendPackagedRequestXML(requestXML);
    	log.info("返回报文：\n"+XMLUtil.formatXml(responseXML));
    	Result result = ResultUtil.chageXMLToResult(responseXML);
    	
    	if (Constants.SUCCESS_CODE_VALUE.equals(result.getCode())) {
    		log.info("[可疑名单上报-异常事件成功]");
    	} else {
    		log.info("[可疑名单上报-异常事件失败,错误码=" + result.getCode() + ",错误信息=" + result.getDescription()+"]");
    		return false;
    	}
    	
    	return true;
	}
    
	/**
	 * 生成xml文件，并不发送，以供筛选后发送
	 */
	public boolean execute(Tx100405 tx100405) throws Exception {
        String transSerialNumber = Com.BANK_CODE+Com.TRUST_CODE+Com.APP_ID+Com.getRandomNo(28-2);
        String fromTGOrganizationId = "";
        
    	String feature = tx100405.getAccountsList().get(0).getAccountList().get(0).getTransactionList().get(0).getFeatureCode();
        log.info("[可疑账户事件类型编号为："+feature+"]");
        
        tx100405.setBankID(Com.BANK_CODE);
        tx100405.setApplicationID(Com.N0405+Com.getRandomNo(32));
        tx100405.setTransSerialNumber(transSerialNumber);

        String requestXML = sgBusiness.tx100405(tx100405, fromTGOrganizationId);
        log.info("生成报文：\n"+XMLUtil.formatXml(requestXML));
        
        /*if(!Com.DEBUG){
        	String responseXML = sgBusiness.sendPackagedRequestXML(requestXML);
        	log.info("返回报文：\n"+XMLUtil.formatXml(responseXML));
        	Result result = ResultUtil.chageXMLToResult(responseXML);
        	
        	if (Constants.SUCCESS_CODE_VALUE.equals(result.getCode())) {
        		LogFileUtil.writeLog("100405_suc_"+feature, XMLUtil.formatXml(requestXML));
        		log.info("[可疑名单上报-异常事件成功]");
        	} else {
        		LogFileUtil.writeLog("100405_fail_"+feature, XMLUtil.formatXml(requestXML));
        		log.info("[可疑名单上报-异常事件失败,错误码=" + result.getCode() + ",错误信息=" + result.getDescription()+"]");
        	}
        	return true;
        }*/
        
    	String filename = LogFileUtil.writeLog("100405_"+feature, XMLUtil.formatXml(requestXML));
    	log.info("[已将报文存储在("+filename+")请手动发送！]");

    	
    	Tx405Item item = new Tx405Item();
		
		TxExceptionalEvent_Accounts accounts = tx100405.getAccountsList().get(0);
		item.setAccountName(accounts.getAccountName());
		item.setCardNumber(accounts.getCardNumber());
		
		TxExceptionalEvent_Account account = accounts.getAccountList().get(0);
		item.setAccountNumber(account.getAccountNumber());
		item.setAccountSerial(account.getAccountSerial());
		item.setAccountType(account.getAccountType());
		item.setAccountStatus(account.getAccountStatus());
		
		//该卡号开户行
		item.setBankid(item405Dao.getOpenBankId(account.getAccountNumber()));
		log.info("[开卡行号："+item.getBankid()+"]");
		
		item.setStatus(0);
		item.setExeDate(DateUtil.getNowDate());
		item.setFeature(feature);
		item.setFilepath(filename);
		item405Dao.addItem(item);
		
		int payCount = 0;
		int peyCount = 0;
		double payAmount = 0;
		double peyAmount = 0;
		
		List<TxExceptionalEvent_Transaction> trans = account.getTransactionList();
		for(TxExceptionalEvent_Transaction tran : trans){
			if("0".equals(tran.getBorrowingSigns())){		//借
				payCount++;
				payAmount += Double.parseDouble(tran.getTransactionAmount());
			}else if("1".equals(tran.getBorrowingSigns())){	//贷
				peyCount++;
				peyAmount += Double.parseDouble(tran.getTransactionAmount());
			}
			
			Tx405Detail detail = new Tx405Detail();
			detail.setItemId(item.getId());						//ItemID
			detail.setTransFlow(tran.getTransactionSerial());	//交易流水
			detail.setTransTime(tran.getTransactionTime());		//交易时间
			detail.setTrsCode(tran.getTransactionType());		//交易码
			detail.setAcctNo(account.getAccountNumber());		//账号
			detail.setAcctName(accounts.getAccountName());		//名称
			detail.setCardNo(accounts.getCardNumber());			//卡号
			detail.setVchType(tran.getVoucherType());			//凭证类型
			detail.setVchCode(tran.getVoucherCode());			//凭证号
			detail.setBorrowSign(tran.getBorrowingSigns());		//借贷方
			detail.setTrsType(tran.getTransactionRemark());		//交易类型
			detail.setTrsAmount(tran.getTransactionAmount());	//交易金额
			detail.setTrsBalance(tran.getAccountBalance());		//交易余额
			detail.setOpBankName(tran.getOpponentDepositBankID());//对方行名
			detail.setOpAcctNo(tran.getOpponentAccountNumber());//对方账号
			detail.setOpAcctName(tran.getOpponentName());		//对方名称
			
			item405Dao.addItemDetail(detail);
		}
		
		item.setPayAmount(payAmount);
		item.setPayCount(payCount);
		item.setPeyAmount(peyAmount);
		item.setPeyCount(peyCount);
		item405Dao.updateItem(item);
		
		return true;
	}
	
	
	/**
	 * 根据账号查询账号信息
	 */
	public TxExceptionalEvent_Accounts getF405grCust(Map<String,?> param){
		return tx100405Dao.getF405grCust(param);
	}
	
	/**
	 * 根据账号查询账号信息
	 */
	public TxExceptionalEvent_Account getF405grAcnt(Map<String,?> param){
		return tx100405Dao.getF405grAcnt(param);
	}
	
	/**
	 * 根据流水号，查询交易流水信息
	 */
	public TxExceptionalEvent_Transaction getTransaction(Map<String,?> param){
		return tx100405Dao2.getTransaction(param);
	}
	
	/**
	 * 根据提供的条件，查询交易流水信息
	 */
	public List<TxExceptionalEvent_Transaction> listgrTransactions(Map<String,?> param){
		return tx100405Dao2.listgrTransactions(param);
	}
	public Integer getgrTransactionsCount(Map<String,?> param){
		return tx100405Dao2.getgrTransactionsCount(param);
	}
	
	/**
	 * 获取符合分散转入集中转出的账号 - 3006
	 */
	public List<String> listgr3006Acnt(Map<String,?> param){
		List<String> result = new ArrayList<String>();
		
		List<Map<String,Object>> flows = tx100405Dao2.listgr3006flows(param);
		if(flows != null){
			for (Map<String, Object> map : flows) {
				map.put("last", param.get("last"));
				
				int mcount = Integer.parseInt(map.get("mcount").toString());
				Integer count = tx100405Dao2.hasgr3006flows(map);
				if(count!=null && count>0 && 3*count<=mcount){		//转入/转出>=3
					result.add(map.get("account").toString());
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 获取符合分散转出集中转入的账号 - 3007
	 */
	public List<String> listgr3007Acnt(Map<String,?> param){
		List<String> result = new ArrayList<String>();
		
		List<Map<String,Object>> flows = tx100405Dao2.listgr3007flows(param);
		if(flows != null){
			for (Map<String, Object> map : flows) {
				map.put("last", param.get("last"));
				
				int mcount = Integer.parseInt(map.get("mcount").toString());
				Integer count = tx100405Dao2.hasgr3007flows(map);
				if(count!=null && count>0 && 3*count<=mcount){		//转出/转入>=3
					result.add(map.get("account").toString());
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 获取符合连续多笔交易的账号 - 3008
	 */
	public List<String> listgr3008Acnt(Map<String,?> param){
		List<String> result = new ArrayList<String>();
		
		List<Map<String,Object>> flows = tx100405Dao2.listgr3008flows(param);
		if(flows != null){
			for (Map<String, Object> map : flows) {
				map.put("last", param.get("last"));
				
//				int mcount = Integer.parseInt(map.get("mcount").toString());
//				Integer count = tx100405Dao2.hasgr3007flows(map);
//				if(count!=null && count>0 && 3*count<=mcount){		//转出/转入>=3
//					result.add(map.get("account").toString());
//				}
				//TODO: 判断金额是否相近
				
				result.add(map.get("khzh").toString());
			}
		}
		
		return result;
	}
	
	/**
	 * 获取所有的符合3006、3007、3008的交易记录
	 */
	public List<TxExceptionalEvent_Transaction> getgrTransactions(Map<String,?> param){
		return tx100405Dao2.getgrTransactions(param);
	}
	
	
	/**
	 * 更新筛选结果，确定是否上传
	 */
	public int updateItem(long id){
		Tx405Item item = item405Dao.queryItemsById(id);
		
		int status = 0;
		String oldpath = item.getFilepath().trim();
		String filename = oldpath.substring(oldpath.lastIndexOf(File.separatorChar)+1);
		String newpath = null;
		if(item.getStatus() == 1){		//将不需要上传改为需要上传
			if(!oldpath.contains(Com.IGNORE))	return status;
			newpath = oldpath.substring(0,oldpath.lastIndexOf(Com.IGNORE))+filename;
			status = 0;
		}else if(item.getStatus() == 0){//将需要上传改为不需要上传
			newpath = oldpath.substring(0,oldpath.lastIndexOf(File.separatorChar)+1)+Com.IGNORE;
			File file = new File(newpath);
			if(!file.exists()) file.mkdirs();
			newpath += (File.separator+filename);
			status = 1;
		}
		
		String cmd = "mv "+oldpath+" "+newpath;
		log.info("[移动文件命令："+cmd+"]");
		
		new File(oldpath).renameTo(new File(newpath));
		//Runtime.getRuntime().exec(cmd);
		item.setFilepath(newpath);
		item.setStatus(status);
		item405Dao.updateItemStatus(item);
		
		return status;
	}
	
	
	/**
	 * 分页查询筛选结果
	 */
	public List<Tx405Item> queryItemsByPage(Map<String,?> param){
		return item405Dao.queryItemsByPage(param);
	}
	
	/**
	 * 获取总数
	 */
	public int queryItemsCount(Map<String,?> param){
		Integer count = item405Dao.queryItemsCount(param); 
		return count==null?0:count;
	}
	
	/**
	 * 根据ID查询
	 */
	public Tx405Item queryItemsById(long id){
		return item405Dao.queryItemsById(id);
	}
	
	/**
	 * 根据ItemID查询交易流水明细
	 */
	public List<Tx405Detail> queryItemDetail(long id){
		return item405Dao.queryItemDetail(id);
	}
	
	/**
	 * 手工上报xml字符串
	 * @param requestXML
	 */
	public boolean manualReport(String requestXML) throws Exception{
		log.info("发送报文：\n"+XMLUtil.formatXml(requestXML));
		String responseXML = sgBusiness.sendPackagedRequestXML(requestXML);
    	log.info("返回报文：\n"+XMLUtil.formatXml(responseXML));
    	Result result = ResultUtil.chageXMLToResult(responseXML);
    	
    	if (Constants.SUCCESS_CODE_VALUE.equals(result.getCode())) {
    		log.info("[可疑名单上报-异常事件成功]");
    		return true;
    	} else {
    		log.info("[可疑名单上报-异常事件失败,错误码=" + result.getCode() + ",错误信息=" + result.getDescription()+"]");
    		return false;
    	}
	}
	
}
