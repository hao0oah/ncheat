package com.founder.database3;

import java.util.List;
import java.util.Map;


public interface Dao3 {

	/**
	 * 分页获取
	 */
	public List<Map<String,Object>> listDataByPage(Map<String,?> param);
	
	/**
	 * 插入数据库
	 */
	public void add(Map<String,?> param);
	
}
