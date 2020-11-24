package cn.minsin.core.web.template.feign;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.io.Serializable;

/**
 * @author: minton.zhang
 * @since: 2020/11/23 16:06
 */
//@FeignClient(name = "ck-user-service",path = "/baseProvince",configuration = FeignConfiguration.class)
//@Headers({"Content-Type: application/json", "Accept: application/json"})
public interface BaseFeignClient<R_ID_REQUEST,
		PAGE_SEARCH_REQUEST,
		PAGE_RESULT,
		ONE_RESULT,
		SAVE_REQUEST> {

	@ApiOperation("新增或修改 根据id判断")
	@PostMapping("/save")
	Serializable doSave(@RequestBody @Valid SAVE_REQUEST save);

	@ApiOperation("按照id删除")
	@PostMapping("/delete")
	Serializable doDelete(@RequestBody @Valid R_ID_REQUEST delete);

	@ApiOperation("查询分页列表")
	@PostMapping("/page")
	PAGE_RESULT doSelectPage(@RequestBody @Valid PAGE_SEARCH_REQUEST list);

	@ApiOperation("根据id查询")
	@PostMapping("/get")
	ONE_RESULT doSelectOne(@RequestBody @Valid R_ID_REQUEST one);
}
