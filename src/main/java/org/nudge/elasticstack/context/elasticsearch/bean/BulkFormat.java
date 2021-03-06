package org.nudge.elasticstack.context.elasticsearch.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Bulk JSON structure for ES bulk pushes.
 *
 * @author Sarah Bourgeois
 * @author Frederic Massart
 */
public class BulkFormat {

	@JsonProperty("index")
	private Index indexElement;

	public BulkFormat() {
		this.indexElement = new Index();
	}

	public Index getIndexElement() {
		return indexElement;
	}

	public void setIndexElement(Index indexElement) {
		this.indexElement = indexElement;
	}

	// -- Inner class Index
	public class Index {
		private String index;
		private String type;
		private String id;

		@JsonProperty("_index")
		public String getIndex() {
			String format = "yyyy-MM-dd";
			java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat(format);
			java.util.Date date = new java.util.Date();
			String dateFormater = formater.format(date);
			return index  + "-" + dateFormater;
			
		}

		public void setIndex(String index) {
			this.index = index;
		}

		@JsonProperty("_type")
		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		@JsonProperty("_id")
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}
	}
}
