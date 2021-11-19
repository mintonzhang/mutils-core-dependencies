package cn.minsin.core.tools.log.common.reporeies;

import lombok.Getter;

/**
 * @author minton.zhang
 * @since 2021/11/9 09:38
 */
@Getter
public abstract class BaseWebhookErrorReporter extends BaseErrorReporter {

    private final String webhookUrl;

    private final String secretKey;


    public BaseWebhookErrorReporter(String webhookUrl, String secretKey) {
        this.webhookUrl = webhookUrl;
        this.secretKey = secretKey;
    }
}
