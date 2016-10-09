package jcconf.groovy;

import static java.awt.Font.*;
import static javax.swing.BorderFactory.*;

import java.awt.Color;
import java.awt.Font;
import java.util.prefs.Preferences;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import jcconf.groovy.script.TableFormat;
import jcconf.groovy.swing.DocumentCallback;
import jcconf.groovy.swing.RowFunctionTableModel;
import net.miginfocom.swing.MigLayout;

public class ScriptableTable {

	private RSyntaxTextArea editor = createEditor();
	private JTable table = new JTable();
	private JTextArea error = new JTextArea();

	private Object[] data;

	private Preferences prefs = Preferences.userNodeForPackage(getClass());

	public ScriptableTable(Object[] data) {
		this.data = data;

		editor.getDocument().addDocumentListener(new DocumentCallback(evt -> setScript(editor.getText().trim())));
		table.setRowHeight(30);
		table.setShowGrid(true);
		table.setGridColor(Color.LIGHT_GRAY);
		error.setVisible(false);
		error.setForeground(Color.RED);
		error.setFont(new Font(MONOSPACED, PLAIN, 12));

		// restore script
		editor.setText(prefs.get("script", "[n, s, e, t]"));
	}

	public void setScript(String script) {
		try {
			TableFormat rowFunction = new TableFormat(script);

			// test table format script
			rowFunction.apply(data[0]);

			prefs.put("script", script);

			table.setModel(new RowFunctionTableModel(data, rowFunction));
			error.setVisible(false);
			error.setText(null);
		} catch (Throwable e) {
			while (e.getCause() != null)
				e = e.getCause();

			error.setVisible(true);
			error.setText(e.getMessage());
		}

	}

	public RSyntaxTextArea createEditor() {
		RSyntaxTextArea editor = new RSyntaxTextArea(5, 50);
		editor.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_GROOVY);
		editor.setAutoscrolls(false);
		editor.setAnimateBracketMatching(false);
		editor.setAntiAliasingEnabled(true);
		editor.setAutoIndentEnabled(true);
		editor.setBracketMatchingEnabled(true);
		editor.setCloseCurlyBraces(true);
		editor.setClearWhitespaceLinesEnabled(true);
		editor.setCodeFoldingEnabled(false);
		editor.setHighlightSecondaryLanguages(false);
		editor.setRoundedSelectionEdges(false);
		editor.setTabsEmulated(false);
		editor.setFont(new Font(MONOSPACED, PLAIN, 18));
		return editor;
	}

	public JComponent createContentPane() {
		JComponent c = new JPanel(new MigLayout("fill", "", "[pref!][pref][grow]"));

		c.add(withTitledBorder("Format", new RTextScrollPane(editor, true)), "growx, wrap");
		c.add(withTitledBorder("Error", error), "hidemode 2, growx, wrap");
		c.add(withTitledBorder("Table", new JScrollPane(table)), "grow");

		return c;
	}

	private JComponent withTitledBorder(String title, JComponent c) {
		c.setBorder(createCompoundBorder(createTitledBorder(title), c.getBorder()));
		c.setOpaque(false);
		return c;
	}

}
