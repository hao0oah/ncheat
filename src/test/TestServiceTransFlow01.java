package test;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.founder.service.TransFlow01Service;

@RunWith(SpringJUnit4ClassRunner.class)  
//加载spring配置文件  
@ContextConfiguration(locations={"classpath:config/common/spring-servlet.xml","classpath:config/datasource/datasource.xml"}) 
public class TestServiceTransFlow01 {

	@Resource(name="transflowService")
	private TransFlow01Service service;

	@Test
	public void test01(){
		JSONObject jbj = JSONObject.fromObject("{type:0,account:'6210910002001084569'}");
		int type = jbj.optInt("type");
		String account = jbj.optString("account").trim();
		String start = jbj.optString("start");
		String end = jbj.optString("end");
		
		Map<String,Object> maps = new HashMap<String, Object>();
		maps.put("type", type);
		maps.put("khzh", account);
		maps.put("start", start);
		maps.put("end", end);
		
		String filepath = null;
		try{
			if(type==0){		//个人
				filepath = service.reportgrTrans01(maps);
			} else if(type==1){	//对公
				filepath = service.reportdgTrans01(maps);
			}
			System.out.println("filepath="+filepath);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
		
}
