package jcconf.groovy.swing;

import java.util.function.Consumer;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class DocumentCallback implements DocumentListener {

	private Consumer<DocumentEvent> consumer;

	public DocumentCallback(Consumer<DocumentEvent> consumer) {
		this.consumer = consumer;
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		consumer.accept(e);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		consumer.accept(e);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		consumer.accept(e);
	}

}
