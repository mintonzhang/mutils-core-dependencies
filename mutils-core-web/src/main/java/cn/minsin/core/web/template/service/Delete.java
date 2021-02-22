package cn.minsin.core.web.template.service;

import java.io.Serializable;

/**
 * @author minton.zhang
 * @since 2020/11/21 22:03
 */
public interface Delete<R_ID_REQUEST> {

	/**
	 * 按照id删除
	 *
	 * @param type
	 * @return
	 */
	Serializable doDelete(R_ID_REQUEST type);
}
