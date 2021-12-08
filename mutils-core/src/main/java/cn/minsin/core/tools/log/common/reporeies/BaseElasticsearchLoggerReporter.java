package cn.minsin.core.tools.log.common.reporeies;

import cn.minsin.core.tools.IOUtil;
import cn.minsin.core.tools.log.common.BaseJsonObjectReportRequest;
import cn.minsin.core.tools.log.common.reporeies.es.ElasticsearchLoggerConfig;
import com.google.common.collect.Lists;
import lombok.Getter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author minton.zhang
 * @since 2021/7/20 10:22
 */
@Getter
public abstract class BaseElasticsearchLoggerReporter extends BaseErrorReporter {

    public static final List<Integer> SUCCESS_CODES = Lists.newArrayList(200, 201, 203);
    private final ElasticsearchLoggerConfig elasticConfig;

    public BaseElasticsearchLoggerReporter(ElasticsearchLoggerConfig config) {
        this.elasticConfig = config;
    }


    protected URL createUrl(String url, String indexName, String indexType) throws MalformedURLException {

        if (url.endsWith("/")) {
            return new URL(url + indexName + "/" + indexType);
        }
        return new URL(url + "/" + indexName + "/" + indexType);


    }


    @Override
    protected void doPushLogicForJsonData(BaseJsonObjectReportRequest jsonObject) throws Exception {
        this.pushingToElastic(jsonObject.toJsonString(), false);
    }

    protected void pushingToElastic(String jsonData, boolean isError) throws IOException {
        String indexName;
        if (isError) {
            indexName = elasticConfig.getIndexNameErrorConvert().apply(elasticConfig.getIndexName());
        } else {
            indexName = elasticConfig.getIndexNameNormalConvert().apply(elasticConfig.getIndexName());
        }
        URL url = this.createUrl(elasticConfig.getUrl(), indexName, elasticConfig.getIndexType());
        HttpURLConnection urlConnection = (HttpURLConnection) (url.openConnection());
        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(true);
        urlConnection.setReadTimeout(elasticConfig.getReadTimeout());
        urlConnection.setConnectTimeout(elasticConfig.getConnectTimeout());
        urlConnection.setRequestMethod("POST");
        try {
            String body = jsonData.concat("\n");

            elasticConfig.getHeaders().forEach(urlConnection::setRequestProperty);

            if (elasticConfig.getAuthentication() != null) {
                elasticConfig.getAuthentication().authentication(urlConnection, body);
            }
            Writer writer = new OutputStreamWriter(urlConnection.getOutputStream(), StandardCharsets.UTF_8);
            writer.write(body);
            writer.flush();
            writer.close();

            int rc = urlConnection.getResponseCode();
            if (!SUCCESS_CODES.contains(rc)) {
                String data = IOUtil.readAllBytes2String(urlConnection.getErrorStream());
                logger.error("数据添加到elasticsearch失败,返回状态码:[{}],错误消息:[{}]", rc, data);
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}


