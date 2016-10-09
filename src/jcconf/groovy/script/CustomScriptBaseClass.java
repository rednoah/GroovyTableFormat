package jcconf.groovy.script;

import java.time.LocalDate;

import groovy.lang.MissingPropertyException;
import groovy.lang.Script;
import jcconf.groovy.model.SimpleDate;

public abstract class CustomScriptBaseClass extends Script {

	@Override
	public Object getProperty(String property) {
		try {
			return super.getProperty(property);
		} catch (MissingPropertyException e) {
			// fake values for undefined variables
			return "UNDEFINED VARIABLE: " + property;
		}
	}

	public SimpleDate getToday() {
		return new SimpleDate(LocalDate.now());
	}

}
