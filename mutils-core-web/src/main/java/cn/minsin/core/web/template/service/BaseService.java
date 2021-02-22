package cn.minsin.core.web.template.service;

import cn.minsin.core.web.exception.BusinessException;

import java.io.Serializable;

/**
 * 新增 SAVE
 * 删除和按照id查询 RequiredOneParamRequest
 * 分页查询 PAGE
 *
 * @author minton.zhang
 * @since 2020/11/21 21:59
 */
public interface BaseService<
		R_ID_REQUEST,
		PAGE_SEARCH_REQUEST,
		PAGE_RESULT,
		ONE_RESULT,
		SAVE_REQUEST
		>
		extends
		Delete<R_ID_REQUEST>,
		SelectOne<R_ID_REQUEST, ONE_RESULT>,
		SelectPage<PAGE_SEARCH_REQUEST, PAGE_RESULT>,
		Save<SAVE_REQUEST> {


	@Override
	default Serializable doDelete(R_ID_REQUEST id_type) {
		throw new BusinessException("不支持该操作");
	}

	@Override
	default Serializable doSave(SAVE_REQUEST save_request) {
		throw new BusinessException("不支持该操作");

	}

	@Override
	default ONE_RESULT doSelectOne(R_ID_REQUEST id_type) {
		throw new BusinessException("不支持该操作");

	}

	@Override
	default PAGE_RESULT doSelectPage(PAGE_SEARCH_REQUEST condition) {
		throw new BusinessException("不支持该操作");

	}
}
