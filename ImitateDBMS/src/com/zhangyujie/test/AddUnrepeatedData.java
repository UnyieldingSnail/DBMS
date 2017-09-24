package com.zhangyujie.test;
/**
 * 向文件中插入随机不重复记录
 */
import java.io.File;
import java.io.PrintWriter;
import java.util.Random;

public class AddUnrepeatedData {

	public static void main(String[] args) throws Exception {
		File file = new File("./database/test1/unrepatedIndex");
		PrintWriter pw = new PrintWriter(file);
		
		Random rand = new Random();
		int[] array = randomArray(1, 250000, 10001);
		int n = 10000;
		String line = null;
		while (n-- > 0) {
			line = randomPhrase(rand);
			pw.println("\"" + line + "\"," + array[n]);
//			System.out.println("\"" + line + "\"," + array[n]);
		}
		pw.close();
	}
	/**
	 * 随机生成三个GBK编码的汉字
	 * @param rand
	 * @return
	 */
	public static String randomPhrase(Random rand) {
		
		int[] arr = randomArray(19968, 40869, 15000);
		char[] str = new char[3];
		str[0] = (char) arr[rand.nextInt(10000)];
		str[1] = (char) arr[rand.nextInt(10000)];
		str[2] = (char) arr[rand.nextInt(10000)];
		return new String(str);
	}
	/**
	 * 随机生成指定范围的不重复的N个整数
	 * @param min
	 * @param max
	 * @param n
	 * @return
	 */
	public static int[] randomArray(int min, int max, int n) {
		int len = max - min + 1;

		if (max < min || n > len) {
			return null;
		}

		// 初始化给定范围的待选数组
		int[] source = new int[len];
		for (int i = min; i < min + len; i++) {
			source[i - min] = i;
		}

		int[] result = new int[n];
		Random rd = new Random();
		int index = 0;
		for (int i = 0; i < result.length; i++) {
			// 待选数组0到(len-2)随机一个下标
			index = Math.abs(rd.nextInt() % len--);
			// 将随机到的数放入结果集
			result[i] = source[index];
			// 将待选数组中被随机到的数，用待选数组(len-1)下标对应的数替换
			source[index] = source[len];
		}
		return result;
	}

	public static char byteToChar(byte[] b) {
		char c = (char) (((b[0] & 0xFF) << 8) | (b[1] & 0xFF));
		return c;
	}

}
