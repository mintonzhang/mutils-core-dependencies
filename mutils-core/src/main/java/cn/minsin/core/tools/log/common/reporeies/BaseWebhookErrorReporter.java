package cn.minsin.core.tools.log.common.reporeies;

import cn.minsin.core.tools.log.common.LoggerHelperConfig;
import lombok.Getter;

/**
 * @author minton.zhang
 * @since 2021/11/9 09:38
 */
@Getter
public abstract class BaseWebhookErrorReporter extends ErrorReporter {

    private final String webhookUrl;

    private final String secretKey;


    public BaseWebhookErrorReporter(LoggerHelperConfig logHelperConfig, String webhookUrl, String secretKey) {
        super(logHelperConfig);
        this.webhookUrl = webhookUrl;
        this.secretKey = secretKey;
    }
}
