package com.zhangyujie.test;

import java.util.HashMap;
import java.util.Map;

public class HashMapTest {

	public static void main(String[] args) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		map.put("����", 1);
		map.put("����", 2);
		map.put("����", 3);
		map.put("����", 4);
		map.put("����", 1);
		map.put("����", 6);
		map.put("����", 9);
		
		System.out.println(map.get("����"));
	}

}
