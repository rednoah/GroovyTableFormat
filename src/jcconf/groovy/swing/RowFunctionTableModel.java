package jcconf.groovy.swing;

import java.util.function.Function;

import javax.swing.table.AbstractTableModel;

public class RowFunctionTableModel extends AbstractTableModel {

	private Object[] data;
	private Function<Object, Object[]> rowFunction;

	private Object[][] results;

	public RowFunctionTableModel(Object[] data, Function<Object, Object[]> rowFunction) {
		this.data = data;
		this.rowFunction = rowFunction;
		this.results = new Object[data.length][];
	}

	public Object[] getRowValues(int row) {
		if (results[row] == null) {
			try {
				results[row] = rowFunction.apply(data[row]);
			} catch (Exception e) {
				results[row] = new Object[] { e };
			}
		}
		return results[row];
	}

	@Override
	public int getRowCount() {
		return results.length;
	}

	@Override
	public int getColumnCount() {
		return getRowCount() > 0 ? getRowValues(0).length : 0;
	}

	@Override
	public Object getValueAt(int row, int column) {
		try {
			return getRowValues(row)[column];
		} catch (Exception e) {
			return e;
		}
	}

}
