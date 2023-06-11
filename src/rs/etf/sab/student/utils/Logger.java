package rs.etf.sab.student.utils;

import java.util.ArrayList;

public class Logger {
	private static final ArrayList<String> functions = new ArrayList<>();
	
	public static void functionStart(String function) {
		StringBuilder prefix = new StringBuilder();
		
		if (functions.isEmpty()) prefix.append("\n");
		
		functions.add(function);
		
		prefix.append("┌");
		prefix.append(" ─".repeat(functions.size() - 1));
		prefix.append(" ");
		
		System.out.println(prefix + function);
	}
	
	public static void functionEnd(Object result) {
		String prefix = "└" + " ─".repeat(functions.size() - 1) + " ";
		
		String function = functions.remove(functions.size() - 1);
		
		System.out.println(prefix + function + " -> " + result);
	}
}
