package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.founder.service.Tx100403Service;
import com.founder.task.Task100403;
import com.founder.tools.DateUtil;

@RunWith(SpringJUnit4ClassRunner.class)  
//加载spring配置文件  
@ContextConfiguration(locations={"classpath:config/common/spring-servlet.xml","classpath:config/datasource/datasource.xml"}) 
public class TestService100403 {

	@Resource(name="tx100403")
	private Tx100403Service tx100403;

	/**
	 * 取消上传可疑账户
	 */
	@Test
	public void task0000() throws Exception{
		System.out.println("[时间："+DateUtil.getNow()+"，取消上传可疑账户]");
		tx100403.execute(null);
	}
	
	/**
	 * 定时任务，上传频繁开户可疑账户信息
	 */
	@Test
	public void task1001() throws Exception{
		System.out.println("[时间："+DateUtil.getNow()+"，开始上传-频繁开户可疑数据]");
		Map<String,String> param = new HashMap<String,String>();
    	param.put("khbh", "1000000");
		tx100403.execute(null);
	}
	
	/**
	 * 定时任务，上传频繁开户可疑账户信息
	 */
	@Test
	public void task1001_1() throws Exception{
		System.out.println("[时间："+DateUtil.getNow()+"，开始上传-频繁开户可疑数据]");
		//数据库查询参数，查询5个工作日内开卡大于3张卡的所有客户
    	Map<String,String> param = new HashMap<String,String>();
//    	param.put("start", "20160101");
//    	param.put("end", "20160701");
//    	param.put("count", "3");
    	
		List<String> custs = new ArrayList<String>();
		custs.add("1568160");
		custs.add("1167141");
		custs.add("1292064");
		custs.add("1266038");
		custs.add("1269328");
		custs.add("1219490");
		custs.add("1226335");
		
		for (String cust : custs) {
			param.put("khbh", cust);
			List<String> days = tx100403.getF1001grDays(param);
			System.out.println(Arrays.toString(days.toArray()));
			
			if(tx100403.isF1001Cust(days,null,5)){
				tx100403.execute(null);
				Thread.sleep(5000);
			}
		}
	}
	
	/**
	 * 定时任务，上传频繁开户可疑账户信息
	 */
	@Test
	public void task1001_2() throws Exception{
		System.out.println("[时间："+DateUtil.getNow()+"，开始上传-频繁开户可疑数据]");
		//数据库查询参数，查询5个工作日内开卡大于3张卡的所有客户
    	Map<String,String> param = new HashMap<String,String>();
    	param.put("start", "20160701");
    	param.put("end", "20160705");
    	param.put("count", "3");
		List<String> custs = tx100403.getF1001grCusts(param);
		System.out.println("[查出的客户数："+custs.size()+"]");
		
		int suc = 0;
		for (String cust : custs) {
			param.put("khbh", cust);
			List<String> days = tx100403.getF1001grDays(param);
			System.out.println(Arrays.toString(days.toArray()));
			
			if(tx100403.isF1001Cust(days,null,5)){
				Thread.sleep(1000);
				tx100403.execute(null);
				suc++;
			}
		}
		System.out.println("[发送可疑客户数："+suc+"]");
	}
	
	/**
	 * 定时任务，上传累计开户可疑账户信息
	 */
	@Test
	public void task1002() throws Exception{
		System.out.println("[时间："+DateUtil.getNow()+"，开始上传-累计开户可疑数据]");
		//数据库查询参数
    	Map<String,String> param = new HashMap<String,String>();
    	param.put("start", "20160601");
    	param.put("end", "20160729");
    	param.put("count", "10");
		List<String> custs = tx100403.getF1001grCusts(param);
		
		int suc = 0;
		for (String cust : custs) {
			Thread.sleep(1000);
			param.put("khbh", cust);
			tx100403.execute(null);
			suc++;
		}
		System.out.println("[发送可疑客户数："+suc+"]");
	}
	
	
	@Test
	public void test1001_01(){
		Task100403 task = new Task100403();
		try {
			task.task1001("{last:10}");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
