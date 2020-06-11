package cn.minsin.core.tools.coordinate;

import cn.minsin.core.tools.math.BigDecimalPlus;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.StringJoiner;
import java.util.function.Function;

/**
 * <pre>
 * 介绍:
 *  经度(longitude)：东经为正数，西经为负数。
 *  纬度(latitude)：北纬为正数，南纬为负数。
 *  <a href="https://tool.lu/coordinate/">取址工具</a>
 *
 * WGS84坐标系:地球坐标系，国际通用坐标系
 * GCJ02坐标系:火星坐标系，WGS84坐标系加密后的坐标系；Google国内地图、高德、QQ地图 使用
 * BD09坐标系:百度坐标系，GCJ02坐标系加密后的坐标系
 * </pre>
 *
 * @author: minton.zhang
 * @since: 2020/5/13 15:54
 */

@Getter
@Setter
public abstract class Gps {

    /**
     * 经度
     */
    protected double longitude;
    /**
     * 纬度
     */
    protected double latitude;

    protected CoordinateType coordinateType;

    public Gps(double longitude, double latitude, CoordinateType coordinateType) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.coordinateType = coordinateType;
    }

    public Gps(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public boolean isInChina() {
        return !CoordinateUtil.outOfChina(this.latitude, this.longitude);
    }


    protected BD09 convertToBD09() {
        return null;
    }

    protected GCJ02 convertToGCJ02() {
        return null;
    }

    protected WGS84 convertToWGS84() {
        return null;
    }

    /**
     * 计算坐标点距离 小数位可能很多
     * 点->点->点  点之前的距离相加
     *
     * @param gps  坐标系
     * @param unit 距离单位
     * @return 距离
     */
    public Double computeDistanceWithPoints(@NonNull DistanceUnit unit, @NonNull Gps... gps) {
        BigDecimalPlus of = BigDecimalPlus.of(0);
        Gps now = this;
        for (Gps gp : gps) {
            of.add(now.computeDistance(gp, unit));
            now = gp;
        }
        return of.doubleValue();
    }

    /**
     * 计算坐标点距离 小数位可能很多
     *
     * @param gps  坐标系
     * @param unit 距离单位
     * @return 距离
     */
    public Double computeDistance(@NonNull Gps gps, @NonNull DistanceUnit unit) {
        //转换为同一个坐标系
        if (gps.coordinateType.equals(this.coordinateType)) {
            return Math.abs(CoordinateUtil.distance(gps.latitude, gps.longitude, this.latitude, this.longitude, unit));
        } else {
            WGS84 d2 = gps.convertToWGS84();
            WGS84 d1 = this.convertToWGS84();
            return Math.abs(CoordinateUtil.distance(d1.latitude, d1.longitude, d2.latitude, d2.longitude, unit));
        }
    }

    /**
     * 计算坐标点距离
     *
     * @param gps             坐标系
     * @param unit            距离单位
     * @param convertFunction 转换函数
     * @return 距离
     */
    public <T> T computeDistance(@NonNull Gps gps, @NonNull DistanceUnit unit, Function<Double, T> convertFunction) {
        //转换为同一个坐标系
        Double aDouble = this.computeDistance(gps, unit);
        return convertFunction.apply(aDouble);
    }

    /**
     * 计算坐标点距离 并保留两位小数
     *
     * @param gps  坐标系
     * @param unit 距离单位
     * @return 距离
     */
    public Double computeDistanceWith2Scale(@NonNull Gps gps, @NonNull DistanceUnit unit) {
        //转换为同一个坐标系
        Double aDouble = this.computeDistance(gps, unit);
        return aDouble == null ? null : BigDecimal.valueOf(aDouble).setScale(2, RoundingMode.DOWN).doubleValue();
    }


    @Override
    public String toString() {
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
                .add("longitude=" + longitude)
                .add("latitude=" + latitude)
                .add("coordinateType=" + coordinateType)
                .toString();
    }
}
