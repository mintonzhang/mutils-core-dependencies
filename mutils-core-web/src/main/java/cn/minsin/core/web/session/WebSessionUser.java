package cn.minsin.core.web.session;

import cn.minsin.core.session.SessionUser;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author minton.zhang
 * @since 2021/7/29 16:48
 */
@Getter
@Setter
public class WebSessionUser implements SessionUser {

    /**
     * 用户id
     */
    private Serializable id;

    /**
     * 请求id
     */
    private Serializable requestId;
}
