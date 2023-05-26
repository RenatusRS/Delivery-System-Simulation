package rs.etf.sab.student.utils;

public class Where {
	private final String column;
	private final String operator;
	private final String value;
	
	public Where(String column, String operator, int value) {
		this(column, operator, Integer.toString(value));
	}
	public Where(String column, String operator, String value) {
		if (column == null || column.isEmpty()) {
			throw new IllegalArgumentException("Column name cannot be null or empty.");
		}
		
		if (operator == null || operator.isEmpty()) {
			throw new IllegalArgumentException("Operator cannot be null or empty.");
		}
		
		if (value == null || value.isEmpty()) {
			throw new IllegalArgumentException("Value cannot be null or empty.");
		}
		
		if (!operator.equals("=") && !operator.equals("<>") && !operator.equals(">") && !operator.equals("<") && !operator.equals(">=") && !operator.equals("<=")) {
			throw new IllegalArgumentException("Operator must be one of the following: =, <>, >, <, >=, <=.");
		}
		
		this.column = column;
		this.operator = operator;
		this.value = value;
	}
	
	public String toString() {
		return column + " " + operator + " '" + value + "'";
	}
	
	public static String toString(Where[] wheres) {
		if (wheres == null || wheres.length == 0) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < wheres.length; i++) {
			sb.append(wheres[i]);
			
			if (i < wheres.length - 1) {
				sb.append(" AND ");
			}
		}
		
		return sb.toString();
	}
	
	public static String toString(Where[][] wheres) {
		if (wheres == null || wheres.length == 0) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < wheres.length; i++) {
			sb.append(toString(wheres[i]));
			
			if (i < wheres.length - 1) {
				sb.append(" OR ");
			}
		}
		
		return sb.toString();
	}
}
