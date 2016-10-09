package jcconf.groovy.model;

import static java.util.Arrays.*;
import static java.util.Collections.*;

import java.io.Serializable;
import java.util.List;

public class SeriesInfo implements Serializable {

	// request parameters
	protected String database;
	protected String order;
	protected String language;

	// series parameters
	protected Integer id;
	protected String name;
	protected String[] aliasNames;
	protected String certification;
	protected SimpleDate startDate;
	protected String[] genres;
	protected String network;
	protected Double rating;
	protected Integer ratingCount;
	protected Integer runtime;
	protected String status;

	public SeriesInfo() {
		// used by deserializer
	}

	public SeriesInfo(SeriesInfo other) {
		this.database = other.database;
		this.order = other.order;
		this.language = other.language;
		this.id = other.id;
		this.name = other.name;
		this.aliasNames = other.aliasNames;
		this.certification = other.certification;
		this.startDate = other.startDate;
		this.genres = other.genres;
		this.network = other.network;
		this.rating = other.rating;
		this.ratingCount = other.ratingCount;
		this.runtime = other.runtime;
		this.status = other.status;
	}

	public String getDatabase() {
		return database;
	}

	public String getOrder() {
		return order;
	}

	public Integer getId() {
		return id;
	}

	public String getLanguage() {
		return language;
	}

	public String getName() {
		return name;
	}

	public List<String> getAliasNames() {
		return aliasNames == null ? emptyList() : asList(aliasNames.clone());
	}

	public String getCertification() {
		return certification;
	}

	public SimpleDate getStartDate() {
		return startDate;
	}

	public List<String> getGenres() {
		return genres == null ? emptyList() : asList(genres.clone());
	}

	public String getNetwork() {
		return network;
	}

	public Double getRating() {
		return rating;
	}

	public Integer getRatingCount() {
		return ratingCount;
	}

	public Integer getRuntime() {
		return runtime;
	}

	public String getStatus() {
		return status;
	}

}
