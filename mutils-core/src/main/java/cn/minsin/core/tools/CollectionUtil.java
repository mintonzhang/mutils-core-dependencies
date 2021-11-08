package cn.minsin.core.tools;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * 更多查看{@link CollectionUtils}
 *
 * @author minton.zhang
 * @since 2021/6/9 10:59
 */
public interface CollectionUtil {


    static <T> void addIgnoreNull(final Collection<T> collection, final T object, boolean throwException) {

        if (throwException) {
            CollectionUtils.addIgnoreNull(collection, object);
        } else {
            if (object != null) {
                collection.add(object);
            }
        }
    }

    static <T> void addIgnoreNull(final Collection<T> collection, final T object) {
        addIgnoreNull(collection, object, false);
    }

    static <T> List<T> singletonUnModifyList(final T object) {
        if (object == null) {
            return Collections.emptyList();
        }
        return Collections.singletonList(object);
    }

    static <T> List<T> singletonList(final T object) {
        final List<T> list = new ArrayList<>(1);
        if (object != null) {
            list.add(object);
        }
        return list;
    }

    static boolean isAnyEmpty(final Collection<?>... collections) {
        for (Collection<?> collection : collections) {
            if (CollectionUtils.isEmpty(collection)) {
                return true;
            }
        }
        return false;
    }

    static boolean isAllEmpty(final Collection<?>... collections) {

        for (Collection<?> collection : collections) {
            if (CollectionUtils.isNotEmpty(collection)) {
                return false;
            }
        }
        return true;
    }

    static <T> Set<T> newSingleHashSet(final T object) {

        Set<T> set = Sets.newHashSet();
        if (object != null) {
            set.add(object);
        }
        return set;

    }

    static <T> List<T> newSingleArrayList(final T object) {

        List<T> set = Lists.newArrayList();
        if (object != null) {
            set.add(object);
        }
        return set;
    }


}