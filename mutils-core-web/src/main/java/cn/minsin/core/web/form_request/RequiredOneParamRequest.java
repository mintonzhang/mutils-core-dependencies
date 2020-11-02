package cn.minsin.core.web.form_request;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: minton.zhang
 * @since: 2020/6/23 18:57
 */
public class RequiredOneParamRequest<ID_TYPE extends Serializable> extends OneParamRequest<ID_TYPE> {

	@Override
	@NotNull
	public ID_TYPE getId() {
		return super.getId();
	}
}
