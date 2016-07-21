package com.founder.database;

import java.util.Map;

import com.founder.beans.ReportRecord;

public interface RecordDao {

	public void insert(ReportRecord record);
	
	public ReportRecord selectById(long id);
	
	public ReportRecord selectByCust(Map<String,?> param);
}
