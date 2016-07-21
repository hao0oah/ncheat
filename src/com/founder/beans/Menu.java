package com.founder.beans;

/**
 * 显示菜单
 * @author Hao
 * @date 2016/09/01
 */
public class Menu {
	private int menuid;		//菜单ID
	private String title;	//菜单标题
	private String url;		//菜单的链接地址
	private int status;		//状态：0-使用中；1-未使用
	
	public int getMenuid() {
		return menuid;
	}
	public void setMenuid(int menuid) {
		this.menuid = menuid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
