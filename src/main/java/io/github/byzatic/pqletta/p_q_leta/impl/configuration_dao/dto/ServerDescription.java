package io.github.byzatic.pqletta.p_q_leta.impl.configuration_dao.dto;

import com.google.gson.annotations.SerializedName;

public class ServerDescription{

	@SerializedName("ssl_verify")
	private String sslVerify;

	@SerializedName("url")
	private String url;

	public String getSslVerify(){
		return sslVerify;
	}

	public String getUrl(){
		return url;
	}
}