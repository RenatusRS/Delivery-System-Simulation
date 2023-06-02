package rs.etf.sab.student;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import rs.etf.sab.operations.OrderOperations;
import rs.etf.sab.student.utils.Column;
import rs.etf.sab.student.utils.Result;
import rs.etf.sab.student.utils.DB;
import rs.etf.sab.student.utils.Where;


class OrderOperationsImpl implements OrderOperations {
    
    @Override
    public int addArticle(int orderId, int articleId, int count) {
        Result orderArticle = DB.select("OrderArticle", new Where[] {
            new Where("OrderID", "=", orderId),
            new Where("ArticleID", "=", articleId)
        });
        
        if (orderArticle.isEmpty()) {
            return DB.insert("OrderArticle", new Column() {{
                put("OrderID", orderId);
                put("ArticleID", articleId);
                put("Count", count);
            }});
        } else {
            DB.update("OrderArticle", new Column() {{
                put("Count", (int) orderArticle.get(0).get("Count") + count);
            }}, new Where[] {
                new Where("OrderID", "=", orderId),
                new Where("ArticleID", "=", articleId)
            });
        }
        
        return (int) orderArticle.get(0).get("ID");
    }
    
    @Override
    public int removeArticle(int orderId, int articleId) {
        return DB.delete("OrderArticle", new Where[] {
            new Where("OrderID", "=", orderId),
            new Where("ArticleID", "=", articleId)
        });
    }
    
    @Override
    public List<Integer> getItems(int orderId) {
        Result orderArticles = DB.select("OrderArticle", new Where("OrderID", "=", orderId));
        
        return (List<Integer>) orderArticles.getAll("ID");
    }
    
    @Override
    public int completeOrder(int orderId) {
        return 0;
    }
    
    @Override
    public BigDecimal getFinalPrice(int orderId) {
        return null;
    }
    
    @Override
    public BigDecimal getDiscountSum(int orderId) {
        return null;
    }
    
    @Override
    public String getState(int orderId) {
        return null;
    }
    
    @Override
    public Calendar getSentTime(int orderId) {
        return null;
    }
    
    @Override
    public Calendar getRecievedTime(int orderId) {
        return null;
    }
    
    @Override
    public int getBuyer(int orderId) {
        Result order = DB.select("Order", new Where("OrderID", "=", orderId));
        
        return order.isEmpty() ? -1 : (int) order.get(0).get("BuyerID");
    }
    
    @Override
    public int getLocation(int orderId) {
        return 0;
    }
}