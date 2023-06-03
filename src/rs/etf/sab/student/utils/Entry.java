package rs.etf.sab.student.utils;

import java.util.HashMap;

public class Entry extends HashMap<String, Object> {
	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		
		for (String key : this.keySet()) {
			string.append(key).append(": ").append(this.get(key)).append(", ");
		}
		
		return string.substring(0, string.length() - 2);
	}
}
