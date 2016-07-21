package com.founder.tools;

import java.util.Locale;
import java.util.ResourceBundle;

public class PropetyUtil {
	private ResourceBundle bundle;

	public PropetyUtil(String fileName) {
		this.bundle = ResourceBundle.getBundle(fileName, Locale.getDefault()); // 从xxx.properties中读取配置信息
	}

	public String getValue(String key) {
		return this.bundle.getString(key);
	}
}
