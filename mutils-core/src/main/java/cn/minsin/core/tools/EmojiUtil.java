package cn.minsin.core.tools;

/**
 * 特殊表情过滤
 *
 * @author mintonzhang
 * @date 2019年1月29日
 * @since 0.1.0
 */
public class EmojiUtil {
    protected EmojiUtil() {
        // allow Subclass
    }

    public static boolean containsEmoji(String source) {
        if (StringUtil.isBlank(source)) {
            return false;
        }
        return source.matches("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]");
    }

    /**
     * 过滤emoji 或者 其他非文字类型的字符
     *
     * @param source
     */
    public static String filterEmoji(String source) {
        if (StringUtil.isBlank(source)) {
            return source;
        }
        return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "");
    }

}
