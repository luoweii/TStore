package com.luowei.tstore.service.message;

import com.tesool.kiread.utils.BeanUtil;

import java.io.Serializable;

public class BaseMsg implements Serializable {
	@Override
	public String toString() {
		return BeanUtil.bean2string(this);
	}
}