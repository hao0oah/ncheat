package com.founder.beans;

import java.util.Date;
import java.util.Random;

import org.apache.commons.lang.time.DateFormatUtils;

public class Com {

	public static final String RESP_SUC = "100000";
	public static final String BANK_CODE = "313134000011";
	public static final String TRUST_CODE = "000000000000";
	
	//业务申请编号编码
	/**
	 * 止付
	 */
	public static final String N0101 = "0101";
	/**
	 * 止付解除
	 */
	public static final String N0103 = "0103";
	/**
	 * 止付延期
	 */
	public static final String N0105 = "0105";
	/**
	 * 冻结
	 */
	public static final String N0201 = "0201";
	/**
	 * 冻结解除
	 */
	public static final String N0203 = "0203";
	/**
	 * 冻结延期
	 */
	public static final String N0205 = "0205";
	/**
	 * 查询账户交易明细
	 */
	public static final String N0301 = "0301";
	/**
	 * 查询账户持卡主体
	 */
	public static final String N0303 = "0303";
	/**
	 * 动态查询账户
	 */
	public static final String N0305 = "0305";
	/**
	 * 动态查询账户解除
	 */
	public static final String N0307 = "0307";
	/**
	 * 查询客户全账户
	 */
	public static final String N0309 = "0309";
	/**
	 * 案件举报
	 */
	public static final String N0401 = "0401";
	/**
	 * 案件举报反馈
	 */
	public static final String N0402 = "0402";
	/**
	 * 可疑名单上报-异常开卡
	 */
	public static final String N0403 = "0403";
	/**
	 * 可疑名单上报-涉案账户
	 */
	public static final String N0404 = "0404";
	/**
	 * 可疑名单上报-异常事件
	 */
	public static final String N0405 = "0405";
	/**
	 * 涉案信息推送
	 */
	public static final String N0501 = "0501";
	
	//事件特征码
	/**
	 * 取消上报
	 */
	public static final String F0000 = "0000";
	/**
	 * 频繁开户
	 */
	public static final String F1001 = "1001";
	/**
	 * 累计开户
	 */
	public static final String F1002 = "1002";
	/**
	 * 涉案账户
	 */
	public static final String F2001 = "2001";
	/**
	 * 诈骗未遂
	 */
	public static final String F3001 = "3001";
	/**
	 * 受害人举报疑似诈骗
	 */
	public static final String F3002 = "3002";
	/**
	 * 银行举报疑似诈骗
	 */
	public static final String F3003 = "3003";
	/**
	 * 收方涉案
	 */
	public static final String F3004 = "3004";
	/**
	 * 付方涉案
	 */
	public static final String F3005 = "3005";
	/**
	 * 分散入集中出
	 */
	public static final String F3006 = "3006";
	/**
	 * 集中入分散出
	 */
	public static final String F3007 = "3007";
	/**
	 * 连续多笔交易
	 */
	public static final String F3008 = "3008";
	/**
	 * 其余可疑交易
	 */
	public static final String F9999 = "9999";
	
	/**
	 * 获取len位的随机数字
	 */
	public static String getRandomNo(int len){
		String str = "";
		
		if(len <= 0){
			return str;
		} else if (len>=14 && len<17){
			str = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
			len -= 14;
		} else if(len>=17){
			str = DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS");
			len -= 17;
		}
		Random rand = new Random();
		for(int i=0;i<len;i++){
			str += rand.nextInt(10);
		}
		return str;
	}
	
}
