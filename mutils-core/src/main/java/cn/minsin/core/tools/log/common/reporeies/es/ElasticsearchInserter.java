package cn.minsin.core.tools.log.common.reporeies.es;

import cn.minsin.core.tools.IOUtil;
import cn.minsin.core.tools.log.common.LoggerHelperConfig;
import cn.minsin.core.tools.log.common.reporeies.ErrorReporter;
import cn.minsin.core.tools.log.common.reporeies.es.func.SaveRequestConvertFunction;
import cn.minsin.core.tools.log.common.reporeies.es.header.HttpRequestHeader;
import cn.minsin.core.tools.log.common.reporeies.es.request.BaseSaveRequest;
import cn.minsin.core.tools.tuple.Tuple2;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.Getter;

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
public class ElasticsearchInserter extends ErrorReporter {

    public static final List<Integer> SUCCESS_CODES = Lists.newArrayList(200, 201, 203);
    private final ElasticsearchLoggerConfig elasticConfig;
    private final SaveRequestConvertFunction<BaseSaveRequest> saveRequestConvertFunction;

    public ElasticsearchInserter(
            ElasticsearchLoggerConfig config,
            LoggerHelperConfig logHelperConfig,
            SaveRequestConvertFunction<BaseSaveRequest> saveRequestConvertFunction
    ) {
        super(logHelperConfig);
        this.elasticConfig = config;
        this.saveRequestConvertFunction = saveRequestConvertFunction;
    }


    protected URL createUrl(String url, String indexName, String indexType) throws MalformedURLException {

        if (url.endsWith("/")) {
            return new URL(url + indexName + "/" + indexType);
        }
        return new URL(url + "/" + indexName + "/" + indexType);


    }

    @Override
    protected void doPushLogic(Throwable throwable, String errorMsg, String errorDetail) throws Exception {

        Tuple2<String, BaseSaveRequest> apply = saveRequestConvertFunction.apply(throwable);

        String index = apply.getT1();

        URL url = this.createUrl(elasticConfig.getUrl(), index, elasticConfig.getIndexType());
        HttpURLConnection urlConnection = (HttpURLConnection) (url.openConnection());
        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(true);
        urlConnection.setReadTimeout(elasticConfig.getReadTimeout());
        urlConnection.setConnectTimeout(elasticConfig.getConnectTimeout());
        urlConnection.setRequestMethod("POST");
        try {
            String body = JSON.toJSONString(apply.getT2()).concat("\n");

            for (HttpRequestHeader header : elasticConfig.getHeaders()) {
                urlConnection.setRequestProperty(header.getKey(), header.getValue());
            }
            if (elasticConfig.getAuthentication() != null) {
                elasticConfig.getAuthentication().addAuth(urlConnection, body);
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


