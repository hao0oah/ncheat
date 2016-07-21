package com.founder.beans;

public class TransFlow01 {

	private String acctNo;		//账号
	private String acctName;	//户名
	private String transDate;	//交易日期
	private int acctType;		//借贷方
	private String amount;		//发生额
	private String balance;		//余额
	private String remark;		//摘要
	private String opAcctNo;	//对手账号
	private String opAcctName;	//对手名称
	private String opBankNo;	//对手开户行号
	private String opBankName;	//对手开户行名
	
	public int getAcctType() {
		return acctType;
	}
	public void setAcctType(int acctType) {
		this.acctType = acctType;
	}
	public String getAcctNo() {
		return acctNo;
	}
	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}
	public String getAcctName() {
		return acctName;
	}
	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}
	public String getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOpAcctNo() {
		return opAcctNo;
	}
	public void setOpAcctNo(String opAcctNo) {
		this.opAcctNo = opAcctNo;
	}
	public String getOpAcctName() {
		return opAcctName;
	}
	public void setOpAcctName(String opAcctName) {
		this.opAcctName = opAcctName;
	}
	public String getOpBankName() {
		return opBankName;
	}
	public void setOpBankName(String opBankName) {
		this.opBankName = opBankName;
	}
	public String getOpBankNo() {
		return opBankNo;
	}
	public void setOpBankNo(String opBankNo) {
		this.opBankNo = opBankNo;
	}
	
}
