package com.zhangyujie.db;
/**
 * ���в�����ʵ�ִ˽ӿ�
 * @author zhangyujie
 *
 */
public interface Operate {
	public abstract void start() throws Exception;
	public abstract void setAccount(String oper);
}
