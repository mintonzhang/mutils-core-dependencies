package cn.minsin.core.tools;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author minton.zhang
 * @since 2021/3/23 14:42
 */
public interface SortUtil {


	/**
	 * 排序并返回自身
	 *
	 * @param data       需要排序的数据
	 * @param comparator 比较器
	 */
	static <T> List<T> sort(List<T> data, Comparator<T> comparator) {
		data.sort(comparator);
		return data;
	}

	/**
	 * 排序并返回自身
	 *
	 * @param data 需要排序的数据
	 */
	static <T extends Comparable<? super T>> List<T> sort(List<T> data) {
		Collections.sort(data);
		return data;
	}
}
