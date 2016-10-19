package org.nudge.elasticstack.context.elasticsearch.json.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author : Sarah Bourgeois
 * @author : Frederic Massart
 */
public abstract class NudgeEvent {

	private String date;
	private String name;
	private long responseTime;
	private long count;
	private String type;
	private String transactionId;
	
	public NudgeEvent(String name, long responseTime, String date, long count, String type, String transactionId) {
		setName(name);
		this.date = date;
		this.responseTime = responseTime;
		this.count = count;
		this.type = type;
		this.transactionId=transactionId;
	}

	public String toString() {
		return " response-time :" + responseTime + "name = :" + name + "date = :" + date + "count :" + count + "transactionId =" + transactionId;
	}

	// =========================
	//  Getters and Setters
	// =========================
	
	@JsonProperty("@timestamp")
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@JsonProperty("transaction_name")
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@JsonProperty("transaction_responseTime")
	public long getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(long responseTime) {
		this.responseTime = responseTime;
	}

	@JsonProperty("transaction_count")
	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}
	
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionId() {
		return transactionId;
	}

}