package com.founder.service;

import cfca.safeguard.api.bank.ClientEnvironment;
import cfca.safeguard.api.bank.SGBusiness;

public abstract class BaseService {
	protected SGBusiness sgBusiness;
	
	public BaseService() throws Exception {
		ClientEnvironment.initTxClientEnvironment("config/common/config");
		sgBusiness = new SGBusiness();
	}
	
	/**
	 * 根据事件类型特征编号，进行相应业务处理
	 * @param featrue 事件类型特征编号
	 */
	public abstract void execute(String featrue) throws Exception;

}
