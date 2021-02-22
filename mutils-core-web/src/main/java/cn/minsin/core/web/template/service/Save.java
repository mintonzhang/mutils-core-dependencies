package cn.minsin.core.web.template.service;

import java.io.Serializable;

/**
 * @author minton.zhang
 * @since 2020/11/21 22:04
 */
public interface Save<ID_REQUEST> {


	Serializable doSave(ID_REQUEST save);
}
