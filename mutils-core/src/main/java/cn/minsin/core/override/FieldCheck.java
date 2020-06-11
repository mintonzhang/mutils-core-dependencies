package cn.minsin.core.override;

import cn.minsin.core.annotation.NotNull;
import cn.minsin.core.tools.ModelUtil;

import java.io.Serializable;

/**
 * 字段检查 配合{@link NotNull}
 *
 * @author: minton.zhang
 * @since: 0.0.8.RELEASE
 */
public interface FieldCheck extends Serializable {

    /**
     * 验证字段
     */
    default void verificationField() {
        ModelUtil.verificationField(this);
    }
}
