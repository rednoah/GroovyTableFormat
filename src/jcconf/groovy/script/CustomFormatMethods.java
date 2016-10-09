package jcconf.groovy.script;

import static java.util.regex.Pattern.*;

import java.util.regex.Matcher;

import com.ibm.icu.text.Transliterator;

public class CustomFormatMethods {

	public static String getLower(String self) {
		return self.toLowerCase();
	}

	public static String getUpper(String self) {
		return self.toUpperCase();
	}

	public static String pad(String self, int length, String padding) {
		while (self.length() < length) {
			self = padding + self;
		}
		return self;
	}

	public static String pad(String self, int length) {
		return pad(self, length, "0");
	}

	public static String pad(Number self, int length) {
		return pad(self.toString(), length, "0");
	}

	public static String getAcronym(String self) {
		Matcher matcher = compile("(?<=[&()+.,-;<=>?\\[\\]_{|}~ ]|^)[\\p{Alnum}]").matcher(self);

		StringBuilder buffer = new StringBuilder();
		while (matcher.find()) {
			buffer.append(matcher.group());
		}

		return buffer.toString();
	}

	public static String getPinyin(String self) {
		return Transliterator.getInstance("Han-Latin").transform(self);
	}

}
