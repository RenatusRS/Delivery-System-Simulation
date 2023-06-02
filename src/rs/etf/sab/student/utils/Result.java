package rs.etf.sab.student.utils;

import java.util.ArrayList;
import java.util.List;

public class Result extends ArrayList<Column> {
	public Object get(int index, String column) {
		return this.get(index).get(column);
	}
	
	public Object get(String column) {
		return this.get(0).get(column);
	}
	
	public Object getAll(String column) {
		List<Object> values = new ArrayList<>();
		
		for (Column row : this) {
			values.add(row.get(column));
		}
		
		return values;
	}
}
