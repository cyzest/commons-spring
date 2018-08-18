package io.github.cyzest.commons.spring.model;

import java.io.Serializable;

/**
 * Boolean Flag
 */
public enum BooleanFlag implements EnumCode, Serializable {

	YES("Y"),
	NO("N");

	private String code;

	private BooleanFlag(String code) {
		this.code = code;
	}

	public String getCode() {
			return code;
		}

}
