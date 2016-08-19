package mapping;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * 
 * @author Sarah Bourgeois
 * @author Frederic Massart
 *
 *         Description : Propertiees of the update mapping for Geolocation
 */

public class MappingPropertiesGeoLocation {

	@JsonProperty("properties")
	private Properties propertiesElement;

	public MappingPropertiesGeoLocation() {
		this.propertiesElement = new Properties();
	}

	public Properties getPropertiesElement() {
		return propertiesElement;
	}

	public void setPropertiesElement(Properties propertiesElement) {
		this.propertiesElement = propertiesElement;
	}

	// =====================================
	// Inner class Geolocation mapping
	// =====================================

	// *****Properties ************
	public class Properties {
		private Name name;

		@JsonProperty("geoPoint")
		public Name getName() {
			return name;
		}

		public void setName(Name name) {
			this.name = name;
		}

		// ********* Name : Geopoint ***************
		public class Name {
			private String type;
			private boolean geohash;
			private boolean geohash_prefix;
			private int geohash_precision;

			public String getType() {
				return type;
			}

			public void setType(String type) {
				this.type = type;
			}

			public boolean isGeohash() {
				return geohash;
			}

			public void setGeohash(boolean geohash) {
				this.geohash = geohash;
			}

			public boolean isGeohash_prefix() {
				return geohash_prefix;
			}

			public void setGeohash_prefix(boolean geohash_prefix) {
				this.geohash_prefix = geohash_prefix;
			}

			public int getGeohash_precision() {
				return geohash_precision;
			}

			public void setGeohash_precision(int geohash_precision) {
				this.geohash_precision = geohash_precision;
			}

		} // End of name Geopoint

	} // End of class Properties

} // End of class
