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
	 * ��ʾ�������ݿ�!!!
	 */
	private void showDatabase() {
		System.out.println(DBMS.dbNames);
	}
	/**
	 * ��ʾ��ǰ���ݿ��µ����б�!!!
	 */
	private void showTables() {
		System.out.println(DBMS.dataDictionary.getTables());
	}

	@Override
	public void setAccount(String oper) {
		this.account = oper;
	}
	
}
