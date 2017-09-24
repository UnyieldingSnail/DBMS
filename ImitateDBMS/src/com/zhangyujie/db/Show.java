package com.zhangyujie.db;

import com.zhangyujie.main.DBMS;

public class Show implements Operate{
	private String account = null;

	public Show() {
		super();
	}

	public Show(String account) {
		this.account = account;
	}
	
	@Override
	public void start() throws Exception {
		switch(ParseAccount.parseShow(this.account)) {
			case 1:
				showDatabase();
				break;
			case 2:
				Check.hadUseDatabase();
				showTables();
				break;
			default:
				System.out.println("Error");
				break;
		}
	}
	/**
	 * 显示所有数据库!!!
	 */
	private void showDatabase() {
		System.out.println(DBMS.dbNames);
	}
	/**
	 * 显示当前数据库下的所有表!!!
	 */
	private void showTables() {
		System.out.println(DBMS.dataDictionary.getTables());
	}

	@Override
	public void setAccount(String oper) {
		this.account = oper;
	}
	
}
