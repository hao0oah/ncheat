package com.founder.beans;

/**
 * 机构柜员
 * @author Hao
 * @date 2016/09/01
 */
public class Teller {

	private int userid;			//用户登陆名/ID（机构号）
	private String username;	//用户名（机构名）
	private String password;	//密码（MD5加密）
	private String name;		//姓名
	private String telephone;	//电话
	private String email;		//邮箱
	private int roleid;			//角色ID
	private int status;			//状态：0-正常；1-注销/弃用

	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getRoleid() {
		return roleid;
	}
	public void setRoleid(int role) {
		this.roleid = role;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Teller [userid=" + userid + ", username=" + username
				+ ", name=" + name + ", roleid=" + roleid + ", status=" + status + "]";
	}
	
}
