package cn.minsin.core.tools.area;

import cn.minsin.core.exception.MutilsErrorException;
import cn.minsin.core.tools.HttpClientUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 中国地域信息 浙江省、四川省等等
 *
 * @author mintonzhang
 * @date 2019年4月5日
 * @since 0.3.7
 */
@Slf4j
public class ChinaAreaUtil {

    private static String remoteUrl = "http://datavmap-public.oss-cn-hangzhou.aliyuncs.com/areas/csv/";

    private static String defaultProvinceCode = "100000";

    private static String province = "${code}_province.json";

    private static String city = "${code}_city.json";

    private static String district = "${code}_district.json";

    private static String placeholder = "${code}";

    private final CloseableHttpClient client;

    private ChinaAreaUtil() {
        this.client = HttpClientUtil.getInstance();
    }

    public static ChinaAreaUtil init() {
        return new ChinaAreaUtil();
    }

    /**
     * 获取省列表
     *
     * @throws MutilsErrorException
     */
    protected List<AreaModel> initProvince() throws  IOException {
        HttpGet getMethod = HttpClientUtil.getGetMethod(remoteUrl + province.replace(placeholder, defaultProvinceCode));
        try (CloseableHttpResponse response = client.execute(getMethod)) {
            String jsonStr = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            JSONObject parseObject = JSON.parseObject(jsonStr);
            Object object = parseObject.get("rows");
            return JSON.parseArray(object.toString(), AreaModel.class);
        }
    }

    /**
     * 获取城市
     *
     * @param provinceCode
     * @throws MutilsErrorException
     */
    protected List<AreaModel> initCity(String provinceCode) throws IOException {
        HttpGet getMethod = HttpClientUtil.getGetMethod(remoteUrl + city.replace(placeholder, provinceCode));
        try (CloseableHttpResponse response = client.execute(getMethod)) {
            String jsonStr = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            JSONObject parseObject = JSON.parseObject(jsonStr);
            Object object = parseObject.get("rows");
            return JSON.parseArray(object.toString(), AreaModel.class);
        }
    }

    /**
     * 获取县市区
     *
     * @param cityCode
     * @throws MutilsErrorException
     */
    protected List<AreaModel> initDistrict(String cityCode) throws IOException {
        HttpGet getMethod = HttpClientUtil.getGetMethod(remoteUrl + district.replace(placeholder, cityCode));
        try (CloseableHttpResponse response = client.execute(getMethod)) {
            String jsonStr = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            JSONObject parseObject = JSON.parseObject(jsonStr);
            Object object = parseObject.get("rows");
            return JSON.parseArray(object.toString(), AreaModel.class);
        }
    }

    /**
     * 获取省市区的树状图，由于数据量大 可用于重设本地数据库
     *
     * @throws MutilsErrorException
     */
    public List<AreaModel> initProvinceWithChildren() throws IOException {
        List<AreaModel> initProvince = initProvince();
        for (AreaModel province : initProvince) {
            List<AreaModel> initCity = this.initCity(province.getAdcode());
            for (AreaModel city : initCity) {
                try {
                    List<AreaModel> initDistrict = this.initDistrict(city.getAdcode());
                    for (AreaModel temp : initDistrict) {
                        if (temp.getParent().equals(city.getName())) {
                            city.setChild(temp);
                        }
                    }
	                if (CollectionUtils.isEmpty(city.getChildren())) {
		                log.error("adcode为：{},name为{}，没有Children.", city.getAdcode(), city.getName());
	                }
                } catch (Exception e) {
                    log.error("adcode为：{},name为{}，丢失数据,已跳过.", city.getAdcode(), city.getName());
                }
            }
            province.setChildren(initCity);
        }
        return initProvince;
    }

	public static void main(String[] args) throws IOException {
		List<AreaModel> areaModels = init().initProvinceWithChildren();

		areaModels.forEach(System.out::println);
	}
}
