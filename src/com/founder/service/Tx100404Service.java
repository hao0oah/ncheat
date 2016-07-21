package com.founder.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.founder.beans.Com;
import com.founder.database.Tx100404Dao;
import com.founder.tools.XMLUtil;

import cfca.safeguard.Result;
import cfca.safeguard.api.bank.Constants;
import cfca.safeguard.api.bank.bean.tx.upstream.Tx100404;
import cfca.safeguard.api.bank.util.ResultUtil;
import cfca.safeguard.tx.business.bank.TxInvolvedAccount_Account;

/**
 * 可疑名单上报-涉案账户
 * @date 2016-07-19
 */
@Service("tx100404")
public class Tx100404Service extends BaseService{
	public final Logger log = Logger.getLogger(this.getClass());

	@Resource
	private Tx100404Dao tx100404Dao;
	
    public Tx100404Service() throws Exception {
		super();
	}

	@Override
	public void execute(String featrue,Map<String,?> param) throws Exception {
        String transSerialNumber = Com.BANK_CODE+Com.TRUST_CODE+Com.APP_ID+Com.getRandomNo(28-2);
        String fromTGOrganizationId="";

        Tx100404 tx100404 = null;

        if(Com.F0000.equals(featrue)){
        	//TODO:取消上传涉案账户
        	return;
        } else if(Com.F2001.equals(featrue)){
        	tx100404 = tx100404Dao.getF2001grCust(param);
        	if(tx100404 == null){
        		 log.info("[没有需要上报的涉案账户]");
        		 return;
        	}
        	
        	List<TxInvolvedAccount_Account> accountList = tx100404Dao.getF2001grAcntList(param);
        	for (TxInvolvedAccount_Account account : accountList) {
        		Map<String,String> map = new HashMap<String, String>();
        		map.put("khzh", account.getAccountNumber());
				account.setTransactionList(tx100404Dao.getF2001grTransList(param));
			}
        	tx100404.setAccountList(accountList);
        }
        
        tx100404.setTransSerialNumber(transSerialNumber);
        tx100404.setApplicationID(Com.N0404+Com.getRandomNo(32));
        tx100404.setFeatureCode(featrue);
        tx100404.setBankID(Com.BANK_CODE);

        String requestXML = sgBusiness.tx100404(tx100404,fromTGOrganizationId);
        log.info("发送报文：\n"+XMLUtil.formatXml(requestXML));
        String responseXML = sgBusiness.sendPackagedRequestXML(requestXML);
        log.info("返回报文：\n"+XMLUtil.formatXml(responseXML));
        Result result = ResultUtil.chageXMLToResult(responseXML);

        if (Constants.SUCCESS_CODE_VALUE.equals(result.getCode())) {
        	log.info("[可疑名单上报-涉案账户成功]");
        } else {
            log.info("[可疑名单上报-涉案账户失败,错误码=" + result.getCode() + ",错误信息=" + result.getDescription()+"]");
        }
	}
	
	/**
	 * 获取所有涉案账户
	 */
	public List<String> getgrF2001AllCust(Map<String,?> param){
		return tx100404Dao.getF2001grCusts(param);
	}

}
