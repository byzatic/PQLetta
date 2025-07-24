package io.github.byzatic.pqletta.p_q_leta.impl.configuration_dao.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QueryDescription {

	@SerializedName("identifier")
	private String identifier;

	@SerializedName("lower_limit")
	private String lowerLimit;

	@SerializedName("time_range")
	private String timeRange;

	@SerializedName("upper_limit")
	private String upperLimit;

	@SerializedName("step")
	private String step;

	@SerializedName("type")
	private String type;

	@SerializedName("labels")
	private List<Label> labels;

	public String getIdentifier(){
		return identifier;
	}

	public String getLowerLimit(){
		return lowerLimit;
	}

	public String getTimeRange(){
		return timeRange;
	}

	public String getUpperLimit(){
		return upperLimit;
	}

	public String getStep(){
		return step;
	}

	public String getType(){
		return type;
	}

	public List<Label> getLabels(){
		return labels;
	}
}