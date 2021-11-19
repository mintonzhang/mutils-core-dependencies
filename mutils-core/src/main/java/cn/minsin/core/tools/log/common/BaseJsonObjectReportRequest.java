package cn.minsin.core.tools.log.common;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.event.Level;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author minton.zhang
 * @since 2021/7/20 11:20
 */
@Getter
@Setter
public class BaseJsonObjectReportRequest {

    protected static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");

    private String threadName = Thread.currentThread().getName();

    private String dateTime = LocalDateTime.now().format(DATETIME_FORMATTER);

    private LocalDate date = LocalDate.now();

    private long timestamp = System.currentTimeMillis();

    private Level level = Level.INFO;

    private String errorStackTrace;

    private String errorMessage;


    public void parseErrorStack(Throwable throwable) {
        if (throwable != null) {
            this.errorStackTrace = ExceptionUtils.getStackTrace(throwable);
            this.errorMessage = throwable.getMessage();
        }
    }

    public String toJsonString() {
        return JSON.toJSONString(this);
    }

}
