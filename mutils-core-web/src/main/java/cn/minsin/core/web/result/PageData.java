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
import java.util.Iterator;
import java.util.function.Function;

/**
 * @author minton.zhang
 * @since 2020/5/9 20:15
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class PageData<E> implements Iterable<E>, Serializable {

    private Collection<E> data = new ArrayList<>();

    private long page = 1;

    private long size = 10;

    private long totalCount;

    private long totalPage;

    public static <E> PageData<E> of(long page, long size) {
        return new PageData<E>().setPage(page).setSize(size);
    }

    public static <E> PageData<E> of(long page, long size, Collection<E> data) {
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
        return page * size < totalCount;
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
     * 传入一个新的List 覆盖原数据
     */
    public <N> PageData<N> override(@NonNull Collection<N> collection) {
        return this.copy(collection);
    }

    public PageData<E> setTotalCount(long totalCount) {
        this.totalCount = totalCount;
        this.totalPage = this.calculationTotalPage();
        return this;
    }

    protected long calculationTotalPage() {
        try {
            return totalCount % size == 0 ? totalCount / size : (totalCount / size + 1);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return data.iterator();
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
        public PageData<Object> setTotalCount(long totalCount) {
            throw new UnsupportedOperationException();
        }

        @Override
        public PageData<Object> setTotalPage(long totalPage) {
            throw new UnsupportedOperationException();
        }
    }


    //********************************函数转换、消费区****************************************//

}
