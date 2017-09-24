package com.zhangyujie.db;

import java.io.File;
import java.util.HashMap;

import com.zhangyujie.entity.DataDictionary;
import com.zhangyujie.main.DBMS;
import com.zhangyujie.util.OperUtil;

public class Use implements Operate {
	private String account = null;
	private String databaseName = null;

	public Use() {}

	@Override
	public void start() throws Exception {
		this.databaseName = ParseAccount.parseUse(this.account);
		this.use();

	}

	/**
	 * ִ��use����!!!
	 * 
	 * @param account
	 * @throws Exception
	 */
	private void use() throws Exception {
		OperUtil.persistence();
		DBMS.loadedTables = new HashMap<String, Table>();

		File databaseDir = new File(DBMS.ROOTPATH, this.databaseName);
		if (!databaseDir.exists()) {
			System.out.println("�����ݿⲻ���ڣ����ȴ������ݿ⣡");
			return;
		}
		DBMS.currentPath = new File(DBMS.ROOTPATH + File.separator + this.databaseName);

		File file = new File(DBMS.ROOTPATH + File.separator + this.databaseName + ".config");
		DBMS.dataDictionary = null;
		
		if (file.exists()) {// ������ݿ�Ľṹ�����ļ����ڣ�������ڴ�
			DBMS.dataDictionary = (DataDictionary) OperUtil.loadObject(file);
			System.out.println("�������ݿ�ṹ�����ļ��ɹ���");
		} else {// ����������򴴽�һ�����ݿ�ṹ�����ļ�
			file.createNewFile();// �������ݿ�ṹ�����ļ�
			DBMS.dataDictionary = new DataDictionary(this.databaseName);// �������ݿ�ṹ����
			System.out.println("�������ݿ�ṹ�����ļ��ɹ���");
		}

	}

	@Override
	public void setAccount(String oper) {
		this.account = oper;
	}

}
