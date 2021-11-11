package cn.minsin.core.tools.log.common.reporeies.es.header;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author minton.zhang
 * @since 2021/7/20 11:13
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HttpRequestHeader {

    private String key;

    private String value;
}
