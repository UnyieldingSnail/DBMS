package com.zhangyujie.main;


public class Main {
	/**
	 * ��������
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new DBMS().start();
		} catch (Exception e) {// ��������input�׳����쳣,��EXIT������
			// e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
}
