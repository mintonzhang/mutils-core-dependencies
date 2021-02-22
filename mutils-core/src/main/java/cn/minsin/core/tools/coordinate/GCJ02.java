package cn.minsin.core.tools.coordinate;

/**
 * @author minton.zhang
 * @since 2020/5/13 16:18
 */
public class GCJ02 extends Gps {
    public GCJ02(double longitude, double latitude) {
        super(longitude, latitude, CoordinateType.GCJ02);
    }

    @Override
    public BD09 convertToBD09() {
        double[] doubles = CoordinateUtil.transformGCJ02ToBD09(super.longitude, super.latitude);
        return new BD09(doubles[0], doubles[1]);
    }

    @Override
    protected GCJ02 convertToGCJ02() {
        return this;
    }

    @Override
    public WGS84 convertToWGS84() {
        double[] doubles = CoordinateUtil.transformGCJ02ToWGS84(super.longitude, super.latitude);
        return new WGS84(doubles[0], doubles[1]);
    }

}
