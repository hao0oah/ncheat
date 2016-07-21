package com.founder.tools;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class XMLUtil {

	@SuppressWarnings("unchecked")
	public static String readFromDoc(String xmlString,String ptn){
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xmlString);
			Element root = doc.getRootElement();
			for (Element e : (List<Element>) root.elements()) {
				if (ptn.equals(e.getName())) {
					return e.getTextTrim();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 格式化XML字符串，使输出格式更易于查看和调试
	 */
	public static String formatXml(String xmlStr) {
		OutputFormat format = OutputFormat.createPrettyPrint();
		StringWriter out = new StringWriter();

		try{
			Document doc = DocumentHelper.parseText(xmlStr);
			format.setEncoding("UTF-8");
			format.setSuppressDeclaration(true);// 去掉头文件也就是版本信息
			XMLWriter writer = new XMLWriter(out, format);
			writer.write(doc);
			writer.flush();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return out.toString().trim();
	}
	
	public static Map<?, ?> jsonToMap(String xml){
		
		return null;
	}

}
