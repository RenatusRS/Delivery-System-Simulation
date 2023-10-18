package rs.etf.sab.student;

import rs.etf.sab.student.implementations.CityOperationsImpl;
import rs.etf.sab.student.utils.Logger;

import java.util.List;


class CityOperations extends CityOperationsImpl {
    
    @Override
    public int createCity(String name) {
        Logger.functionStart("CityOperations createCity(name: " + name + ")");
        
        int result = super.createCity(name);
    
        Logger.functionEnd(result);
        
        return result;
    }
    
    @Override
    public List<Integer> getCities() {
        Logger.functionStart("CityOperations getCities()");
        
        List<Integer> result = super.getCities();
    
        Logger.functionEnd(result);
        
        return result;
    }
    
    @Override
    public int connectCities(int cityId1, int cityId2, int distance) {
        Logger.functionStart("CityOperations connectCities(cityId1: " + cityId1 + ", cityId2: " + cityId2 + ", distance: " + distance + ")");
        
        int result = super.connectCities(cityId1, cityId2, distance);
    
        Logger.functionEnd(result);
        
        return result;
    }
    
    @Override
    public List<Integer> getConnectedCities(int cityId) {
        Logger.functionStart("CityOperations getConnectedCities(cityId: " + cityId + ")");
        
        List<Integer> result = super.getConnectedCities(cityId);
    
        Logger.functionEnd(result);
        
        return result;
    }
    
    @Override
    public List<Integer> getShops(int cityId) {
        Logger.functionStart("CityOperations getShops(cityId: " + cityId + ")");
        
        List<Integer> result = super.getShops(cityId);
    
        Logger.functionEnd(result);
        
        return result;
    }
    
}