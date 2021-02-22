package cn.minsin.core.tools.coordinate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author minton.zhang
 * @since 2020/5/13 16:13
 */
@Getter
@RequiredArgsConstructor
public enum CoordinateType {

    WGS84("地球坐标系，国际通用坐标系"),
    GCJ02("火星坐标系，WGS84坐标系加密后的坐标系；Google国内地图、高德、QQ地图 使用"),
    BD09("百度坐标系，GCJ02坐标系加密后的坐标系");

    private final String desc;
}
