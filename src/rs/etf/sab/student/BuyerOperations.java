package rs.etf.sab.student;

import rs.etf.sab.student.implementations.BuyerOperationsImpl;
import rs.etf.sab.student.utils.Logger;

import java.math.BigDecimal;
import java.util.List;


class BuyerOperations extends BuyerOperationsImpl {
    
    @Override
    public int createBuyer(String name, int cityId) {
        Logger.functionStart("BuyerOperations createBuyer(name: " + name + ", cityId: " + cityId + ")");
        
        int result = super.createBuyer(name, cityId);
    
        Logger.functionEnd(result);
        
        return result;
    }
    
    @Override
    public int setCity(int buyerId, int cityId) {
        Logger.functionStart("BuyerOperations setCity(buyerId: " + buyerId + ", cityId: " + cityId + ")");
        
        int result = super.setCity(buyerId, cityId);
    
        Logger.functionEnd(result);
        
        return result;
    }
    
    @Override
    public int getCity(int buyerId) {
        Logger.functionStart("BuyerOperations getCity(buyerId: " + buyerId + ")");
        
        int result = super.getCity(buyerId);
    
        Logger.functionEnd(result);
        
        return result;
    }
    
    @Override
    public BigDecimal increaseCredit(int buyerId, BigDecimal credit) {
        Logger.functionStart("BuyerOperations increaseCredit(buyerId: " + buyerId + ", credit: " + credit + ")");
        
        BigDecimal result = super.increaseCredit(buyerId, credit);
    
        Logger.functionEnd(result);
        
        return result;
    }
    
    @Override
    public int createOrder(int buyerId) {
        Logger.functionStart("BuyerOperations createOrder(buyerId: " + buyerId + ")");
        
        int result = super.createOrder(buyerId);
    
        Logger.functionEnd(result);
        
        return result;
    }
    
    @Override
    public List<Integer> getOrders(int buyerId) {
        Logger.functionStart("BuyerOperations getOrders(buyerId: " + buyerId + ")");
        
        List<Integer> result = super.getOrders(buyerId);
    
        Logger.functionEnd(result);
        
        return result;
    }
    
    @Override
    public BigDecimal getCredit(int buyerId) {
        Logger.functionStart("BuyerOperations getCredit(buyerId: " + buyerId + ")");
        
        BigDecimal result = super.getCredit(buyerId);
    
        Logger.functionEnd(result);
        
        return result;
    }
    
}