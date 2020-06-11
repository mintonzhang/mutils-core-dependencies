package cn.minsin.core.tools.coordinate;

/**
 * @author: minton.zhang
 * @since: 2020/5/13 16:18
 */
public class WGS84 extends Gps {

    public WGS84(double longitude, double latitude) {
        super(longitude, latitude, CoordinateType.WGS84);
    }

    @Override
    public BD09 convertToBD09() {
        double[] doubles = CoordinateUtil.transformWGS84ToBD09(super.longitude, super.latitude);
        return new BD09(doubles[0], doubles[1]);
    }

    @Override
    public GCJ02 convertToGCJ02() {
        double[] doubles = CoordinateUtil.transformWGS84ToGCJ02(super.longitude, super.latitude);
        return new GCJ02(doubles[0], doubles[1]);
    }

    @Override
    protected WGS84 convertToWGS84() {
        return this;
    }

}
