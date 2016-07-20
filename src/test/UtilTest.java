package test;

import java.util.UUID;

import org.junit.Test;

import com.founder.beans.Com;

public class UtilTest {

	@Test
	public void TestGetCode(){
		String str = UUID.randomUUID().toString();
		System.out.println(str);
		long st2 = UUID.randomUUID().getLeastSignificantBits();
		System.out.println(st2);
		long st3 = UUID.randomUUID().getMostSignificantBits();
		System.out.println(st3);
	}
	
	@Test
	public void TestGetSerial() throws Exception{
		int len = 0;
		String str = Com.getRandomNo(len);
		System.out.println(str);
		len = 5;
		str = Com.getRandomNo(len);
		System.out.println(str);
		len = 14;
		str = Com.getRandomNo(len);
		System.out.println(str);
		len = 17;
		str = Com.getRandomNo(len);
		System.out.println(str);
		len = 28;
		str = Com.getRandomNo(len);
		System.out.println(str);
		len = 32;
		str = Com.getRandomNo(len);
		System.out.println(str);
		
	}
}
