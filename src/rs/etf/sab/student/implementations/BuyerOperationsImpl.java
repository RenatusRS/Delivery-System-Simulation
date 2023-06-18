package rs.etf.sab.student.implementations;

import rs.etf.sab.operations.BuyerOperations;
import rs.etf.sab.student.utils.DB;
import rs.etf.sab.student.utils.Entry;
import rs.etf.sab.student.utils.Result;
import rs.etf.sab.student.utils.Where;

import java.math.BigDecimal;
import java.util.List;


public class BuyerOperationsImpl implements BuyerOperations {
    
    @Override
    public int createBuyer(String name, int cityId) {
        return DB.insert("Buyer", new Entry() {{
            put("Name", name);
            put("CityID", cityId);
        }});
    }
    
    @Override
    public int setCity(int buyerId, int cityId) {
        return DB.update("Buyer", new Entry() {{
            put("CityID", cityId);
        }}, new Where("BuyerID", "=", buyerId));
    }
    
    @Override
    public int getCity(int buyerId) {
        Result result = DB.select("Buyer", new Where("BuyerID", "=", buyerId));
        
        return result.isEmpty() ? -1 : (int) result.get("CityID");
    }
    
    @Override
    public BigDecimal increaseCredit(int buyerId, BigDecimal credit) {
        DB.update("Buyer", new Entry() {{
            put("Credit", "Credit + " + credit);
        }}, new Where("BuyerID", "=", buyerId));
        
        return getCredit(buyerId).setScale(3);
    }
    
    @Override
    public int createOrder(int buyerId) {
        return DB.insert("Order", new Entry() {{
            put("BuyerID", buyerId);
        }});
    }
    
    @Override
    public List<Integer> getOrders(int buyerId) {
        Result orders = DB.select("Order", new Where("BuyerID", "=", buyerId));
        
        return (List<Integer>) orders.getAll("OrderID");
    }
    
    @Override
    public BigDecimal getCredit(int buyerId) {
        Result buyer = DB.select("Buyer", new Where("BuyerID", "=", buyerId));
        
        return ((BigDecimal) buyer.get("Credit")).setScale(3);
    }
    
}