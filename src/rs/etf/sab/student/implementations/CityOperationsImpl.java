package rs.etf.sab.student.implementations;

import rs.etf.sab.operations.CityOperations;
import rs.etf.sab.student.utils.DB;
import rs.etf.sab.student.utils.Entry;
import rs.etf.sab.student.utils.Result;
import rs.etf.sab.student.utils.Where;

import java.util.ArrayList;
import java.util.List;


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
        
        return cities.isEmpty() ? null : (List<Integer>) cities.getAll("CityID");
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
    
        return new ArrayList<>() {{
            for (Entry row : cities) {
                add((int) row.get("CityID1") == cityId ? (int) row.get("CityID2") : (int) row.get("CityID1"));
            }
        }};
    }
    
    @Override
    public List<Integer> getShops(int cityId) {
        Result shops = DB.select("Shop", new Where("CityID", "=", cityId));
        
        return shops.isEmpty() ? null : (List<Integer>) shops.getAll("ShopID");
    }
    
}