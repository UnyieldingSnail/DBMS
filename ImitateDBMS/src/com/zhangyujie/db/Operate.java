package com.zhangyujie.db;
/**
 * 所有操作都实现此接口
 * @author zhangyujie
 *
 */
public interface Operate {
	public abstract void start() throws Exception;
	public abstract void setAccount(String oper);
}
