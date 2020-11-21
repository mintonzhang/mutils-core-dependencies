package cn.minsin.core.web.template.service;

/**
 * @author: minton.zhang
 * @since: 2020/11/21 22:03
 */
public interface SelectOne<R_ID_REQUEST, RESULT> {

	/**
	 * 按照id删除
	 *
	 * @param type
	 * @return
	 */
	RESULT doSelectOne(R_ID_REQUEST type);
}
