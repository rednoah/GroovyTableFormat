package jcconf.groovy.script;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;

import groovy.lang.Closure;

public class CustomStaticImports {

	public static Object any(Object c1, Object c2, Object... cN) {
		return stream(c1, c2, cN).findFirst().orElse(null);
	}

	public static List<Object> allOf(Object c1, Object c2, Object... cN) {
		return stream(c1, c2, cN).collect(toList());
	}

	private static Stream<Object> stream(Object c1, Object c2, Object... cN) {
		return Stream.concat(Stream.of(c1, c2), Stream.of(cN)).map(CustomStaticImports::getResult).filter(Objects::nonNull);
	}

	private static Object getResult(Object object) {
		if (object instanceof Closure<?>) {
			try {
				object = ((Closure<?>) object).call();
			} catch (Exception e) {
				return null;
			}
		}

		// TODO Groovy Truth: object as boolean
		if (DefaultTypeTransformation.castToBoolean(object)) {
			return object;
		}

		return null;
	}

}
