package com.zhangyujie.main;

/**
 * 较2.4版本，投影下降到叶节点
 */
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.zhangyujie.db.Table;
import com.zhangyujie.entity.DataDictionary;
import com.zhangyujie.entity.Field;
import com.zhangyujie.util.OperUtil;
import com.zhangyujie.factory.Handler;

/**
 * 数据库主类
 * 
 * @author zhangyujie
 *
 */
public class DBMS {
	public static final File ROOTPATH = new File("." + File.separator + "database");// 根目录
	public static File currentPath;// 当前目录
	public static final File DBS = new File("." + File.separator + "database.config");
	public static List<String> dbNames = null;// 数据库名集合
	public static DataDictionary dataDictionary = null;// 数据库字典
	public static Map<String, Table> loadedTables;// 已加载的关系表
	public static Map<String, Field> indexEntry;// 索引集合

	@SuppressWarnings("unchecked")
	public DBMS() {
		if (DBMS.DBS.exists()) {
			DBMS.dbNames = (List<String>) OperUtil.loadObject(DBMS.DBS);
		} else {
			DBMS.dbNames = new ArrayList<String>();
		}
		DBMS.loadedTables = new HashMap<String, Table>();
		DBMS.indexEntry = new HashMap<String, Field>();
		DBMS.currentPath = ROOTPATH;
	}

	/**
	 * 输入指令
	 * 
	 * @throws Exception
	 */
	public void start() throws Exception {
		Scanner scan = new Scanner(System.in);//实例化Scanner类，进行输入
		String account = null;//字符串用来保存你输入的命令
		while (true) {
			account = OperUtil.input(scan);// 从控制台接收一条命令
			try {
				new Handler(account).start();// 工厂运转
			}  catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

}
