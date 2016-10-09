package jcconf.groovy.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class SimpleDate implements Serializable {

	protected int year;
	protected int month;
	protected int day;

	public SimpleDate() {
		// used by deserializer
	}

	public SimpleDate(int year, int month, int day) {
		this.year = year;
		this.month = month;
		this.day = day;
	}

	public SimpleDate(LocalDate date) {
		this(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
	}

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}

	public String format(String pattern) {
		return DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH).format(toLocalDate());
	}

	public LocalDate toLocalDate() {
		return LocalDate.of(year, month, day);
	}

	@Override
	public String toString() {
		return String.format("%04d-%02d-%02d", year, month, day);
	}

}
