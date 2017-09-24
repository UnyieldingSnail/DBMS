package com.zhangyujie.main;

/**
 * ��2.4�汾��ͶӰ�½���Ҷ�ڵ�
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
 * ���ݿ�����
 * 
 * @author zhangyujie
 *
 */
public class DBMS {
	public static final File ROOTPATH = new File("." + File.separator + "database");// ��Ŀ¼
	public static File currentPath;// ��ǰĿ¼
	public static final File DBS = new File("." + File.separator + "database.config");
	public static List<String> dbNames = null;// ���ݿ�������
	public static DataDictionary dataDictionary = null;// ���ݿ��ֵ�
	public static Map<String, Table> loadedTables;// �Ѽ��صĹ�ϵ��
	public static Map<String, Field> indexEntry;// ��������

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
	 * ����ָ��
	 * 
	 * @throws Exception
	 */
	public void start() throws Exception {
		Scanner scan = new Scanner(System.in);//ʵ����Scanner�࣬��������
		String account = null;//�ַ����������������������
		while (true) {
			account = OperUtil.input(scan);// �ӿ���̨����һ������
			try {
				new Handler(account).start();// ������ת
			}  catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

}
