package rs.etf.sab.student.implementations;

import java.util.ArrayList;
import java.util.List;
import rs.etf.sab.operations.CityOperations;
import rs.etf.sab.student.utils.Entry;
import rs.etf.sab.student.utils.Result;
import rs.etf.sab.student.utils.DB;
import rs.etf.sab.student.utils.Where;


public class CityOperationsImpl implements CityOperations {
    
    @Override
    public int createCity(String name) {
        return DB.insert("City", new Entry() {{
            put("Name", name);
        }});
    }
    
    @Override
    public List<Integer> getCities() {
        Result cities = DB.select("City");
        
        return (List<Integer>) cities.getAll("CityID");
    }
    
    @Override
    public int connectCities(int cityId1, int cityId2, int distance) {
        return DB.insert("Connection", new Entry() {{
            put("CityID1", cityId1);
            put("CityID2", cityId2);
            put("Distance", distance);
        }});
    }
    
    @Override
    public List<Integer> getConnectedCities(int cityId) {
        Result cities = DB.select("Connection", new Where[][] {
                new Where[] { new Where("CityID1", "=", cityId) },
                new Where[] { new Where("CityID2", "=", cityId) }
        });
        
        ArrayList<Integer> cityIds = new ArrayList<>();
    
        for (Entry row : cities) {
            cityIds.add((int) row.get("CityID1") == cityId ? (int) row.get("CityID2") : (int) row.get("CityID1"));
        }
        
        return cityIds;
    }
    
    @Override
    public List<Integer> getShops(int cityId) {
        Result shops = DB.select("Shop", new Where("CityID", "=", cityId));
        
        return (List<Integer>) shops.getAll("ShopID");
    }
    
}