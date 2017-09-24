package com.zhangyujie.factory;

import java.io.FileInputStream;
import java.util.Properties;

import com.zhangyujie.db.Operate;

/**
 * 工厂
 * @author zhangyujie
 *
 */
public class Handler {
	private Operate operate = null;//操作类型
	
	public Handler(String account) {
		String[] operationArray = account.split("\\s");//按空字符分割输入语句

		String oper = operationArray[0].toUpperCase();
		try {
			Properties prop = new Properties();
			prop.load(new FileInputStream("operation.properties"));//加载影射文件
			this.operate = (Operate) Class.forName(prop.getProperty(oper)).newInstance();//利用反射机制创建操作类
			if (this.operate != null) {
				this.operate.setAccount(account);
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
	
	public void start() {
		try {
			if (this.operate != null) {
				this.operate.start();
			}
			if (this.operate == null) {
				System.out.println("语句非法！");
			}
		} catch (Exception e) {
//			e.printStackTrace();
			//System.out.println("[Handler:44]" + e.getMessage());
		}
	}
}
