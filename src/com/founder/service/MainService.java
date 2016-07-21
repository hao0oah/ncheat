package com.founder.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.founder.beans.Menu;
import com.founder.beans.Teller;
import com.founder.database.TellerDao;

@Service("mainService")
public class MainService {
	public final Logger log = Logger.getLogger(this.getClass());

	@Resource
	private TellerDao tellerDao;

	/**
	 * 根据ID获取柜员
	 */
	public Teller getTeller(int userid){
		return tellerDao.getTellerById(userid);
	}
	
	/**
	 * 更新柜员
	 */
	public void update(Teller teller){
		tellerDao.updateTeller(teller);
	}
	
	/**
	 * 获取所有柜员
	 */
	public List<Teller> getAllTller(){
		return tellerDao.getAllTeller();
	}
	
	/**
	 * 柜员获取显示菜单
	 */
	public List<Menu> getOrderMenu(int userid){
		return tellerDao.getMenus(userid);
	}
	
	/**
	 * 记录操作日志
	 */
	public void recordOpt(Map<String,Object> map){
		tellerDao.addOptRecord(map);
	}
	
	/**
	 * 查询操作日志
	 */
	public List<Map<String,Object>> getOptRecord(Map<String,Object> map){
		return tellerDao.queryRecordByPage(map);
	}
	
	/**
	 * 查询操作日志总数
	 */
	public Integer getOptRecordCount(Map<String,Object> map){
		return tellerDao.queryRecordCount(map);
	}
	
	/**
	 * 是否存在记录
	 */
	public boolean isExist(Map<String,Object> map){
		Integer count = tellerDao.existRecord(map);
		return (count!=null&&count>0);
	}
	
}
