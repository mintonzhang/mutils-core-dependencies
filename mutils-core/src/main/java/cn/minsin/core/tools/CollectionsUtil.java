package cn.minsin.core.tools;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author minton.zhang
 * @since 2021/6/9 10:59
 */
public interface CollectionsUtil {


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
}
