package cn.minsin.core.tools.pinyin;

import cn.minsin.core.override.JsonString;
import cn.minsin.core.tools._assert.CA;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author minton.zhang
 * @since 2021/10/19 13:58
 */
public class PinyinParser {

    private static final HanyuPinyinOutputFormat FORMAT_DEFAULT;

    static {
        FORMAT_DEFAULT = new HanyuPinyinOutputFormat();
        FORMAT_DEFAULT.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        FORMAT_DEFAULT.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
    }

    private final HanyuPinyinOutputFormat hanyuPinyinOutputFormat;

    private final char joiner;

    public PinyinParser(HanyuPinyinOutputFormat hanyuPinyinOutputFormat, char joiner) {
        this.hanyuPinyinOutputFormat = hanyuPinyinOutputFormat;
        this.joiner = joiner;
    }

    public PinyinParser() {
        hanyuPinyinOutputFormat = FORMAT_DEFAULT;
        this.joiner = ' ';
    }
//
//    public String[] parseOutputOne(String str) {
//        List<String> result = this.parseStr2List(str, e -> e[0], String::valueOf);
//
//         this.resolve(result, (full, first) -> );
//    }

    public PinyinParseResult single(String str) {
        return this.parsing(str, false);
    }

    /**
     * 解析 包含多音字
     */
    public PinyinParseResult multi(String str) {
        return this.parsing(str, true);
    }

    /**
     * 解析 包含多音字
     */
    public PinyinParseResult parsing(String str, boolean includeMultiLetter) {
        CA.isNull(str, "被解析的字符串不运行为空");
        if (includeMultiLetter) {
            List<String[]> result = parseStr2List(str, e -> e, e -> new String[]{String.valueOf(e)});

            return this.resolve(result, PinyinParseResult::new);
        } else {
            List<String[]> result = parseStr2List(str, e -> new String[]{e[0]}, e -> new String[]{String.valueOf(e)});

            return this.resolve(result, PinyinParseResult::new);
        }
    }


    protected <T> List<T> parseStr2List(String str, Function<String[], T> findConvert, Function<Character, T> otherConvert) {
        List<T> result = new ArrayList<>(str.length());

        for (char c : str.toCharArray()) {
            if (c > 128) {
                try {
                    // 取得当前汉字的所有全拼
                    String[] array = PinyinHelper.toHanyuPinyinStringArray(c, hanyuPinyinOutputFormat);
                    if (array != null) {
                        result.add(findConvert.apply(array));
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    // 无法解析
                }
            } else {
                result.add(otherConvert.apply(c));
            }
        }
        return result;
    }


    protected <T> T resolve(List<String[]> multiLetter, BiFunction<String[], String[], T> function) {
        int index = 0;
        String[] fillLetter = null;
        String[] firstLetter = null;
        while (true) {
            String[] strings = multiLetter.get(index);
            if (strings == null) {
                break;
            }
            fillLetter = this.fullLetter(fillLetter, strings);
            firstLetter = this.firstLetter(firstLetter, this.subString0(strings));
            index++;
            if (index >= multiLetter.size()) {
                break;
            }
        }
        return function.apply(fillLetter, firstLetter);
    }

    /**
     * 全拼
     */
    protected String[] fullLetter(String[] a, String[] b) {
        if (a == null && b == null) {
            return b;
        }
        if (a == null) {
            return b;
        }
        return Stream.of(a).flatMap(f -> Stream.of(b).map(s -> f + joiner + s)).distinct().toArray(String[]::new);
    }

    /**
     * 首字母
     */
    protected String[] firstLetter(String[] a, String[] b) {
        if (a == null && b == null) {
            return b;
        }
        if (a == null) {
            return b;
        }
        return Stream.of(a).flatMap(f -> Stream.of(b).map(s -> f + s.charAt(0))).distinct().toArray(String[]::new);

    }


    /**
     * 截取第一个字母
     */
    protected String[] subString0(String[] s) {
        return Stream.of(s).map(w -> w.substring(0, 1)).distinct().toArray(String[]::new);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class PinyinParseResult implements JsonString {

        /**
         * 拼音全拼
         */
        private String[] fullPinyin;

        /**
         * 拼音首字母
         */
        private String[] firstPinyin;


        public String[] getAllArray() {
            return ArrayUtils.addAll(firstPinyin, fullPinyin);
        }
    }
}
