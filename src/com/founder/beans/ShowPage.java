package com.founder.beans;

import java.util.List;

/**
 * 分页查询JavaBean
 * @author Hao
 * @param <E>
 */
public class ShowPage<E> {
	private int begin;			//开始页码
	private int limit;			//每页记录条数
	private int count;			//所有记录数，用来计算总页数
	private int totalPage;		//总页数
	private int currentPage;	//当前页码
	private List<E> beanList;	//当前页数据
	private E  bean;			//其他查询条件
	
	public int getBegin() {
		return begin;
	}
	public void setBegin(int begin) {
		this.begin = begin;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.totalPage = (int)Math.ceil(count*1.0/limit);
		this.currentPage = (int)begin/limit + 1;
		this.count = count;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getCurrentPage() {
		
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public List<E> getBeanList() {
		return beanList;
	}
	public void setBeanList(List<E> beanList) {
		this.beanList = beanList;
	}
	public E getBean() {
		return bean;
	}
	public void setBean(E bean) {
		this.bean = bean;
	}
	
}
