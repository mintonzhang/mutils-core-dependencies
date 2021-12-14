package cn.minsin.core.tools.vaild;

import cn.minsin.core.tools.NumberUtil;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author minton.zhang
 * @since 2021/1/18 上午10:24
 */
public class JavaxValidatorUtil {

    private static final Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * javax.validation注解校验
     *
     * @param object 将要校验的对象
     */
    public static <T> List<String> validate(T object) {
        Set<ConstraintViolation<T>> set = validator.validate(object);
        List<String> validateError = new ArrayList<>();
        if (set.size() > 0) {
            for (ConstraintViolation<T> val : set) {
                validateError.add(val.getMessage());
            }
        }
        return validateError;
    }

    /**
     * javax.validation注解校验
     *
     * @param object 将要校验的对象
     */
    public static <T> void validateThenThrowError(T object) {
        final Set<ConstraintViolation<T>> validate = validator.validate(object);
        for (ConstraintViolation<T> val : validate) {
            throw new ValidationException("参数'" + val.getPropertyPath() + "'校验失败,请检查后重试");
        }
    }

    /**
     * javax.validation注解校验
     *
     * @param object 将要校验的对象
     */
    public static <T> void validatePropertiesThenThrowError(T object, String propertiesName) {
        final Set<ConstraintViolation<T>> validate = validator.validateProperty(object, propertiesName);
        for (ConstraintViolation<T> val : validate) {
            throw new ValidationException("参数'" + val.getPropertyPath() + "'校验失败,请检查后重试");
        }
    }

    /**
     * javax.validation注解校验
     */
    public static Validator getValidator() {
        return validator;
    }

    /**
     * 是否数字
     */
    public static boolean isNumber(String str) {
        return NumberUtil.isNumbers(str);
    }


    /**
     * 是否正整数
     */
    public static boolean isPositiveInteger(String str) {
        try {
            String regex = "^[1-9]+[0-9]*$";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(str);
            if (m.find()) {
                return true;
            }
        } catch (Exception e) {
            //unnecessary
        }
        return false;
    }
}
