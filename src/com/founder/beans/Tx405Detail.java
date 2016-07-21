package com.founder.beans;

/**
 * 筛选条目详细
 * @author Hao
 * @date 2016/08/15
 */
public class Tx405Detail {
	private long id;			//ID
	private long itemId;		//ItemID
	private String transFlow;	//交易流水
	private String transTime;	//交易时间
	private String transCount;	//交易序号
	private String trsCode;		//交易码
	private String acctNo;		//账号
	private String acctName;	//名称
	private String cardNo;		//卡号
	private String vchType;		//凭证类型
	private String vchCode;		//凭证号
	private String borrowSign;	//借贷方
	private String trsType;		//交易类型
	private String trsAmount;	//交易金额
	private String trsBalance;	//交易余额
	private String opBankNo;	//对方行号
	private String opBankName;	//对方行名
	private String opAcctNo;	//对方账号
	private String opAcctName;	//对方名称
	
	public String getBorrowSign() {
		return borrowSign;
	}
	public void setBorrowSign(String borrowSign) {
		this.borrowSign = borrowSign;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getItemId() {
		return itemId;
	}
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	public String getTransFlow() {
		return transFlow;
	}
	public void setTransFlow(String transFlow) {
		this.transFlow = transFlow;
	}
	public String getTransTime() {
		return transTime;
	}
	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}
	public String getTransCount() {
		return transCount;
	}
	public void setTransCount(String transCount) {
		this.transCount = transCount;
	}
	public String getTrsCode() {
		return trsCode;
	}
	public void setTrsCode(String trsCode) {
		this.trsCode = trsCode;
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
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getVchType() {
		return vchType;
	}
	public void setVchType(String vchType) {
		this.vchType = vchType;
	}
	public String getVchCode() {
		return vchCode;
	}
	public void setVchCode(String vchCode) {
		this.vchCode = vchCode;
	}
	public String getTrsType() {
		return trsType;
	}
	public void setTrsType(String trsType) {
		this.trsType = trsType;
	}
	public String getTrsAmount() {
		return trsAmount;
	}
	public void setTrsAmount(String trsAmount) {
		this.trsAmount = trsAmount;
	}
	public String getTrsBalance() {
		return trsBalance;
	}
	public void setTrsBalance(String trsBalance) {
		this.trsBalance = trsBalance;
	}
	public String getOpBankNo() {
		return opBankNo;
	}
	public void setOpBankNo(String opBankNo) {
		this.opBankNo = opBankNo;
	}
	public String getOpBankName() {
		return opBankName;
	}
	public void setOpBankName(String opBankName) {
		this.opBankName = opBankName;
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

}
