package rs.etf.sab.student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import rs.etf.sab.operations.CityOperations;
import rs.etf.sab.student.utils.SelectResult;
import rs.etf.sab.student.utils.DB;
import rs.etf.sab.student.utils.Where;


class CityOperationsImpl implements CityOperations {
    
    @Override
    public int createCity(String name) {
        return DB.insert("City", new HashMap<>() {{
            put("Name", name);
        }});
    }
    
    @Override
    public List<Integer> getCities() {
        SelectResult cities = DB.select("City");
        
        ArrayList<Integer> cityIds = new ArrayList<>();
        
        for (HashMap<String, Object> row : cities) {
            cityIds.add((int) row.get("ID"));
        }
        
        return cityIds;
    }
    
    @Override
    public int connectCities(int cityId1, int cityId2, int distance) {
        return DB.insert("Connection", new HashMap<>() {{
            put("CityID1", cityId1);
            put("CityID2", cityId2);
            put("Distance", distance);
        }});
    }
    
    @Override
    public List<Integer> getConnectedCities(int cityId) {
        SelectResult cities = DB.select("Connection", new Where[][] {
                new Where[] { new Where("CityID1", "=", cityId) },
                new Where[] { new Where("CityID2", "=", cityId) }
        });
        
        ArrayList<Integer> cityIds = new ArrayList<>();
    
        for (HashMap<String, Object> row : cities) {
            cityIds.add((int) row.get("CityID1") == cityId ? (int) row.get("CityID2") : (int) row.get("CityID1"));
        }
        
        return cityIds;
    }
    
    @Override
    public List<Integer> getShops(int cityId) {
        SelectResult shops = DB.select("Shop", new Where("CityID", "=", cityId));
        
        ArrayList<Integer> shopIds = new ArrayList<>();
        
        for (HashMap<String, Object> shop : shops) {
            shopIds.add((int) shop.get("ID"));
        }
        
        return shopIds;
    }
    
}