package rs.etf.sab.student.utils;

public class UtilityOperations {
	
	public static int getCityByName(String name) {
		Result city = DB.select("City", new Where("Name", "=", name));
		
		return city.isEmpty() ? -1 : (int) city.get(0).get("ID");
	}
}
