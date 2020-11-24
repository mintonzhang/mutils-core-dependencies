package cn.minsin.core.web.template.controller;

import cn.minsin.core.web.template.feign.BaseFeignClient;
import cn.minsin.core.web.template.service.BaseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.io.Serializable;

/**
 * @author: minton.zhang
 * @since: 2020/10/23 09:37
 */
public abstract class BaseController<T extends BaseService<
		R_ID_REQUEST,
		PAGE_SEARCH_REQUEST,
		PAGE_RESULT,
		ONE_RESULT,
		SAVE_REQUEST
		>,
		R_ID_REQUEST,
		PAGE_SEARCH_REQUEST,
		PAGE_RESULT,
		ONE_RESULT,
		SAVE_REQUEST> implements BaseFeignClient<R_ID_REQUEST,
		PAGE_SEARCH_REQUEST,
		PAGE_RESULT,
		ONE_RESULT,
		SAVE_REQUEST> {

	@Autowired
	protected T service;

	@Override
	@ApiOperation("新增或修改 传入id为修改 未传入为新增")
	@PostMapping("/save")
	public Serializable doSave(@RequestBody @Valid SAVE_REQUEST save) {
		return service.doSave(save);
	}

	@Override
	@ApiOperation("按照id删除")
	@PostMapping("/delete")
	public Serializable doDelete(@RequestBody @Valid R_ID_REQUEST delete) {
		return service.doDelete(delete);
	}

	@Override
	@ApiOperation("查询分页列表")
	@PostMapping("/page")
	public PAGE_RESULT doSelectPage(@RequestBody @Valid PAGE_SEARCH_REQUEST list) {
		return service.doSelectPage(list);
	}

	@Override
	@ApiOperation("根据id查询")
	@PostMapping("/get")
	public ONE_RESULT doSelectOne(@RequestBody @Valid R_ID_REQUEST one) {
		return service.doSelectOne(one);
	}
}
