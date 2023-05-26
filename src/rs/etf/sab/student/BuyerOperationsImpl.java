package rs.etf.sab.student;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import rs.etf.sab.operations.BuyerOperations;
import rs.etf.sab.student.utils.SelectResult;
import rs.etf.sab.student.utils.DB;
import rs.etf.sab.student.utils.Where;


class BuyerOperationsImpl implements BuyerOperations {
    
    @Override
    public int createBuyer(String name, int cityId) {
        return DB.insert("Buyer", new HashMap<>() {{
            put("Name", name);
            put("CityID", cityId);
        }});
    }
    
    @Override
    public int setCity(int buyerId, int cityId) {
        return DB.update("Buyer", new HashMap<>() {{
            put("CityID", cityId);
        }}, new Where("ID", "=", buyerId));
    }
    
    @Override
    public int getCity(int buyerId) {
        SelectResult result = DB.select("Buyer", new Where("ID", "=", buyerId));
        
        return result.isEmpty() ? -1 : (int) result.get("CityID");
    }
    
    @Override
    public BigDecimal increaseCredit(int buyerId, BigDecimal credit) {
        DB.update("Buyer", new HashMap<>() {{
            put("Credit", "Credit + " + credit);
        }}, new Where("ID", "=", buyerId));
        
        return getCredit(buyerId);
    }
    
    @Override
    public int createOrder(int buyerId) {
        return DB.insert("Order", new HashMap<>() {{
            put("BuyerID", buyerId);
        }});
    }
    
    @Override
    public List<Integer> getOrders(int buyerId) {
        SelectResult orders = DB.select("Order", new Where("BuyerID", "=", buyerId));
        
        ArrayList<Integer> orderIds = new ArrayList<>();
        
        for (HashMap<String, Object> order : orders) {
            orderIds.add((int) order.get("ID"));
        }
        
        return orderIds;
    }
    
    @Override
    public BigDecimal getCredit(int buyerId) {
        SelectResult buyer = DB.select("Buyer", new Where("ID", "=", buyerId));
        
        return buyer.isEmpty() ? null : (BigDecimal) buyer.get("Credit");
    }
    
}