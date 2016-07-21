package com.founder.beans;

import java.sql.Date;

/**
 * 上报记录（暂时没有用到）
 * @author Hao
 */
public class ReportRecord {
	
	private long serail;		//上报序号
	private String custNo;		//客户编号
	private Date reportTime;	//上报日期时间
	private int flag1001;		//频繁开户标志
	private int flag1002;		//累计开户标志
	private int status;			//上报状态
	
	public long getSerail() {
		return serail;
	}
	public void setSerail(long serail) {
		this.serail = serail;
	}
	public String getCustNo() {
		return custNo;
	}
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
	public Date getReportTime() {
		return reportTime;
	}
	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}
	public int getFlag1001() {
		return flag1001;
	}
	public void setFlag1001(int flag1001) {
		this.flag1001 = flag1001;
	}
	public int getFlag1002() {
		return flag1002;
	}
	public void setFlag1002(int flag1002) {
		this.flag1002 = flag1002;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

}
