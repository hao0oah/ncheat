package test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.founder.database3.Dao3;
import com.founder.tools.DateUtil;

@RunWith(SpringJUnit4ClassRunner.class)  
//加载spring配置文件  
@ContextConfiguration(locations={"classpath:config/common/spring-servlet.xml","classpath:config/common/spring-quartz.xml","classpath:config/datasource/datasource.xml"}) 
public class TestDao3 {

	@Resource
	private Dao3 dao;
	
	/**
	 * 生成中间表数据
	 */
	@Test
	public void createData(){
		
		String branch = "";
		String doc_type = "";

		long begin_no = 0;
		long temp_no = 0;
		long last_no = 0;
		long end_no = 0;
		
		int begin = 0;
		int limit = 1000;
		
		System.out.println("开始时间["+DateUtil.getNowTime()+"]");
		
		
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("limit", limit);
		
		while(true){
			long time1 = System.currentTimeMillis();
			param.put("begin", begin);
			
			List<Map<String,Object>> lists = dao.listDataByPage(param);
			
			for (int i = 0; i < lists.size(); i++) {
				Map<String,Object> tmp = lists.get(i);
				temp_no = Long.parseLong((String) tmp.get("begin_no"));
				
				//如果是第一次取到tmp_no，给begin赋值，给last赋值
				if(begin_no == 0){
					begin_no = temp_no;
					last_no = temp_no;
				}else{
					//如果是连续的，即相差为1，begin_no不变，将当前temp_no赋值给last_no，向下取下一个
					if(temp_no-last_no == 1){
						last_no = temp_no;
						continue;
					}
					//如果不连续，即相差大于1，将last_no赋值给end_no赋值，并插入数据库。
					else if(Math.abs(temp_no-last_no) > 1){
						end_no = last_no;
						
						branch = (String)tmp.get("branch");
						doc_type = (String)tmp.get("doc_type");
						
						//插入数据库
						Map<String,Object> pppp = new HashMap<String, Object>();
						pppp.put("begin_no", begin_no);
						pppp.put("end_no", end_no);
						pppp.put("branch", branch);
						pppp.put("doc_type", doc_type);
						dao.add(pppp);
						
						//相当于重新开始，将当前值temp_no赋值给begin_no和last_no
						begin_no = temp_no;
						last_no = temp_no;
					}
				}
			}
			
			//如果取到的个数小于limit，说明已到最后，结束循环
			if(lists.size()<limit) break;
			begin += limit;
			
			long time2 = System.currentTimeMillis();
			System.out.println("已执行完["+begin+"]条，用时["+(time2-time1)/1000.0+"]秒......");
		}
		System.out.println("["+DateUtil.getNowTime()+"]执行完成!");
	}
}
