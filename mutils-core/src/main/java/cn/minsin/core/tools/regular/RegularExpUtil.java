package cn.minsin.core.tools.regular;

import lombok.RequiredArgsConstructor;

import java.util.regex.Pattern;


@RequiredArgsConstructor
public enum RegularExpUtil implements RegularExpWrapper {

    /**
     * 中国大陆手机号
     */
    CHINA_MOBILE(Pattern.compile("^1[3-9][0-9]{9}$")),
    /**
     * 中文
     */
    CHINESE(Pattern.compile("^[\u4e00-\u9fa5]*$")),

    /**
     * 中文和特殊字符
     */
    SPECIAL_CHARACTERS(Pattern.compile("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]")),

    LOWERCASE(Pattern.compile("^[a-z]*$")),

    UPPERCASE(Pattern.compile("^[A-Z]*$")),

    NUMBERS(Pattern.compile("^[0-9]*$")),

    ALL_CHARACTERS_NUMBERS(Pattern.compile("^[A-Za-z0-9]*$")),

    CHARACTERS(Pattern.compile("^[A-Za-z]*$")),

    /**
     * web前端错误内容
     */
    WEB_ERROR_CONTENT(Pattern.compile("^(?im)undefined|null|NaN$"));

    private final Pattern pattern;

    @Override
    public Pattern getPattern() {
        return pattern;
    }
}
