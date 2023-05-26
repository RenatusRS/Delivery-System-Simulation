package rs.etf.sab.student.utils;

import java.util.ArrayList;
import java.util.HashMap;

public class SelectResult extends ArrayList<HashMap<String, Object>> {
	public Object get(int index, String column) {
		return this.get(index).get(column);
	}
	
	public Object get(String column) {
		return this.get(0).get(column);
	}
}
