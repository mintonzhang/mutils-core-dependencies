package cn.minsin.core.web.result;

import cn.minsin.core.constant.SuppressWarningsTypeConstant;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author: minton.zhang
 * @since: 2020/5/9 20:15
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class PageData<E> implements Serializable {

	private Collection<E> data;

	private int page = 1;

	private int size = 10;

	private int totalCount;

	private int totalPage;

	public static <E> PageData<E> of(int page, int size) {
		return new PageData<E>().setPage(page).setSize(size);
	}

	public static <E> PageData<E> of(int page, int size, Collection<E> data) {
		return new PageData<E>().setPage(page).setSize(size).setData(data);
	}

	//********************************普通方法区****************************************//


	//********************************匿名空类****************************************//

	public static <E> PageData<E> of() {
		return new PageData<>();
	}

	//********************************匿名空类****************************************//


	//********************************静态方法区****************************************//

	public static <E> PageData<E> empty() {
		return EmptyPage.getInstance();
	}

	//********************************普通方法区****************************************//
	protected <R> PageData<R> copy(@NonNull Collection<R> data) {
		return new PageData<R>().setSize(this.size).setPage(this.page).setTotalCount(this.totalCount).setTotalPage(this.totalPage).setData(data);
	}

	/**
	 * 是否还有下一页
	 */
	public boolean getHasMore() {
		return totalPage > page;
	}

	/**
	 * 将E转换成R 并重新生成pageData
	 *
	 * @param function 处理函数
	 */
	public <R> PageData<R> map(@NonNull Function<E, R> function) {
		ArrayList<R> newData = Lists.newArrayListWithCapacity(data.size());
		data.forEach(e -> {
			R apply = function.apply(e);
			newData.add(apply);
		});
		return this.copy(newData);
	}


	//********************************静态方法区****************************************//


	//********************************函数转换、消费区****************************************//

	/**
	 * 对list进行二次处理
	 *
	 * @param function 处理函数
	 */
	public PageData<E> forEach(@NonNull Consumer<E> function) {
		this.data.forEach(function);
		return this;
	}

	/**
	 * 传入一个新的List 覆盖原数据
	 */
	public <N> PageData<N> override(@NonNull Collection<N> collection) {
		return this.copy(collection);
	}

	private static class EmptyPage extends PageData<Object> {

		private static final EmptyPage EMPTY_PAGE;

		static {
			// 允许修改 page和size  e: Allow modify the page and size.
			EMPTY_PAGE = new EmptyPage();
		}

		@SuppressWarnings(SuppressWarningsTypeConstant.UNCHECKED)
		public static <E> PageData<E> getInstance() {
			return (PageData<E>) EMPTY_PAGE;
		}

		@Override
		public Collection<Object> getData() {
			return Collections.emptyList();
		}

		@Override
		public PageData<Object> setData(Collection<Object> data) {
			throw new UnsupportedOperationException();
		}

		@Override
		public PageData<Object> setTotalCount(int totalCount) {
			throw new UnsupportedOperationException();
		}

		@Override
		public PageData<Object> setTotalPage(int totalPage) {
			throw new UnsupportedOperationException();
		}
	}


	//********************************函数转换、消费区****************************************//
}
