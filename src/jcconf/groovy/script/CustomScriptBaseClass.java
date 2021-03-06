package jcconf.groovy.script;

import static java.util.Arrays.*;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDate;

import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;

import groovy.lang.Closure;
import groovy.lang.MissingPropertyException;
import groovy.lang.Script;
import groovy.xml.MarkupBuilder;
import jcconf.groovy.Main;
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

			// TODO 3.2 Use Null Object pattern for non-existing properties instead of null or throwing an MissingPropertyException
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

	/**
	 * e.g. hasEpisode{ it.season == s && it.episode > e} ? null : 'Season Finale'
	 */
	public boolean hasEpisode(Closure<?> condition) throws Exception {
		Object[] array = Main.getSampleData();

		// TODO 5.1 DefaultGroovyMethods: Call default Groovy methods: e.g. episodes.find{ s == 6 && e == 10 }
		return DefaultGroovyMethods.find(array, condition) != null;
	}

	public File getHome() throws IOException {
		return new File(".").getCanonicalFile();
	}

	/**
	 * e.g. check(home) { it.name.contains 'Groovy' } { it.name.contains 'Table' }
	 */
	public boolean check(File file, Closure<?>... closures) {
		return stream(closures).allMatch(closure -> {
			// TODO 4.2 Cast closure to Java interface
			FileFilter filter = (FileFilter) DefaultTypeTransformation.castToType(closure, FileFilter.class);
			return filter.accept(file);
		});
	}

	/**
	 * e.g. XML{ episode(id:i) { name(n); title(t) } }
	 */
	public String XML(Closure<?> closure) {
		StringWriter buffer = new StringWriter();
		MarkupBuilder builder = new MarkupBuilder(buffer);

		// TODO 5.2 Closure.rehydrate(): Call closure in MarkupBuilder context
		closure.rehydrate(closure.getDelegate(), builder, builder).call();

		return buffer.toString();
	}

}
