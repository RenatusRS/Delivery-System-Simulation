package rs.etf.sab.operations;

import java.util.List;

public interface CityOperations {
	int createCity(String paramString);
	
	List<Integer> getCities();
	
	int connectCities(int paramInt1, int paramInt2, int paramInt3);
	
	List<Integer> getConnectedCities(int paramInt);
	
	List<Integer> getShops(int paramInt);
}


/* Location:              D:\repos\SAB-Project\SAB_project_2223.jar!\rs\etf\sab\operations\CityOperations.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */