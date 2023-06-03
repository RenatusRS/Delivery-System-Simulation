package rs.etf.sab.student.implementations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import rs.etf.sab.operations.OrderOperations;
import rs.etf.sab.student.StudentMain;
import rs.etf.sab.student.utils.*;


public class OrderOperationsImpl implements OrderOperations {
    
    @Override
    public int addArticle(int orderId, int articleId, int count) {
        Result orderArticle = DB.select("OrderArticle", new Where[] {
            new Where("OrderID", "=", orderId),
            new Where("ArticleID", "=", articleId)
        });
        
        if (orderArticle.isEmpty()) {
            return DB.insert("OrderArticle", new Entry() {{
                put("OrderID", orderId);
                put("ArticleID", articleId);
                put("Count", count);
            }});
        } else {
            DB.update("OrderArticle", new Entry() {{
                put("Count", (int) orderArticle.get("Count") + count);
            }}, new Where[] {
                new Where("OrderID", "=", orderId),
                new Where("ArticleID", "=", articleId)
            });
        }
        
        return (int) orderArticle.get("OrderArticleID");
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
        
        return (List<Integer>) orderArticles.getAll("OrderArticleID");
    }
    
    @Override
    public int completeOrder(int orderId) {
        Result order = DB.select("Order", new Where("OrderID", "=", orderId));
        Result buyer = DB.select("Buyer", new Where("BuyerID", "=", (int) order.get("BuyerID")));
        
        int closestStore = UtilityOperations.findClosestStoreCity((int) buyer.get("CityID"));
        
        DB.update("Order", new Entry() {{
            put("Status", "sent");
            put("SentTime", StudentMain.generalOperations.getCurrentTime());
            put("CurrentCity", closestStore);
        }}, new Where("OrderID", "=", orderId));
        
        Result orderArticles = DB.select("OrderArticle", new Where("OrderID", "=", orderId));
        
        for (Entry orderArticle : orderArticles) {
            Result article = DB.select("Article", new Where("ArticleID", "=", (int) orderArticle.get("ArticleID")));
            Result store = DB.select("Shop", new Where("ShopID", "=", (int) article.get("ShopID")));
            
            int startingStore = (int) store.get("CityID");
            
            if (startingStore == closestStore) {
                DB.update("OrderArticle", new Entry() {{
                    put("ShopCityID", closestStore);
                    put("TargetCityID", closestStore);
                    put("Distance", 0);
                }}, new Where("OrderArticleID", "=", (int) orderArticle.get("OrderArticleID")));
                
                continue;
            }
            
            ArrayList<Integer> path = UtilityOperations.shortestPath(startingStore, closestStore);
            
            Result connection = DB.select("Connection", new Where[] {
                new Where("City1ID", "=", path.get(0)),
                new Where("City2ID", "=", path.get(1))
            });
            
            DB.update("OrderArticle", new Entry() {{
                put("ShopCityID", closestStore);
                put("TargetCityID", path.get(1));
                put("Distance", connection.get("Distance"));
            }}, new Where("OrderArticleID", "=", (int) orderArticle.get("OrderArticleID")));
        }
        
        return 1;
    }
    
    @Override
    public BigDecimal getFinalPrice(int orderId) {
        // TODO: With procedure
        
        return null;
    }
    
    @Override
    public BigDecimal getDiscountSum(int orderId) {
        Result order = DB.select("Order", new Where("OrderID", "=", orderId));
        
        Result transactions = DB.select("Transaction", new Where("BuyerID", "=", (int) order.get("BuyerID")));
        
        boolean eligibleForDiscount = false;
        
        for (Entry transaction : transactions) {
            long executedTime = ((Calendar) transaction.get("ExecutionTime")).getTimeInMillis();
            long currentTime = StudentMain.generalOperations.getCurrentTime().getTimeInMillis();
            
            int daysBetween = (int) ((currentTime - executedTime) / (1000 * 60 * 60 * 24));
            
            if (((BigDecimal) transaction.get("Amount")).compareTo(BigDecimal.valueOf(10000)) >= 0 && daysBetween <= 30) {
                eligibleForDiscount = true;
                break;
            }
        }
    
        Result orderArticles = DB.select("OrderArticle", new Where("OrderID", "=", orderId));
    
        float total = 0;
        float totalWithDiscount = 0;
        
        for (Entry orderArticle : orderArticles) {
            Result article = DB.select("Article", new Where("ArticleID", "=", (int) orderArticle.get("ArticleID")));
            Result shop = DB.select("Shop", new Where("ShopID", "=", (int) article.get("ShopID")));
        
            float price = (float) article.get("Price");
            float priceWithDiscount = price * (1 - (float) shop.get("Discount")) / 100;
            
            float count = (float) orderArticle.get("Count");
            
            total += price * count;
            totalWithDiscount += priceWithDiscount * count;
        }
        
        if (eligibleForDiscount) totalWithDiscount *= 0.98;
        
        return BigDecimal.valueOf(total - totalWithDiscount);
    }
    
    @Override
    public String getState(int orderId) {
        Result order = DB.select("Order", new Where("OrderID", "=", orderId));
        
        return order.isEmpty() ? null : (String) order.get("State");
    }
    
    @Override
    public Calendar getSentTime(int orderId) {
        Result order = DB.select("Order", new Where("OrderID", "=", orderId));
        
        return order.isEmpty() ? null : (Calendar) order.get("SentTime");
    }
    
    @Override
    public Calendar getRecievedTime(int orderId) {
        Result order = DB.select("Order", new Where("OrderID", "=", orderId));
        
        return order.isEmpty() ? null : (Calendar) order.get("RecievedTime");
    }
    
    @Override
    public int getBuyer(int orderId) {
        Result order = DB.select("Order", new Where("OrderID", "=", orderId));
        
        return order.isEmpty() ? -1 : (int) order.get("BuyerID");
    }
    
    @Override
    public int getLocation(int orderId) {
        Result order = DB.select("Order", new Where("OrderID", "=", orderId));
        
        return order.isEmpty() ? -1 : (int) order.get("CurrentCityID");
    }
}