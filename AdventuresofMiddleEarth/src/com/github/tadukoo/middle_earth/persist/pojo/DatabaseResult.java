package com.github.tadukoo.middle_earth.persist.pojo;

import com.github.tadukoo.util.StringUtil;

public record DatabaseResult<Type>(Type result, String errorMessage){
	
	public boolean success(){
		return StringUtil.isBlank(errorMessage);
	}
}
