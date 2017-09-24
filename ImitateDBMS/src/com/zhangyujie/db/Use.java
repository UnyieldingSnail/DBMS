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
	 * 执行use操作!!!
	 * 
	 * @param account
	 * @throws Exception
	 */
	private void use() throws Exception {
		OperUtil.persistence();
		DBMS.loadedTables = new HashMap<String, Table>();

		File databaseDir = new File(DBMS.ROOTPATH, this.databaseName);
		if (!databaseDir.exists()) {
			System.out.println("此数据库不存在，请先创建数据库！");
			return;
		}
		DBMS.currentPath = new File(DBMS.ROOTPATH + File.separator + this.databaseName);

		File file = new File(DBMS.ROOTPATH + File.separator + this.databaseName + ".config");
		DBMS.dataDictionary = null;
		
		if (file.exists()) {// 如果数据库的结构定义文件存在，则读入内存
			DBMS.dataDictionary = (DataDictionary) OperUtil.loadObject(file);
			System.out.println("加载数据库结构定义文件成功！");
		} else {// 如果不存在则创建一个数据库结构定义文件
			file.createNewFile();// 创建数据库结构定义文件
			DBMS.dataDictionary = new DataDictionary(this.databaseName);// 创建数据库结构对象
			System.out.println("创建数据库结构定义文件成功！");
		}

	}

	@Override
	public void setAccount(String oper) {
		this.account = oper;
	}

}
