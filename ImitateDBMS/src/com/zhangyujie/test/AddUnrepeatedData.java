package com.zhangyujie.test;
/**
 * ���ļ��в���������ظ���¼
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
	 * �����������GBK����ĺ���
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
	 * �������ָ����Χ�Ĳ��ظ���N������
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

		// ��ʼ��������Χ�Ĵ�ѡ����
		int[] source = new int[len];
		for (int i = min; i < min + len; i++) {
			source[i - min] = i;
		}

		int[] result = new int[n];
		Random rd = new Random();
		int index = 0;
		for (int i = 0; i < result.length; i++) {
			// ��ѡ����0��(len-2)���һ���±�
			index = Math.abs(rd.nextInt() % len--);
			// �������������������
			result[i] = source[index];
			// ����ѡ�����б�������������ô�ѡ����(len-1)�±��Ӧ�����滻
			source[index] = source[len];
		}
		return result;
	}

	public static char byteToChar(byte[] b) {
		char c = (char) (((b[0] & 0xFF) << 8) | (b[1] & 0xFF));
		return c;
	}

}
