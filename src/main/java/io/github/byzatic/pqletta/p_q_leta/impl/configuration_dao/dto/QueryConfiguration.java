package io.github.byzatic.pqletta.p_q_leta.impl.configuration_dao.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QueryConfiguration {

	@SerializedName("server_description")
	private ServerDescription serverDescription;

	@SerializedName("query_description")
	private List<QueryDescription> queryDescription;

	public ServerDescription getServerDescription(){
		return serverDescription;
	}

	public List<QueryDescription> getQueryDescriptions(){
		return queryDescription;
	}
}