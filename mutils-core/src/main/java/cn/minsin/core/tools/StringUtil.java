package cn.minsin.core.tools;

import com.google.common.collect.Lists;
import lombok.NonNull;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串帮助类 可参考 {@link StringUtils}
 *
 * @author mintonzhang
 * @date 2019年2月14日
 * @since 0.1.0
 */
public class StringUtil extends StringUtils {

	static Pattern p = Pattern.compile("[\u4E00-\u9FA5|\\！|\\，|\\。|\\（|\\）|\\《|\\》|\\“|\\”|\\？|\\：|\\；|\\【|\\】]");

	protected StringUtil() {
		// allow Subclass
	}

	/**
	 * 判断是否为空 如果为空则返回默认值
	 *
	 * @param cs  判断的字符串
	 * @param def 默认值字符串
	 */
	public static String isBlankWithDefault(String cs, String def) {
		return isBlank(cs) ? def : cs;
	}


	/**
	 * 关键字去空格及过滤
	 *
	 * @param str
	 * @param filterKey
	 */
	public static String filterSearchKey(String str, String... filterKey) {
		str = filterSpace(str);
		if (filterKey != null && filterKey.length > 0 && str != null) {
			for (String string : filterKey) {
				if (string.equals(str)) {
					return null;
				}
			}
		}
		return str;
	}

	/**
	 * 关键字去空格及过滤 如果出现关键字将替换为空
	 *
	 * @param str
	 * @param filterKey
	 */
	public static String filterSearchKeyAndReplace(String str, String... filterKey) {
		str = filterSpace(str);
		if (ArrayUtils.isNotEmpty(filterKey)) {
			for (String string : filterKey) {
				str = str.replace(string, "");
			}
		}
		return isBlankWithDefault(str, null);
	}

	/**
	 * 去除两端空格
	 *
	 * @param str 2018年9月21日
	 */
	public static String filterSpace(String str) {
		return StringUtils.isBlank(str) ? null : str.trim();
	}

	/**
	 * 去除所有空格
	 *
	 * @param str
	 */
	public static String filterAllSpace(String str) {
		return filterSpace(str) == null ? null : str.replace(" ", "");
	}

	/**
	 * 关键字 模糊查询
	 *
	 * @param key
	 * @param type -1 为%key 0为 %key% 1为key% 其他默认为0
	 */
	public static String likeSearch(String key, int type, String... filterKey) {
		key = filterSearchKey(key, filterKey);
		if (key != null) {
			return type == -1 ? "%" + key : type == 1 ? key + "%" : "%" + key + "%";
		}
		return null;
	}

	/**
	 * 生成length位随机UUID随机主键
	 */
	public static String createUUID() {
		return UUID.randomUUID().toString().replace("-", "").toLowerCase();
	}

	/**
	 * 生成length位随机UUID随机主键
	 */
	public static String getUUIDForLength(int length) {
		String uuid = createUUID();
		if (length < 0 || length > 32) {
			return uuid;
		}
		return uuid.substring(0, length);
	}

	/**
	 * 判断字符串是否超长
	 *
	 * @param str       待定字符串
	 * @param length    长度
	 * @param allowNull 是否可以为空
	 *                  true为不满足条件 false为满足条件
	 */
	public static boolean checkStringLength(String str, int length, boolean allowNull) {
		if (StringUtil.isBlank(str)) {
			return !allowNull;
		}
		return str.length() > length;
	}

	/**
	 * 替换下划线 并且把下划线后的第一个单词大写
	 *
	 * @param str
	 */
	public static String replaceUnderline(String str) {
		if (StringUtil.isBlank(str)) {
			return "";
		}
		String[] split = str.split("_");
		for (int i = 0; i < split.length; i++) {
			split[i] = firstCharacterToUpper(split[i]);
		}
		return String.join("", split);
	}

	/**
	 * 替换下划线 并且把下划线后的第一个单词大写
	 *
	 * @param str
	 */
	public static String replaceUnderline(String[] str) {

		for (int i = 0; i < str.length; i++) {
			str[i] = firstCharacterToUpper(str[i]);
		}
		return String.join("", str);
	}

	/**
	 * 首字母大写
	 *
	 * @param srcStr
	 */
	public static String firstCharacterToUpper(String srcStr) {
		return isBlank(srcStr) ? null : srcStr.substring(0, 1).toUpperCase() + srcStr.substring(1);
	}

	/**
	 * 删除格式
	 *
	 * @param str
	 */
	public static String removeFormat(String str) {
		str = filterAllSpace(str);
		return str == null ? null : str.replace("\r", "").replace("\n", "").replace("\t", "");
	}

	/**
	 * 字符串是否包含中文
	 *
	 * @param str 待校验字符串
	 *            true 包含中文字符 false 不包含中文字符
	 */
	public static boolean isContainChinese(String str) {
		if (StringUtil.isBlank(str)) {
			return false;
		}
		Matcher m = p.matcher(str);
		return m.find();
	}

	/**
	 * 判断是否为中文
	 *
	 * @param str      原字符串
	 * @param keywords 需要过滤的字符(即包含此字符将视为中文)
	 */
	public static boolean isChinese(String str, String... keywords) {
		str = filterSearchKeyAndReplace(str, keywords);
		if (isBlank(str)) {
			return false;
		}
		String reg = "[\\u4e00-\\u9fa5]+";
		return str.matches(reg);
	}

	/**
	 * 提取字符串中的中文
	 *
	 * @param source 如果为空返回null
	 */
	public static String pickUpChinese(String source) {
		return StringUtil.replaceAll(source, "[^\u4e00-\u9fa5]+", "");
	}


	//********************************分割线****************************************//
	//以下为 2020年5月9日19:59:16新增方法


	@SuppressWarnings({"rawtypes", "unchecked"})
	public static <Input> boolean isBlank(Input verifiedData, Predicate<Input> customFunction) {
		if (customFunction != null) {
			return customFunction.test(verifiedData);
		}
		if (verifiedData != null && verifiedData.getClass().isArray()) {
			return ((Input[]) verifiedData).length == 0;
		} else if (verifiedData instanceof Collection) {
			return ((Collection) verifiedData).isEmpty();
		} else if (verifiedData instanceof Map) {
			return ((Map) verifiedData).isEmpty();
		} else if (verifiedData instanceof CharSequence) {
			return ((CharSequence) verifiedData).length() == 0;
		} else {
			return verifiedData == null;
		}
	}

	public static <Input> boolean isNotBlank(Input verifiedData, Predicate<Input> customFunction) {
		return !isBlank(verifiedData, customFunction);
	}

	public static <Input> boolean isBlank(Input verifiedData) {
		return isBlank(verifiedData, null);
	}

	public static <Input> boolean isNotBlank(Input verifiedData) {
		return isNotBlank(verifiedData, null);
	}

	/**
	 * 是否有空值
	 *
	 * @param verifiedData 被验证数据
	 * @return
	 */
	public static boolean isBlank(Object... verifiedData) {
		if (verifiedData.length == 0) {
			return true;
		}
		for (Object verifiedDatum : verifiedData) {
			boolean blank = isBlank(verifiedDatum);
			if (blank) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否全部空值
	 *
	 * @param verifiedData 被验证数据
	 * @return
	 */
	public static boolean isAllBlank(Object... verifiedData) {
		for (Object verifiedDatum : verifiedData) {
			boolean notBlank = isNotBlank(verifiedDatum);
			if (!notBlank) {
				return false;
			}
		}
		return true;
	}


	public static List<String> splitStr(String str, @NonNull String regex) {
		if (isBlank(str)) {
			return Collections.emptyList();
		}
		return splitStr(str, regex, Lists::newArrayList);
	}

	public static <T> T splitStr(@NonNull String str, @NonNull String regex, Function<String[], T> conver) {
		return conver.apply(str.split(regex));
	}
}
