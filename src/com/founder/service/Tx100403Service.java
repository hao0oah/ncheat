package com.founder.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.founder.beans.Com;
import com.founder.database.Tx100403Dao;
import com.founder.tools.XMLUtil;

import cfca.safeguard.Result;
import cfca.safeguard.api.bank.Constants;
import cfca.safeguard.api.bank.bean.tx.upstream.Tx100403;
import cfca.safeguard.api.bank.util.ResultUtil;

/**
 * 可疑名单上报-异常开卡
 * @date 2016-07-19
 */
@Service("tx100403")
public class Tx100403Service extends BaseService{
	public final Logger log = Logger.getLogger(this.getClass());

	@Resource
	private Tx100403Dao tx100403Dao;
	
    public Tx100403Service() throws Exception {
		super();
	}

	@Override
	public void execute(String featrue) throws Exception {
        String transSerialNumber = Com.BANK_CODE+Com.TRUST_CODE+Com.getRandomNo(28);
        String fromTGOrganizationId="";

        Tx100403 tx100403 = null;
        
        //数据库查询参数
        Map<String,String> param = new HashMap<String,String>();
        param.put("khbh", "1001");
        log.info("[可疑账户事件类型编号为："+featrue+"]");
        if(Com.F1001.equals(featrue)){
        	tx100403 = tx100403Dao.getF1001Accnt(param);
        	if(tx100403 == null){
        		 log.info("[没有需要上报的可疑账户]");
        		 return;
        	}
        	tx100403.setAccountsList(tx100403Dao.getF1001List(param));
        } else if(Com.F1002.equals(featrue)){
        	tx100403 = tx100403Dao.getF1002Accnt(param);
        	if(tx100403 == null){
        		log.info("[没有需要上报的可疑账户]");
       			return;
        	}
        	tx100403.setAccountsList(tx100403Dao.getF1002List(param));
        }
        
        tx100403.setTransSerialNumber(transSerialNumber);
        tx100403.setApplicationID(Com.N0403+Com.getRandomNo(32));
        tx100403.setFeatureCode(featrue);
        tx100403.setBankID(Com.BANK_CODE);
        
        String requestXML = sgBusiness.tx100403(tx100403,fromTGOrganizationId);
        log.info("发送报文：\n"+XMLUtil.formatXml(requestXML));
        String responseXML = sgBusiness.sendPackagedRequestXML(requestXML);
        log.info("返回报文：\n"+XMLUtil.formatXml(responseXML));
        Result result = ResultUtil.chageXMLToResult(responseXML);

        if (Constants.SUCCESS_CODE_VALUE.equals(result.getCode())) {
            log.info("[可疑名单上报-异常开卡成功]");
        } else {
            log.info("[可疑名单上报-异常开卡失败,错误码=" + result.getCode() + ",错误信息=" + result.getDescription()+"]");
        }
	}

}
