package io.github.byzatic.pqletta.p_q_leta.impl.configuration_dao.dto;

import com.google.gson.annotations.SerializedName;

public class Label {

	@SerializedName("sign")
	private String sign;

	@SerializedName("value")
	private String value;

	@SerializedName("key")
	private String key;

	public String getSign(){
		return sign;
	}

	public String getValue(){
		return value;
	}

	public String getKey(){
		return key;
	}
}