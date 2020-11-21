package cn.minsin.core.web.template.service;

/**
 * @author: minton.zhang
 * @since: 2020/11/21 22:03
 */
public interface SelectPage<C, R> {

	/**
	 * 按照条件分页查询
	 *
	 * @param condition
	 * @return
	 */
	R doSelectPage(C condition);
}
