package jcconf.groovy.script;

import static java.util.regex.Pattern.*;

import java.util.Locale;

import com.ibm.icu.text.Transliterator;

/**
 * TODO 1.1 ExtensionModule: Add org.codehaus.groovy.runtime.ExtensionModule via java.util.ServiceLoader
 * 
 * @see META-INF/services/org.codehaus.groovy.runtime.ExtensionModule
 */
public class CustomExtensionMethods {

	public static String getLower(String self) {
		return self.toLowerCase(Locale.ENGLISH);
	}

	public static String getUpper(String self) {
		return self.toUpperCase(Locale.ENGLISH);
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

	// TODO 1.2 ExtensionModule: Add Number.pad(int) extension method
	public static String pad(Number self, int length) {
		return pad(self.toString(), length, "0");
	}

	// TODO 1.3 ExtensionModule: Add String.acronym extension method / property
	public static String getAcronym(String self) {
		return compile("\\B.|\\P{L}", UNICODE_CHARACTER_CLASS).matcher(self).replaceAll("");
	}

	// TODO 1.4 ExtensionModule: Add String.pinyin extension method / property
	public static String getPinyin(String self) {
		return Transliterator.getInstance("Han-Latin").transform(self);
	}

}
