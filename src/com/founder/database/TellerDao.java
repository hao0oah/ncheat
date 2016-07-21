package com.founder.database;

import java.util.List;
import java.util.Map;

import com.founder.beans.Menu;
import com.founder.beans.Teller;

/**
 * 柜员相关数据库操作
 * @author Hao
 * @date 2016/09/04
 */
public interface TellerDao {
	
	public void addTeller(Teller teller);
	
	public void updateTeller(Teller teller);
	
	public Teller getTellerById(int userid);
	
	public List<Teller> getAllTeller();
	
	public List<Menu> getMenus(int userid);
	
	public void addOptRecord(Map<String,Object> map);
	
	public Integer existRecord(Map<String,Object> map);

	public List<Map<String,Object>> queryRecordByPage(Map<String, Object> map);
	
	public Integer queryRecordCount(Map<String, Object> map);
}
