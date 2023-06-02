package rs.etf.sab.student;

import java.math.BigDecimal;
import java.util.List;
import rs.etf.sab.operations.BuyerOperations;
import rs.etf.sab.student.utils.Column;
import rs.etf.sab.student.utils.Result;
import rs.etf.sab.student.utils.DB;
import rs.etf.sab.student.utils.Where;


class BuyerOperationsImpl implements BuyerOperations {
    
    @Override
    public int createBuyer(String name, int cityId) {
        return DB.insert("Buyer", new Column() {{
            put("Name", name);
            put("CityID", cityId);
        }});
    }
    
    @Override
    public int setCity(int buyerId, int cityId) {
        return DB.update("Buyer", new Column() {{
            put("CityID", cityId);
        }}, new Where("ID", "=", buyerId));
    }
    
    @Override
    public int getCity(int buyerId) {
        Result result = DB.select("Buyer", new Where("ID", "=", buyerId));
        
        return result.isEmpty() ? -1 : (int) result.get("CityID");
    }
    
    @Override
    public BigDecimal increaseCredit(int buyerId, BigDecimal credit) {
        DB.update("Buyer", new Column() {{
            put("Credit", "Credit + " + credit);
        }}, new Where("ID", "=", buyerId));
        
        return getCredit(buyerId);
    }
    
    @Override
    public int createOrder(int buyerId) {
        return DB.insert("Order", new Column() {{
            put("BuyerID", buyerId);
        }});
    }
    
    @Override
    public List<Integer> getOrders(int buyerId) {
        Result orders = DB.select("Order", new Where("BuyerID", "=", buyerId));
        
        return (List<Integer>) orders.getAll("ID");
    }
    
    @Override
    public BigDecimal getCredit(int buyerId) {
        Result buyer = DB.select("Buyer", new Where("ID", "=", buyerId));
        
        return buyer.isEmpty() ? null : (BigDecimal) buyer.get("Credit");
    }
    
}