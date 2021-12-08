package cn.minsin.core.tools.log.common.reporeies.es;

import cn.minsin.core.tools.log.common.reporeies.es.authentication.Authentication;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author minton.zhang
 * @since 2021/10/20 16:52
 */
@Getter
@Setter
public class ElasticsearchLoggerConfig implements Cloneable {


    // eg http://localhost:9200/
    private String url;

    private String profile;

    private String indexType = "_doc";

    private int sleepTime = 250;
    private int maxRetries = 3;
    private int connectTimeout = 30000;
    private int readTimeout = 30000;
    private Authentication authentication;
    private int maxQueueSize = 100 * 1024 * 1024;

    private Map<String, String> headers = new HashMap<>(2);

    private String indexName;

    /**
     * 正常index格式化函数
     */
    private Function<String, String> indexNameNormalConvert = name -> name + "-" + profile;

    /**
     * 异常index格式化函数
     */
    private Function<String, String> indexNameErrorConvert = name -> name + "-error-" + profile;


    {
        headers.put("Content-Type", "application/json");
    }

    public static ElasticsearchLoggerConfig createSetting(String url, String profile) {

        ElasticsearchLoggerConfig elasticSettings = new ElasticsearchLoggerConfig();
        elasticSettings.setUrl(url);
        elasticSettings.setProfile(profile);
        return elasticSettings;
    }

    public static ElasticsearchLoggerConfig createSetting(String url, Authentication authentication, String profile) {
        ElasticsearchLoggerConfig elasticSettings = new ElasticsearchLoggerConfig();
        elasticSettings.setUrl(url);
        elasticSettings.setProfile(profile);
        elasticSettings.setAuthentication(authentication);
        return elasticSettings;
    }

    @Override
    public ElasticsearchLoggerConfig clone() {
        try {
            return (ElasticsearchLoggerConfig) super.clone();
        } catch (Exception e) {
            throw new RuntimeException("无法复制setting", e);
        }
    }
}
