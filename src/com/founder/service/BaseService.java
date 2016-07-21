package com.founder.service;

import java.util.Map;

import com.founder.beans.Com;

import cfca.safeguard.api.bank.ClientEnvironment;
import cfca.safeguard.api.bank.SGBusiness;

/**
 * 服务父类（已经弃用）
 */
public abstract class BaseService {
	protected SGBusiness sgBusiness;
	
	public BaseService() throws Exception {
		ClientEnvironment.initTxClientEnvironment(Com.CONFIG_PATH);
		sgBusiness = new SGBusiness();
	}
	
	/**
	 * 根据事件类型特征编号，进行相应业务处理
	 * @param featrue 事件类型特征编号
	 */
	public abstract void execute(String featrue,Map<String,?> param) throws Exception;

}
