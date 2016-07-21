package com.founder.beans;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.time.DateFormatUtils;

public class Com {

	public static final String RESP_SUC = "100000";
	public static final String BANK_CODE = "313134000011";
	public static final String TRUST_CODE = "_000000000000";
	public static final String APP_ID = "03";
	
	/**
	 * 只针对1001、1002
	 * true-DEBUG模式，生成xml文件，手工发送
	 * false-将分析处的数据直接发送到前置机
	 */
	public static boolean DEBUG = true;
	
	public static final String FILE_PATH = "config/common/filepath";
	public static final String CONFIG_PATH = "config/common/config";
	
	public static final Map<String,Object> map = new HashMap<String, Object>();
	
	/**
	 * 3006-3008生成文件，筛选不发送的文件存放目录
	 */
	public static final String IGNORE = "ignore";
	
	
	/**
	 * 法律文书文件类型：01-警官身份证件
	 */
	public static final String FT_01 = "01";
	/**
	 * 法律文书文件类型：02-冻结类法律文书
	 */
	public static final String FT_02 = "02";
	/**
	 * 法律文书文件类型：03-紧急止付申请表
	 */
	public static final String FT_03 = "03";
	/**
	 * 法律文书文件类型：04-身份证
	 */
	public static final String FT_04 = "04";
	/**
	 * 法律文书文件类型：05-查询类法律文书或盖章公文
	 */
	public static final String FT_05 = "05";
	/**
	 * 文件格式：0-jpg
	 */
	public static final String FP_JPG = "0";
	/**
	 * 文件格式：1-pdf
	 */
	public static final String FP_PDF = "1";
	
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
	 * 登陆记录
	 */
	public static final String F5555 = "55555";
	/**
	 * 退出登陆记录
	 */
	public static final String F5556 = "55556";
	/**
	 * 修改密码记录
	 */
	public static final String F5557 = "55557";
	
	/**
	 * 直接上报前置机（定时任务）
	 */
	public static final String T0000 = "0";
	/**
	 * 直接上报前置机（界面操作）
	 */
	public static final String T0001 = "1";
	/**
	 * 只生成报文供筛选和手工发送（定时任务）
	 */
	public static final String T0002 = "2";
	/**
	 * 只生成报文供筛选和手工发送（界面操作）
	 */
	public static final String T0003 = "3";
	/**
	 * 管理员手工批量上报
	 */
	public static final String T0004 = "4";
	/**
	 * 分机构提交记录
	 */
	public static final String T0005 = "5";
	/**
	 * 其他记录
	 */
	public static final String T0010 = "10";
	
	/*{
		map.put(N0401, "案件举报");
		map.put(N0403, "异常开卡");
		map.put(N0405, "异常事件");
		map.put(F1001, "频繁开户");
		map.put(F1002, "累计开户");
		map.put(F2001, "涉案账户");
		map.put(F3001, "诈骗未遂");
		map.put(F3002, "受害人举报疑似诈骗");
		map.put(F3003, "银行举报疑似诈骗");
		map.put(F3006, "分散入集中出");
		map.put(F3007, "集中入分散出");
		map.put(F3008, "连续多笔交易");
		map.put(F5555, "柜员登陆");
		map.put(F5556, "退出登陆");
		map.put(F5557, "修改密码");
		map.put(T0000, "定时上报");
		map.put(T0001, "界面上报");
		map.put(T0002, "定时生成报文");
		map.put(T0003, "界面生成报文");
		map.put(T0004, "管理员批量上报");
		map.put(T0005, "柜员提交筛选");
		map.put(T0010, "其他操作");
	}*/

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
