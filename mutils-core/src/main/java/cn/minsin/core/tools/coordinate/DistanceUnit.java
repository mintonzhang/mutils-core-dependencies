package cn.minsin.core.tools.coordinate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author: minton.zhang
 * @since: 2020/5/13 16:48
 */
@Getter
@RequiredArgsConstructor
public enum DistanceUnit {
    KM("千米"),
    M("米");

    private final String desc;
}
