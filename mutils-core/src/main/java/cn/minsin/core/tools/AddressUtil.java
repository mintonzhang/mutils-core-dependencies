package cn.minsin.core.tools;

import cn.minsin.core.tools.function.FunctionalInterfaceUtil;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 详细地址解析成为对象
 */
public class AddressUtil {

    private static final String regex = "(?<province>[^省]+自治区|.*?省|.*?行政区|.*?市)(?<city>[^市]+自治州|.*?地区|.*?行政单位|.+盟|市辖区|.*?市|.*?县)(?<district>[^县]+县|.+区|.+市|.+旗|.+海域|.+岛)?(?<town>[^区]+区|.+镇)?(?<detail>.*)";


    private static final List<String> SPECIAL_CITIES = Lists.newArrayList("北京", "上海", "重庆", "天津");

    private static final String CITY_CHINESE_NAME = "市";

    /**
     * <pre>
     * 注意:重庆市、上海市、北京市等等直辖市 省市需要填写一样的 如:重庆市重庆市沙坪坝区西永镇西永天街xxxx
     * </pre>
     *
     * @param address   需要解析的地址
     * @param delimiter 分隔符 默认""
     */
    public static ParseResult resolve(@NonNull String address, CharSequence delimiter) {
        String realAddress = address.replace(delimiter == null ? "" : delimiter, "");
        Matcher m = Pattern.compile(regex).matcher(realAddress);
        ParseResult parseResult = new ParseResult();
        if (m.find()) {
            parseResult.setProvince(FunctionalInterfaceUtil.ifNotNullExecuteOrDefault(m.group("province"), null, String::trim));
            parseResult.setCity(FunctionalInterfaceUtil.ifNotNullExecuteOrDefault(m.group("city"), null, String::trim));
            parseResult.setDistrict(FunctionalInterfaceUtil.ifNotNullExecuteOrDefault(m.group("district"), null, String::trim));
            parseResult.setDetail(FunctionalInterfaceUtil.ifNotNullExecuteOrDefault(m.group("detail"), null, String::trim));
            parseResult.setTown(FunctionalInterfaceUtil.ifNotNullExecuteOrDefault(m.group("town"), null, String::trim));
        }
        return parseResult;
    }

    /**
     * 将地址拼接成为一个字符串
     *
     * @param delimiter 分隔符 默认""
     * @param province  省
     * @param city      市
     * @param district  区
     * @param town      镇
     * @param detail    详细地址
     */
    public static String splice(String province, String city, String district, String town, String detail, CharSequence delimiter) {
        if (SPECIAL_CITIES.contains(province) || SPECIAL_CITIES.contains(city)) {
            province = province.concat(CITY_CHINESE_NAME);
            city = province;
        }
        StringJoiner stringJoiner = new StringJoiner(delimiter == null ? "" : delimiter);
        FunctionalInterfaceUtil.ifNotNullExecute(province, stringJoiner, (a, b) -> b.add(a));
        FunctionalInterfaceUtil.ifNotNullExecute(city, stringJoiner, (a, b) -> b.add(a));
        FunctionalInterfaceUtil.ifNotNullExecute(district, stringJoiner, (a, b) -> b.add(a));
        FunctionalInterfaceUtil.ifNotNullExecute(town, stringJoiner, (a, b) -> b.add(a));
        FunctionalInterfaceUtil.ifNotNullExecute(detail, stringJoiner, (a, b) -> b.add(a));
        return stringJoiner.toString();
    }

    @Getter
    @Setter
    @ToString
    public static class ParseResult {
        private String province, city, district, town, detail;
    }
}