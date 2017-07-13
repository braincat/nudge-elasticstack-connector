package org.nudge.elasticstack.context.elasticsearch.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author Sarah Bourgeois
 * @author Frederic Massart
 */
public class EventSQL extends NudgeEvent {

	public EventSQL(String appId, String hostname, String timestamp, String name, long count, long responseTime, String transactionId) {
		super(appId, hostname, name, responseTime, timestamp, count, "sql", transactionId);
		this.setTransactionId(transactionId);
	}

	// ===========================
	// Getters and Setters
	// ===========================

	@Override
	@JsonProperty("sql_code")
	public String getName() {
		return super.getName();
	}

	public String getTransactionId() {
		return super.getTransactionId();
	}

	@JsonProperty("sql_count")
	public long getCount() {
		return super.getCount();
	}

	@JsonProperty("sql_responseTime")
	public long getResponseTime() {
		return super.getResponseTime();
	}

}