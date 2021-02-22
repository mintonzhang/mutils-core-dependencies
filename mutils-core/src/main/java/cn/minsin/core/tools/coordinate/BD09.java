package cn.minsin.core.tools.coordinate;

/**
 * @author minton.zhang
 * @since 2020/5/13 16:18
 */
public class BD09 extends Gps {


    public BD09(double longitude, double latitude) {
        super(longitude, latitude, CoordinateType.BD09);
    }

    @Override
    protected BD09 convertToBD09() {
        return this;
    }

    @Override
    public GCJ02 convertToGCJ02() {
        double[] doubles = CoordinateUtil.transformBD09ToGCJ02(super.longitude, super.latitude);
        return new GCJ02(doubles[0], doubles[1]);
    }

    @Override
    public WGS84 convertToWGS84() {
        double[] doubles = CoordinateUtil.transformBD09ToWGS84(super.longitude, super.latitude);
        return new WGS84(doubles[0], doubles[1]);
    }

}
