package jcconf.groovy;

import static java.util.Collections.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.apple.eawt.FullScreenUtilities;
import com.cedarsoftware.util.io.JsonReader;

import jcconf.groovy.model.Episode;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(Main::start);
	}

	public static void start() {
		try {
			setSystemLookAndFeel();

			ScriptableTable table = new ScriptableTable(getSampleData());

			JFrame window = new JFrame(table.getClass().getName());
			window.setContentPane(table.createContentPane());
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.setLocationByPlatform(true);
			window.setSize(800, 600);

			setWindowCanFullScreen(window);

			window.setVisible(true);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	private static void setSystemLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

			if ("Mac OS X".equals(System.getProperty("os.name"))) {
				UIManager.put("TitledBorder.border", UIManager.getBorder("InsetBorder.aquaVariant"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void setWindowCanFullScreen(JFrame window) {
		try {
			if ("Mac OS X".equals(System.getProperty("os.name"))) {
				FullScreenUtilities.setWindowCanFullScreen(window, true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Object[] getSampleData() throws IOException {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put(JsonReader.TYPE_NAME_MAP, singletonMap(Episode.class.getName(), Episode.class.getSimpleName()));

		try (InputStream in = Main.class.getResourceAsStream("SampleData.json")) {
			return (Object[]) JsonReader.jsonToJava(in, options);
		}
	}

}