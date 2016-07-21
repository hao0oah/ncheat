package com.founder.beans;

/**
 * 用于筛选界面显示的条目
 * @author Hao
 * @date 2016/08/15
 */
public class Tx405Item {
	private long id;				//ID
	private int bankid;				//开户行号
	private String accountName;		//账户名
	private String cardNumber;		//卡号
	private String accountNumber;	//内部账号
	private String accountSerial;	//账户序号
	private String accountType;		//账户类型
	private String accountStatus;	//账户状态
	private int payCount;			//支出总笔数
	private double payAmount;		//支出总金额
	private int peyCount;			//收入总笔数
	private double peyAmount;		//收入总金额
	private String exeDate;			//执行日期
	private String feature;			//特征码
	private String filepath;		//文件路径
	private int status;				//状态：0-上传；1-不上传
	
	public int getBankid() {
		return bankid;
	}
	public void setBankid(int bankid) {
		this.bankid = bankid;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getAccountSerial() {
		return accountSerial;
	}
	public void setAccountSerial(String accountSerial) {
		this.accountSerial = accountSerial;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public int getPayCount() {
		return payCount;
	}
	public void setPayCount(int payCount) {
		this.payCount = payCount;
	}
	public double getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(double payAmount) {
		this.payAmount = payAmount;
	}
	public int getPeyCount() {
		return peyCount;
	}
	public void setPeyCount(int peyCount) {
		this.peyCount = peyCount;
	}
	public double getPeyAmount() {
		return peyAmount;
	}
	public void setPeyAmount(double peyAmount) {
		this.peyAmount = peyAmount;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getExeDate() {
		return exeDate;
	}
	public void setExeDate(String exeDate) {
		this.exeDate = exeDate;
	}
	public String getFeature() {
		return feature;
	}
	public void setFeature(String feature) {
		this.feature = feature;
	}
	
}
