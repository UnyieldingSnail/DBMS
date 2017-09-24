package com.zhangyujie.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import com.zhangyujie.db.BTree;
import com.zhangyujie.db.Table;
import com.zhangyujie.db.Tree;
import com.zhangyujie.entity.ConditionalExpression;
import com.zhangyujie.entity.DataEntry;
import com.zhangyujie.entity.IndexLeafNode;
import com.zhangyujie.entity.IndexNode;
import com.zhangyujie.entity.Node;
import com.zhangyujie.main.DBMS;

public class OperUtil {
	/**
	 * �Ӽ��̽����������
	 * 
	 * @param scan
	 */
	public static String input(Scanner scan) throws Exception {
		System.out.print(DBMS.currentPath.getName() + ">>");
		String account = scan.nextLine();
		while (!account.endsWith(";")) {// ����";"��β,���������
			System.out.print(DBMS.currentPath.getName() + ">>");
			account = account + " " + scan.nextLine();// next��nextLine������
		}
		if ("exit;".equalsIgnoreCase(account)) {// �˳����ݿ�ʱ�־û������ֵ�
			OperUtil.persistence();// �־û������ֵ�
			scan.close();// �رտ���̨������
			throw new RuntimeException("�˳����ݿ�ɹ���");
		}
		return account;
	}

	/**
	 * select���ִ�н������ͷ���Դ��ɾ���м��ļ�!!!
	 * @param rootNodes 
	 */
	public static void garbageClear(Collection<Node> rootNodes) {
		for (Node node : rootNodes) {
			node.getFile().delete();
		}
		Tree.leaves = null;
		Tree.inners = null;
	}

	/**
	 * �־û���ϵ��ṹ�����ļ������ݿ�ṹ�����ļ�!!!
	 */
	public static void persistence() {
		ObjectOutputStream oos = null;
		try {
			/*
			 * �־û����ݿ�ṹ�������
			 */
			if (DBMS.dataDictionary != null) {
				oos = new ObjectOutputStream(new FileOutputStream(DBMS.dataDictionary.getConfigFile()));
				oos.writeObject(DBMS.dataDictionary);
			}
			/*
			 * �־û���ϵ��ṹ�������
			 */
			if (DBMS.loadedTables != null) {
				for (Table table : DBMS.loadedTables.values()) {
					table.getConfigFile().delete();
					oos = new ObjectOutputStream(new FileOutputStream(table.getConfigFile()));
					oos.writeObject(table);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("��ϵ��־û�ʧ�ܣ�");
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * ���ع�ϵ��!!!
	 */
	public static Table loadTable(String tableName) throws Exception {
		File file = new File(DBMS.currentPath + File.separator + tableName + ".config");
		
		if (!file.exists()) {
			throw new RuntimeException("���Ƿ���û��" + tableName + "�����");
		}
		if (DBMS.loadedTables.containsKey(tableName)) {//����Ѽ��ع�ϵ�����к��д˹�ϵ���򷵻ش˹�ϵ��
			return DBMS.loadedTables.get(tableName);
		}
		
		Table table = (Table) OperUtil.loadObject(file);
		
		if (table != null) {
			DBMS.loadedTables.put(tableName, table);// �����صĹ�ϵ��������Ѽ��ع�ϵ��map
			return table;
		} else {
			throw new RuntimeException("��ȡ��ϵ��������Ϣ����");
		}
	}

	/**
	 * ���ļ��ж�ȡ����
	 * @param file
	 * @return
	 */
	public static Object loadObject(File file) {
		ObjectInputStream ois = null;
		Object obj = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(file));
			obj = ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return obj;
	}
	
	/**
	 * ��ȡָ���ֶ���������
	 * 
	 * @param file
	 * @param column
	 * @return
	 */
	public static List<DataEntry> getIndex(File file, int column, String type) {
		List<DataEntry> indexList = new ArrayList<DataEntry>();//�����������ݼ���
		DataEntry de = null;

		BufferedReader br = null;
		String line = null;
		int index = 0;// ��һ�д�0��ʼ
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));
			long key;
			if ("Integer".equalsIgnoreCase(type)) {//���������������ֵ����ֵ����
				while ((line = br.readLine()) != null) {
					key = Long.parseLong(line.split(",")[column]);//���ַ���ת��Ϊ��ֵ��Ϊ������key
					de = new DataEntry(key, new HashSet<Integer>());
					de.getIndex().add(index++);
					indexList.add(de);
				}
			} else if ("Varchar".equalsIgnoreCase(type)) {//���������������ֵ���ַ�������
				while ((line = br.readLine()) != null) {
					key = line.split(",")[column].hashCode();//���ַ����Ĺ�ϣֵ��Ϊ������key
					de = new DataEntry(key, new HashSet<Integer>());
					de.getIndex().add(index++);
					indexList.add(de);
					// indexMap.put(line.split(",")[column],
					// index++);//����Ӧ���Ե��ֶ���Ϊkey��������Ϊvalue��ע�⣺�д�0��ʼ��
				}
			} else {
				throw new RuntimeException("�޴����ͣ�");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return indexList;
	}
	/**
	 * ��ȡѡ������������ֵ����
	 * @param c
	 * @return
	 * 		null ��ʾ��ѡ�������漰�����Բ���������
	 * 		Set<Integer> ����ֵ����
	 */
	public static Set<Integer> getIndexOfBT(ConditionalExpression c) {

		Set<Integer> indexs = null;
		String cons = c.isLeftConstant() ? c.getLeft() : c.getRight();//��ȡ���Ƚϳ���
		String type = c.isLeftConstant() ? c.getRightType() : c.getLeftType();
		long constant = 0;
		if ("Integer".equalsIgnoreCase(type)) {
			constant = Integer.parseInt(cons);
		} else if ("Varchar".equalsIgnoreCase(type)) {
			constant = cons.hashCode();
		} else {
			throw new RuntimeException("�޴����ͣ�");
		}
		IndexNode root = c.isLeftIsIndex() ? c.getLeftRoot() : c.getRightRoot();//��ȡ�������ĸ��ڵ�
		IndexLeafNode now = BTree.find(root, constant);//����Ҫ�Ƚϵĳ������ڵ�Ҷ�ڵ�
		
		if ("<".equals(c.getOper()) && c.isLeftIsIndex() || ">".equals(c.getOper()) && c.isRightIsIndex()) {
			indexs = new HashSet<Integer>();
			IndexLeafNode minNode = BTree.find(c.getLeftRoot(), Long.MIN_VALUE);//������С�ؼ������ڽڵ�
			while (minNode != null && !minNode.equals(now)) {//��ȡ��ǰҶ�����������ݣ�����¼�����У�
				for (DataEntry de : minNode.getData()) {//������ǰҶ����µ����ݼ���
					indexs.addAll(de.getIndex());
				}
				minNode = minNode.getRightNode();
			}
			for (DataEntry de : now.getData()) {//������ǰҶ����µ����ݼ���
				if (de.getKey() >= constant) {//��ǰ�ؼ��ִ��ڵ��ڴ��Ƚϳ����������ֱ������ѭ��
					break;
				}
				indexs.addAll(de.getIndex());
			}
		} else if ("<=".equals(c.getOper()) && c.isLeftIsIndex() || ">=".equals(c.getOper()) && c.isRightIsIndex()) {
			indexs = new HashSet<Integer>();
			IndexLeafNode minNode = BTree.find(c.getLeftRoot(), Long.MIN_VALUE);//������С�ؼ������ڽڵ�
			while (minNode != null && !minNode.equals(now)) {//��ȡ��ǰҶ�����������ݣ�����¼�����У�
				for (DataEntry de : minNode.getData()) {//������ǰҶ����µ����ݼ���
					indexs.addAll(de.getIndex());
				}
				minNode = minNode.getRightNode();
			}
			for (DataEntry de : now.getData()) {//������ǰҶ����µ����ݼ���
				if (de.getKey() > constant) {//��ǰ�ؼ��ִ��ڴ��Ƚϳ����������ֱ������ѭ��
					break;
				}
				indexs.addAll(de.getIndex());
			}
		} else if ("=".equals(c.getOper()) && (c.isLeftIsIndex() || c.isRightIsIndex())) {
			indexs = new HashSet<Integer>();
			for (DataEntry de : now.getData()) {
				if (de.getKey() == constant) {
					indexs.addAll(de.getIndex());
				}
			}
		} else if (">".equals(c.getOper())  && c.isLeftIsIndex() || "<".equals(c.getOper()) && c.isRightIsIndex()) {
			indexs = new HashSet<Integer>();
			for (DataEntry de : now.getData()) {//������ǰҶ����µ����ݼ���
				if (de.getKey() <= constant) {//��ǰ�ؼ���С�ڵ��ڴ��Ƚϳ���������Ӽ�����һ��ѭ��
					continue;
				}
				indexs.addAll(de.getIndex());
			}
			now = now.getRightNode();
			while (now != null) {//��ȡ��ǰҶ�����������ݣ�����¼�����У�
				for (DataEntry de : now.getData()) {//������ǰҶ����µ����ݼ���
					indexs.addAll(de.getIndex());
				}
				now = now.getRightNode();
			}
		} else if (">=".equals(c.getOper())  && c.isLeftIsIndex() || "<=".equals(c.getOper()) && c.isRightIsIndex()) {
			indexs = new HashSet<Integer>();
			for (DataEntry de : now.getData()) {//������ǰҶ����µ����ݼ���
				if (de.getKey() < constant) {//��ǰ�ؼ���С�ڴ��Ƚϳ���������Ӽ�����һ��ѭ��
					continue;
				}
				indexs.addAll(de.getIndex());
			}
			now = now.getRightNode();
			while (now != null) {//��ȡ��ǰҶ�����������ݣ�����¼�����У�
				for (DataEntry de : now.getData()) {//������ǰҶ����µ����ݼ���
					indexs.addAll(de.getIndex());
				}
				now = now.getRightNode();
			}
		}
		
		return indexs;
	}
	/**
	 * �־û�����ṹ
	 * @param table
	 * @param file
	 * @throws Exception
	 */
	public static void perpetuate(Object obj, File file) throws Exception {
		/*
		 * ��������Ϣд��configFile
		 */
		file.delete();
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
		oos.writeObject(obj);// ������ṹ��Ϣ�־û�
		oos.close();
	}
}
