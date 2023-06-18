package rs.etf.sab.student.implementations;

import rs.etf.sab.operations.GeneralOperations;
import rs.etf.sab.student.utils.*;

import java.util.ArrayList;
import java.util.Calendar;


public class GeneralOperationsImpl implements GeneralOperations {
    private Calendar time = Calendar.getInstance();
    
    @Override
    public void setInitialTime(Calendar time) {
        Calendar timeCopy = Calendar.getInstance();
        timeCopy.setTimeInMillis(time.getTimeInMillis());
        this.time = timeCopy;
    }
    
    @Override
    public Calendar time(int days) {
        time.setTimeInMillis(time.getTimeInMillis() + (long) days * 24 * 60 * 60 * 1000);
        
        DB.select("City");
        
        Result orders = DB.select("Order", new Where("Status", "=", "sent"));
        
        for (Entry order : orders) {
            int orderID = (int) order.get("OrderID");
            
            if (order.get("Status") == "arrived") continue;
    
            int maxUsedTokens = 0;
            
            Result orderArticles = DB.select("OrderArticle", new Where("OrderID", "=", orderID));
            
            for (Entry orderArticle : orderArticles) {
                int shopCityId = (int) orderArticle.get("ShopCityID");
                int targetCityId = (int) orderArticle.get("TargetCityID");
                int distance = (int) orderArticle.get("Distance");
                
                if (shopCityId == targetCityId && distance == 0) continue;
                
                System.out.println("OrderArticleID: " + orderArticle.get("OrderArticleID") + ", ShopCityID: " + shopCityId + ", TargetCityID: " + targetCityId + ", Distance: " + distance);
                
                int usedTokens = 0;
                
                while (usedTokens < days && (shopCityId != targetCityId || distance > 0)) {
                    int remainingTokens = days - usedTokens;
                    
                    int toUse = Math.min(remainingTokens, distance);
                    
                    distance -= toUse;
                    usedTokens += toUse;
                    
                    if (distance == 0) {
                        if (shopCityId == targetCityId) break;
                        
                        ArrayList<Integer> path = UtilityOperations.shortestPath(targetCityId, shopCityId);
                        
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
                        
                        distance = (int) connection.get("Distance");
                        targetCityId = path.get(1);
                    }
                }
                
                maxUsedTokens = Math.max(maxUsedTokens, usedTokens);
    
                int finalDistance = distance;
                int finalTargetCityId = targetCityId;
                DB.update("OrderArticle", new Entry() {{
                    put("Distance", finalDistance);
                    put("TargetCityID", finalTargetCityId);
                }}, new Where("OrderArticleID", "=", (int) orderArticle.get("OrderArticleID")));
            }
            
            if (maxUsedTokens == days) continue;
            
            int distance = (int) order.get("Distance");
            int targetCityId = (int) order.get("TargetCityID");
            int currentCityId = (int) order.get("CurrentCityID");
            
            Result buyer = DB.select("Buyer", new Where("BuyerID", "=", (int) order.get("BuyerID")));
            
            int buyerCityId = (int) buyer.get("CityID");
            
            while (maxUsedTokens < days && (currentCityId != buyerCityId || distance > 0)) {
                int remainingTokens = days - maxUsedTokens;
                
                int toUse = Math.min(remainingTokens, distance);
                
                distance -= toUse;
                maxUsedTokens += toUse;
                
                if (distance == 0) {
                    if (targetCityId == buyerCityId) {
                        currentCityId = buyerCityId;
                        break;
                    }
                    
                    ArrayList<Integer> path = UtilityOperations.shortestPath(targetCityId, buyerCityId);
    
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
                    
                    distance = (int) connection.get("Distance");
                    currentCityId = targetCityId;
                    targetCityId = path.get(1);
                }
            }
    
            int finalDistance = distance;
            int finalTargetCityId = targetCityId;
            int finalCurrentCityId = currentCityId;
    
            int finalMaxUsedTokens = maxUsedTokens;
            DB.update("Order", new Entry() {{
                put("Distance", finalDistance);
                put("TargetCityID", finalTargetCityId);
                put("CurrentCityID", finalCurrentCityId);
                put("Status", finalCurrentCityId == buyerCityId && finalDistance == 0 ? "arrived" : "sent");
                put("RecievedTime", finalCurrentCityId == buyerCityId && finalDistance == 0 ? UtilityOperations.getDateFromCalendar(time, -(days - finalMaxUsedTokens)) : null);
            }}, new Where("OrderID", "=", orderID));
        }
        
        return time;
    }
    
    @Override
    public Calendar getCurrentTime() {
        return time;
    }
    
    @Override
    public void eraseAll() {
        DB.delete("Transaction");
        DB.delete("OrderArticle");
        DB.delete("Connection");
    
        DB.delete("Order");
        
        DB.delete("Article");
        
        DB.delete("Buyer");
        DB.delete("Shop");
    
        DB.delete("City");
    }
}