package rs.etf.sab.student.implementations;

import rs.etf.sab.operations.OrderOperations;
import rs.etf.sab.student.utils.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static rs.etf.sab.student.StudentMain.generalOperations;


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
            if (DB.update("OrderArticle", new Entry() {{
                put("Count", (int) orderArticle.get("Count") + count);
            }}, new Where[] {
                    new Where("OrderID", "=", orderId),
                    new Where("ArticleID", "=", articleId)
            }) == -1) return -1;
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
        int fail = 1;
        
        Result order = DB.select("Order", new Where("OrderID", "=", orderId));
        Result buyer = DB.select("Buyer", new Where("BuyerID", "=", (int) order.get("BuyerID")));
        
        int closestStore = UtilityOperations.findClosestStoreCity((int) buyer.get("CityID"));
        ArrayList<Integer> pathTarget = UtilityOperations.shortestPath(closestStore, (int) buyer.get("CityID"));
        
        Result connectionTarget = DB.select("Connection", new Where[][] {
            new Where[] {
                new Where("CityID1", "=", pathTarget.get(0)),
                new Where("CityID2", "=", pathTarget.get(1))
            },
            new Where[] {
                new Where("CityID1", "=", pathTarget.get(1)),
                new Where("CityID2", "=", pathTarget.get(0))
            }
        });
        
        fail = DB.update("Order", new Entry() {{
            put("Status", "sent");
            put("SentTime", UtilityOperations.getDateFromCalendar(generalOperations.getCurrentTime()));
            put("CurrentCityID", closestStore);
            put("TargetCityID", pathTarget.get(1));
            put("Distance", connectionTarget.get("Distance"));
        }}, new Where("OrderID", "=", orderId));
        
        if (fail == -1) return -1;
        
        Result orderArticles = DB.select("OrderArticle", new Where("OrderID", "=", orderId));
        
        for (Entry orderArticle : orderArticles) {
            Result article = DB.select("Article", new Where("ArticleID", "=", (int) orderArticle.get("ArticleID")));
            Result store = DB.select("Shop", new Where("ShopID", "=", (int) article.get("ShopID")));
            
            int startingStore = (int) store.get("CityID");
            
            if (startingStore == closestStore) {
                fail = DB.update("OrderArticle", new Entry() {{
                    put("ShopCityID", closestStore);
                    put("TargetCityID", closestStore);
                    put("Distance", 0);
                    put("Cost", (int) article.get("Price") * (int) orderArticle.get("Count") * (100 - (int) store.get("Discount")) / 100.0);
                }}, new Where("OrderArticleID", "=", (int) orderArticle.get("OrderArticleID")));
                
                if (fail == -1) return -1;
                
                continue;
            }
            
            ArrayList<Integer> path = UtilityOperations.shortestPath(startingStore, closestStore);
            
            System.out.println(path);
    
            Result connection = DB.select("Connection", new Where[][] {
                    new Where[] {
                            new Where("CityID1", "=", path.get(0)),
                            new Where("CityID2", "=", path.get(1))
                    },
                    new Where[] {
                            new Where("CityID1", "=", path.get(1)),
                            new Where("CityID2", "=", path.get(0))
                    }
            });
            
            fail = DB.update("OrderArticle", new Entry() {{
                put("ShopCityID", closestStore);
                put("TargetCityID", path.get(1));
                put("Distance", connection.get("Distance"));
                put("Cost", (int) article.get("Price") * (int) orderArticle.get("Count") * (100 - (int) store.get("Discount")) / 100.0);
            }}, new Where("OrderArticleID", "=", (int) orderArticle.get("OrderArticleID")));
            
            if (fail == -1) return -1;
        }
        
        fail = DB.insert("Transaction", new Entry() {{
            put("OrderID", orderId);
            put("BuyerID", order.get("BuyerID"));
            put("Amount", getFinalPrice(orderId));
            put("ExecutionTime", UtilityOperations.getDateFromCalendar(generalOperations.getCurrentTime()));
        }});
        
        return fail == -1 ? -1 : 1;
    }
    
    @Override
    public BigDecimal getFinalPrice(int orderId) {
        // TODO: final price. Sum that buyer have to pay. -1 if failure or if order is not completed
        
        return BigDecimal.valueOf((double) DB.procedure("spFinalPrice", String.valueOf(orderId), UtilityOperations.getDateFromCalendar(generalOperations.getCurrentTime()))).setScale(3);
    }
    
    @Override
    public BigDecimal getDiscountSum(int orderId) {
        Result orderArticles = DB.select("OrderArticle", new Where("OrderID", "=", orderId));
    
        double total = 0;
        double totalWithDiscount = 0;
    
        for (Entry orderArticle : orderArticles) {
            Result article = DB.select("Article", new Where("ArticleID", "=", (int) orderArticle.get("ArticleID")));
            Result shop = DB.select("Shop", new Where("ShopID", "=", (int) article.get("ShopID")));
        
            int price = (int) article.get("Price");
            double priceWithDiscount = price * (100 - (int) shop.get("Discount")) / 100.0;
        
            int count = (int) orderArticle.get("Count");
        
            total += price * count;
            totalWithDiscount += priceWithDiscount * count;
        }
    
        Result order = DB.select("Order", new Where("OrderID", "=", orderId));
    
        Result transactions = DB.select("Transaction", new Where[] {
                new Where("BuyerID", "=", (int) order.get("BuyerID"))
        });
    
        boolean eligibleForDiscount = false;
        double eligibleForDiscountSum = 0;
    
        for (Entry transaction : transactions) {
            long executedTime = ((Date) transaction.get("ExecutionTime")).getTime();
            long currentTime = generalOperations.getCurrentTime().getTimeInMillis();
        
            int daysBetween = (int) ((currentTime - executedTime) / (1000 * 60 * 60 * 24));
        
            if (daysBetween <= 30) {
                eligibleForDiscountSum += ((BigDecimal) transaction.get("Amount")).doubleValue();
            
                if (eligibleForDiscountSum >= 10000) {
                    eligibleForDiscount = true;
                    break;
                }
            }
        }
    
        if (eligibleForDiscount) totalWithDiscount *= 0.98;
    
        // TODO: total discount, -1 if failure or if order is not completed
    
        return BigDecimal.valueOf(total - totalWithDiscount).setScale(3);
    }
    
    @Override
    public String getState(int orderId) {
        Result order = DB.select("Order", new Where("OrderID", "=", orderId));
        
        return (String) order.get("Status");
    }
    
    @Override
    public Calendar getSentTime(int orderId) {
        Result order = DB.select("Order", new Where("OrderID", "=", orderId));
        
        if (order.isEmpty() || order.get("SentTime") == null) return null;
    
        Calendar calendar = Calendar.getInstance();
        calendar.setTime((Date) order.get("SentTime"));
    
        return calendar;
    }
    
    @Override
    public Calendar getRecievedTime(int orderId) {
        Result order = DB.select("Order", new Where("OrderID", "=", orderId));
    
        if (order.isEmpty() || order.get("RecievedTime") == null) return null;
    
        Calendar calendar = Calendar.getInstance();
        calendar.setTime((Date) order.get("RecievedTime"));
    
        return calendar;
    }
    
    @Override
    public int getBuyer(int orderId) {
        Result order = DB.select("Order", new Where("OrderID", "=", orderId));
        
        return (int) order.get("BuyerID");
    }
    
    @Override
    public int getLocation(int orderId) {
        Result order = DB.select("Order", new Where("OrderID", "=", orderId));
        
        if (order.isEmpty() || order.get("Status") == "created") return -1;
        
        return order.isEmpty() ? -1 : (int) order.get("CurrentCityID");
    }
}