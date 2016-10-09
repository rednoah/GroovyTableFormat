package jcconf.groovy.model;

import java.io.Serializable;

import jcconf.groovy.script.Define;

public class Episode implements Serializable {

	protected String seriesName;
	protected Integer season;
	protected Integer episode;
	protected String title;

	// absolute episode number
	protected Integer absolute;

	// special number
	protected Integer special;

	// episode airdate
	protected SimpleDate airdate;

	// extended series metadata
	protected SeriesInfo seriesInfo;

	public Episode() {
		// used by deserializer
	}

	public Episode(Episode obj) {
		this(obj.seriesName, obj.season, obj.episode, obj.title, obj.absolute, obj.special, obj.airdate, obj.seriesInfo);
	}

	public Episode(String seriesName, Integer season, Integer episode, String title) {
		this(seriesName, season, episode, title, null, null, null, null);
	}

	public Episode(String seriesName, Integer season, Integer episode, String title, Integer absolute, Integer special, SimpleDate airdate, SeriesInfo seriesInfo) {
		this.seriesName = seriesName;
		this.season = season;
		this.episode = episode;
		this.title = title;
		this.absolute = absolute;
		this.special = special;
		this.airdate = airdate;
		this.seriesInfo = seriesInfo;
	}

	@Define("n")
	public String getSeriesName() {
		return seriesName;
	}

	@Define("e")
	public Integer getEpisode() {
		return episode;
	}

	@Define("s")
	public Integer getSeason() {
		return season;
	}

	@Define("t")
	public String getTitle() {
		return title;
	}

	@Define("i")
	public Integer getAbsolute() {
		return absolute;
	}

	@Define("special")
	public Integer getSpecial() {
		return special;
	}

	@Define("airdate")
	public SimpleDate getAirdate() {
		return airdate;
	}

	@Define("series")
	public SeriesInfo getSeriesInfo() {
		return seriesInfo;
	}

}
