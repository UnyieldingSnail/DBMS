package com.zhangyujie.main;


public class Main {
	/**
	 * 启动程序
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new DBMS().start();
		} catch (Exception e) {// 用来捕获input抛出的异常,即EXIT操作。
			// e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
}
