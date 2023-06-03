package rs.etf.sab.student.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Result extends ArrayList<Entry> {
	public Object get(int index, String column) {
		return this.get(index).get(column);
	}
	
	public Object get(String column) {
		return this.get(0).get(column);
	}
	
	public Object getAll(String column) {
		List<Object> values = new ArrayList<>();
		
		for (Entry row : this) {
			values.add(row.get(column));
		}
		
		return values;
	}
	
	@Override
	public String toString() {
		if (this.size() == 0) {
			return "Empty result set";
		}
		
		HashMap<String, Integer> lengths = new HashMap<>();
		
		for (String column : this.get(0).keySet()) {
			lengths.put(column, column.length());
		}
		
		for (Entry row : this) {
			for (String column : row.keySet()) {
				if (row.get(column) == null) {
					continue;
				}
				
				lengths.put(column, Math.max(lengths.get(column), row.get(column).toString().length()));
			}
		}
		
		StringBuilder string = new StringBuilder();
		
		// Top border
		string.append("┌");
		for (String column : this.get(0).keySet()) {
			int columnLength = lengths.get(column);
			string.append("─".repeat(columnLength + 2)).append("┬");
		}
		string.deleteCharAt(string.length() - 1); // Remove the last unnecessary "┬"
		string.append("┐").append("\n");
		
		// Header
		for (String column : this.get(0).keySet()) {
			string.append("│ ").append(String.format("%-" + lengths.get(column) + "s", column)).append(" ");
		}
		string.append("│").append("\n");
		
		// Middle border
		string.append("├");
		for (String column : this.get(0).keySet()) {
			int columnLength = lengths.get(column);
			string.append("─".repeat(columnLength + 2)).append("┼");
		}
		string.deleteCharAt(string.length() - 1); // Remove the last unnecessary "┼"
		string.append("┤").append("\n");
		
		// Rows
		for (Entry row : this) {
			for (String column : row.keySet()) {
				string.append("│ ").append(String.format("%-" + lengths.get(column) + "s", row.get(column))).append(" ");
			}
			string.append("│").append("\n");
		}
		
		// Bottom border
		string.append("└");
		for (String column : this.get(0).keySet()) {
			int columnLength = lengths.get(column);
			string.append("─".repeat(columnLength + 2)).append("┴");
		}
		string.deleteCharAt(string.length() - 1); // Remove the last unnecessary "┴"
		string.append("┘");
		
		return string.toString();
	}

}
