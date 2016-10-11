package jcconf.groovy.script;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ImportCustomizer;
import org.codehaus.groovy.jsr223.GroovyScriptEngineImpl;

import groovy.lang.GroovyClassLoader;

public class TableFormat implements Function<Object, Object[]> {

	private CompiledScript script;

	public TableFormat(String script) throws ScriptException {
		this.script = groovyScriptEngine.compile(script);
	}

	public Map<String, Object> getVariables(Object it) throws Exception {
		Map<String, Object> variables = new HashMap<String, Object>();

		variables.put("it", it);

		for (Method method : it.getClass().getMethods()) {
			Define define = method.getAnnotation(Define.class);

			if (define == null)
				continue;

			variables.put(define.value(), method.invoke(it));
		}

		return variables;
	}

	@Override
	public Object[] apply(Object it) {
		try {
			Object result = script.eval(new SimpleBindings(getVariables(it)));

			// convert any Collection to Object[]
			if (result instanceof Collection) {
				return ((Collection) result).toArray();
			}

			// singleton
			return new Object[] { result };
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static Compilable groovyScriptEngine = createScriptEngine();

	private static Compilable createScriptEngine() {
		CompilerConfiguration config = new CompilerConfiguration();

		// TODO Set ScriptBaseClass so we can deal with missing properties and add properties or functions to the script context
		config.setScriptBaseClass(CustomScriptBaseClass.class.getName());

		ImportCustomizer imports = new ImportCustomizer();

		// TODO Add static import so that we can use the static methods: e.g. any{e}{"Special $special"}
		imports.addStaticStars(CustomStaticImports.class.getName());
		imports.addStaticStars(Math.class.getName());

		config.addCompilationCustomizers(imports);

		GroovyClassLoader classLoader = new GroovyClassLoader(Thread.currentThread().getContextClassLoader(), config);
		return (Compilable) new GroovyScriptEngineImpl(classLoader);
	}

}
