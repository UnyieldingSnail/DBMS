package com.zhangyujie.factory;

import java.io.FileInputStream;
import java.util.Properties;

import com.zhangyujie.db.Operate;

/**
 * ����
 * @author zhangyujie
 *
 */
public class Handler {
	private Operate operate = null;//��������
	
	public Handler(String account) {
		String[] operationArray = account.split("\\s");//�����ַ��ָ��������

		String oper = operationArray[0].toUpperCase();
		try {
			Properties prop = new Properties();
			prop.load(new FileInputStream("operation.properties"));//����Ӱ���ļ�
			this.operate = (Operate) Class.forName(prop.getProperty(oper)).newInstance();//���÷�����ƴ���������
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
				System.out.println("���Ƿ���");
			}
		} catch (Exception e) {
//			e.printStackTrace();
			//System.out.println("[Handler:44]" + e.getMessage());
		}
	}
}
