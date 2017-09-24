package com.zhangyujie.test;

import java.util.HashMap;
import java.util.Map;

public class HashMapTest {

	public static void main(String[] args) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		map.put("张三", 1);
		map.put("张三", 2);
		map.put("张三", 3);
		map.put("张三", 4);
		map.put("李四", 1);
		map.put("王武", 6);
		map.put("超人", 9);
		
		System.out.println(map.get("张三"));
	}

}
