package com.requestbeans;

import com.constants.MongoConstants;
import com.mongodb.BasicDBObject;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.json.JSONObject;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestBean {
	private String showName;
	private String channelName;
	private String showDate;
	private String distinctFieldName;

	public String getDistinctFieldName() {
		return distinctFieldName;
	}

	public void setDistinctFieldName(String distinctFieldName) {
		this.distinctFieldName = distinctFieldName;
	}
	public String getShowName() {
		return showName;
	}
	public void setShowName(String showName) {
		this.showName = showName;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getShowDate() {
		return showDate;
	}
	public void setShowDate(String showDate) {
		this.showDate = showDate;
	}
	
	@JsonCreator
	public RequestBean(){
		
	}
	
	public RequestBean(String showName, String channelName, String showDate, String distinctFieldName) {
		this.showName = showName;
		this.channelName = channelName;
		this.showDate = showDate;
		this.distinctFieldName =  distinctFieldName;
	}

	public BasicDBObject toSearchObject() {
		BasicDBObject searchQuery = new BasicDBObject();
		if(this.showName != null)  searchQuery.put(MongoConstants.showName, Pattern.compile(this.showName, CASE_INSENSITIVE));
		if(this.channelName != null) searchQuery.put(MongoConstants.channelName, Pattern.compile(this.channelName, CASE_INSENSITIVE));
		if(this.showDate != null) searchQuery.put(MongoConstants.showDate, Pattern.compile(this.showDate, CASE_INSENSITIVE));
		return searchQuery;
	}

	public JSONObject toJson() {
		JSONObject object = new JSONObject();
		object.put(MongoConstants.showName, this.showName);
		object.put(MongoConstants.channelName,this.channelName);
		object.put(MongoConstants.showDate, this.showDate);
		return object;
	}
}
