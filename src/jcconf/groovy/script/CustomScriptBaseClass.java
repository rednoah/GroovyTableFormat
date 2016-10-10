package jcconf.groovy.script;

import static java.util.Arrays.*;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.time.LocalDate;

import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;

import groovy.lang.Closure;
import groovy.lang.MissingPropertyException;
import groovy.lang.Script;
import jcconf.groovy.model.SimpleDate;

public abstract class CustomScriptBaseClass extends Script {

	@Override
	public Object getProperty(String property) {
		try {
			return super.getProperty(property);
		} catch (MissingPropertyException e) {
			// setSuppressMissingPropertyException true
			// x => throw MissingPropertyException: No such property: x
			if (!suppressMissingPropertyException)
				throw e;

			// setSuppressMissingPropertyException false
			// x => return Object => Undefined: x

			// TODO Use Null Object pattern for non-existing properties instead of null or throwing an MissingPropertyException
			return new UndefinedObject(property);
		}

	}

	private boolean suppressMissingPropertyException = true;

	public void setSuppressMissingPropertyException(boolean suppressMissingPropertyException) {
		this.suppressMissingPropertyException = suppressMissingPropertyException;
	}

	public SimpleDate getToday() {
		return new SimpleDate(LocalDate.now());
	}

	public File getHome() throws IOException {
		return new File(".").getCanonicalFile();
	}

	/**
	 * e.g. check(home) { it.name.contains 'Groovy' } { it.name.contains 'Table' }
	 */
	public boolean check(File file, Closure<?>... closures) {
		return stream(closures).allMatch(closure -> {
			// TODO Cast Closure to Java Interface
			FileFilter filter = (FileFilter) DefaultTypeTransformation.castToType(closure, FileFilter.class);
			return filter.accept(file);
		});
	}

}
