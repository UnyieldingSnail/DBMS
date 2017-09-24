package com.zhangyujie.db;

import java.io.File;
import java.util.List;

import com.zhangyujie.entity.DataEntry;
import com.zhangyujie.entity.Field;
import com.zhangyujie.main.DBMS;
import com.zhangyujie.util.OperUtil;

public class Create implements Operate {
	private Table table = null;// �������Ĺ�ϵ
	private String account = null;// create���
	private String name = null;// �������Ĺ�ϵ�������ݿ���
	private String indexName = null;// ������
	private String fieldName = null;// ������������������

	public Create() {
		super();
	}

	public Create(String account) {
		this.account = account;
	}

	@Override
	public void start() throws Exception {
		switch (ParseAccount.parseCreate(this)) {// ����create��䣬������1���Ǵ������ݿ������������2���Ǵ�����ϵ����,����3��Ϊ����������
		case 1:
			parseCreateDatabase();// �������ݿ�
			break;
		case 2:
			parseCreateTable();// ������ϵ��
			break;
		case 3:
			parseCreateIndex();// ��������
			break;
		default:
			System.out.println("Error");// �������򱨴�
			break;
		}
	}

	private void parseCreateDatabase() throws Exception {
		createDatabase();// �������ݿ�
		OperUtil.perpetuate(DBMS.dbNames, DBMS.DBS);
	}

	private void parseCreateTable() throws Exception {
		this.table = ParseAccount.parseTable(this.account, this.name);// ����create-table��䣬��ȡTableʵ��
		this.createTable();// ������ϵ
		OperUtil.perpetuate(this.table, this.table.getConfigFile());//�־û���ϵ��ṹ
		OperUtil.perpetuate(DBMS.dataDictionary, DBMS.dataDictionary.getConfigFile());//�־û����ݿ�ṹ
	}

	private void parseCreateIndex() throws Exception {
		ParseAccount.parseIndex(this);//����create-index���
		this.table = OperUtil.loadTable(this.name);//���ش����������Ĺ�ϵ��
		this.createIndex();//��������
	}

	/**
	 * �������ݿ⣡����
	 * 
	 * @param name
	 */
	private void createDatabase() {
		File dir = new File(DBMS.ROOTPATH + File.separator + this.name);
		if (dir.exists()) {
			throw new RuntimeException("���ݿ��Ѵ��ڣ�");
		}
		System.out.println("���ݿⴴ���ɹ���");
		dir.mkdirs();
		DBMS.dbNames.add(this.name);
	}

	/**
	 * ����һ��table����������Ϣд��configFile����������������ֵ䣡����
	 */
	private void createTable() throws Exception {
		this.table.getFile().createNewFile();
		/*
		 * ��������������ֵ�
		 */
		DBMS.dataDictionary.getTables().add(this.table.getTableName());
		DBMS.loadedTables.put(this.table.getTableName(), this.table);
		System.out.println("һ�������ɹ���");
	}

	/**
	 * ����B+������
	 * 
	 * @param create
	 * @throws Exception
	 */
	private void createIndex() throws Exception {
		int column = -1;
		String type = null;
		Field field = null;
		for (Field field1 : this.getTable().getAttributes()) {// �ҵ�����������������������
			if (field1.getFieldName().equals(this.getFieldName())) {
				column = field1.getColumn();
				type = field1.getType();
				field = field1;
			}
		}
		if (column == -1 || field == null) {
			throw new RuntimeException("�˹�ϵ�����޴����ԣ�");
		}
		List<DataEntry> indexList = OperUtil.getIndex(this.getTable().getFile(), column, type);// ��ȡָ����ֵ��������
		field.setIndex(true);// ������
		field.setIndexRoot(BTree.buildBT(indexList));// ����B+������ȡ���ڵ�
		DBMS.indexEntry.put(this.indexName, field);//��������������������ӵ�map������ɾ������
//		BTree.display(BTree.root);//��ʾ������B+��
		System.out.println("���������ɹ���");

	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

}
