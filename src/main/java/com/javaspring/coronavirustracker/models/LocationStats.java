package com.javaspring.coronavirustracker.models;

public class LocationStats implements Comparable<LocationStats> {

	private String state;
	private String country;
	private String latitude;
	private String longitude;
	private int latestTotalCases;
	private int diffFromPrevDay;
	private int latestTotalDeadCases;
	private int diffDeadFromPrevDay;
	private int latestTotalRecoveredCases;
	private int diffRecoveredFromPrevDay;

	public int getDiffFromPrevDay() {
		return diffFromPrevDay;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LocationStats other = (LocationStats) obj;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;

		return true;
	}

	public void setDiffFromPrevDay(int diffFromPrevDay) {
		this.diffFromPrevDay = diffFromPrevDay;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getLatestTotalCases() {
		return latestTotalCases;
	}

	public void setLatestTotalCases(int latestTotalCases) {
		this.latestTotalCases = latestTotalCases;
	}

	@Override
	public String toString() {
		return "LocationStats{" + "state='" + state + '\'' + ", country='" + country + '\'' + ", latestTotalCases="
				+ latestTotalCases + '}';
	}

	@Override
	public int compareTo(LocationStats o) {
		// TODO Auto-generated method stub
		try {
			/*
			 * if (o.getCountry() != null && getCountry() != null && o.getState() != null &&
			 * getState() != null && o.getCountry().compareTo(getCountry()) == 0) return
			 * o.getLatestTotalCases() - getLatestTotalCases() +
			 * o.getCountry().compareTo(getCountry()) + o.getState().compareTo(getState());
			 * // return o.getLatestTotalCases() - getLatestTotalCases()+5000000; else
			 */
			return o.getLatestTotalCases() - getLatestTotalCases() /*
			 * + o.getCountry().compareTo(getCountry()) +
			 * o.getState().compareTo(getState())
			 */;
		} catch (Exception e) {
			return o.getLatestTotalCases() - getLatestTotalCases() + o.getCountry().compareTo(getCountry())
					+ o.getState().compareTo(getState());
			// TODO: handle exception
		}
		/*
		 * else if(o.getLatestTotalCases()<getLatestTotalCases()) return -1; return 0;
		 */
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public int getLatestTotalDeadCases() {
		return latestTotalDeadCases;
	}

	public void setLatestTotalDeadCases(int latestTotalDeadCases) {
		this.latestTotalDeadCases = latestTotalDeadCases;
	}

	public int getDiffDeadFromPrevDay() {
		return diffDeadFromPrevDay;
	}

	public void setDiffDeadFromPrevDay(int diffDeadFromPrevDay) {
		this.diffDeadFromPrevDay = diffDeadFromPrevDay;
	}

	public int getLatestTotalRecoveredCases() {
		return latestTotalRecoveredCases;
	}

	public void setLatestTotalRecoveredCases(int latestTotalRecoveredCases) {
		this.latestTotalRecoveredCases = latestTotalRecoveredCases;
	}

	public int getDiffRecoveredFromPrevDay() {
		return diffRecoveredFromPrevDay;
	}

	public void setDiffRecoveredFromPrevDay(int diffRecoveredFromPrevDay) {
		this.diffRecoveredFromPrevDay = diffRecoveredFromPrevDay;
	}
}
