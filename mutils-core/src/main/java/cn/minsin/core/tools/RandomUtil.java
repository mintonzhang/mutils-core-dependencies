package cn.minsin.core.tools;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * 随机数生成
 *
 * @author: minton.zhang
 * @since: 2020/7/15 14:48
 */
public class RandomUtil extends RandomStringUtils {

	/**
	 * 生成count位数字随机数
	 *
	 * @param count
	 * @return
	 */
	public static String randomNumber(int count) {
		return RandomStringUtils.random(count, false, true);
	}

	/**
	 * 生成count位字符串随机数
	 *
	 * @param count   长度
	 * @param toLower 是否转换为小写
	 */
	public static String randomString(int count, boolean toLower) {
		String random = randomString(count);
		return toLower ? random.toLowerCase() : random.toUpperCase();
	}

	/**
	 * 生成count位字符串随机数 大小写混合
	 *
	 * @param count 长度
	 */
	public static String randomString(int count) {
		return RandomStringUtils.random(count, true, false);
	}

}
