package cn.minsin.core.web.template.controller;

import cn.minsin.core.web.template.service.BaseService;
import io.swagger.annotations.ApiOperation;
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
		SAVE_REQUEST> {

	protected T service;

	/**
	 * 为service赋值
	 *
	 * @param service
	 */
	public abstract void setService(T service);


	@ApiOperation("新增或修改 传入id为修改 未传入为新增")
	@PostMapping("/save")
	public Serializable doSave(@RequestBody @Valid SAVE_REQUEST save) {
		return service.doSave(save);
	}

	@ApiOperation("按照id删除")
	@PostMapping("/delete")
	public Serializable doDelete(@RequestBody @Valid R_ID_REQUEST delete) {
		return service.doDelete(delete);
	}

	@ApiOperation("查询分页列表")
	@PostMapping("/page")
	public PAGE_RESULT doSelectPage(@RequestBody @Valid PAGE_SEARCH_REQUEST list) {
		return service.doSelectPage(list);
	}

	@ApiOperation("根据id查询")
	@PostMapping("/get")
	public ONE_RESULT doSelectOne(@RequestBody @Valid R_ID_REQUEST one) {
		return service.doSelectOne(one);
	}
}
