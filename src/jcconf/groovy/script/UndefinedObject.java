package jcconf.groovy.script;

import groovy.lang.GroovyObjectSupport;

/**
 * TODO Intercept method calls and properties at runtime
 */
public class UndefinedObject extends GroovyObjectSupport {

	private UndefinedObject parent;
	private String name;

	public UndefinedObject(String name) {
		this.parent = null;
		this.name = name;
	}

	private UndefinedObject(UndefinedObject parent, String name) {
		this.parent = parent;
		this.name = name;
	}

	@Override
	public Object getProperty(String property) {
		return new UndefinedObject(this, property);
	}

	@Override
	public Object invokeMethod(String method, Object args) {
		return new UndefinedObject(this, method + "()");
	}

	@Override
	public String toString() {
		return parent == null ? "Undefined: " + name : parent.toString() + "." + name;
	}

}
