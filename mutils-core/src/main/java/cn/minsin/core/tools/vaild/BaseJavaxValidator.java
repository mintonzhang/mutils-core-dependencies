package cn.minsin.core.tools.vaild;

import java.util.List;

public interface BaseJavaxValidator {

    default List<String> validate() {
        return JavaxValidatorUtil.validate(this);
    }

    default void validateThenThrowError() {
        JavaxValidatorUtil.validateThenThrowError(this);
    }
}
