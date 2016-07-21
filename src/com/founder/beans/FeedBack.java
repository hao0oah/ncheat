package com.founder.beans;

import java.io.Serializable;

/**
 * 消息返回实体
 * @author Hao
 * @date 2016/07/22
 */
public class FeedBack implements Serializable{
	private static final long serialVersionUID = -7090241055830556028L;
	
	private boolean suc;
	private String msg;
	private Object data;
	
	public boolean isSuc() {
		return suc;
	}
	public void setSuc(boolean suc) {
		this.suc = suc;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		return "FeedBack [suc=" + suc + ", msg=" + msg + ", data=" + data.toString() + "]";
	}
	
}
