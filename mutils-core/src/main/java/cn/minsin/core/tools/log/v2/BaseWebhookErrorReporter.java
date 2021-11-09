package cn.minsin.core.tools.log.v2;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author minton.zhang
 * @since 2021/11/9 09:38
 */
@Getter
@RequiredArgsConstructor
public abstract class BaseWebhookErrorReporter extends ErrorReporter {

    private final String webhookUrl;

    private final String secretKey;

}
