package rs.etf.sab.student;

import rs.etf.sab.student.implementations.OrderOperationsImpl;
import rs.etf.sab.student.utils.Logger;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;


class lu190691_OrderOperations extends OrderOperationsImpl {
    
    @Override
    public int addArticle(int orderId, int articleId, int count) {
        Logger.functionStart("OrderOperations addArticle(orderId: " + orderId + ", articleId: " + articleId + ", count: " + count + ")");
        
        int result = super.addArticle(orderId, articleId, count);
    
        Logger.functionEnd(result);
        
        return result;
    }
    
    @Override
    public int removeArticle(int orderId, int articleId) {
        Logger.functionStart("OrderOperations removeArticle(orderId: " + orderId + ", articleId: " + articleId + ")");
        
        int result = super.removeArticle(orderId, articleId);
    
        Logger.functionEnd(result);
        
        return result;
    }
    
    @Override
    public List<Integer> getItems(int orderId) {
        Logger.functionStart("OrderOperations getItems(orderId: " + orderId + ")");
        
        List<Integer> result = super.getItems(orderId);
    
        Logger.functionEnd(result);
        
        return result;
    }
    
    @Override
    public int completeOrder(int orderId) {
        Logger.functionStart("OrderOperations completeOrder(orderId: " + orderId + ")");
        
        int result = super.completeOrder(orderId);
    
        Logger.functionEnd(result);
        
        return result;
    }
    
    @Override
    public BigDecimal getFinalPrice(int orderId) {
        Logger.functionStart("OrderOperations getFinalPrice(orderId: " + orderId + ")");
        
        BigDecimal result = super.getFinalPrice(orderId);
    
        Logger.functionEnd(result);
        
        return result;
    }
    
    @Override
    public BigDecimal getDiscountSum(int orderId) {
        Logger.functionStart("OrderOperations getDiscountSum(orderId: " + orderId + ")");
        
        BigDecimal result = super.getDiscountSum(orderId);
    
        Logger.functionEnd(result);
        
        return result;
    }
    
    @Override
    public String getState(int orderId) {
        Logger.functionStart("OrderOperations getState(orderId: " + orderId + ")");
        
        String result = super.getState(orderId);
    
        Logger.functionEnd(result);
        
        return result;
    }
    
    @Override
    public Calendar getSentTime(int orderId) {
        Logger.functionStart("OrderOperations getSentTime(orderId: " + orderId + ")");
        
        Calendar result = super.getSentTime(orderId);
    
        Logger.functionEnd(result);
        
        return result;
    }
    
    @Override
    public Calendar getRecievedTime(int orderId) {
        Logger.functionStart("OrderOperations getRecievedTime(orderId: " + orderId + ")");
        
        Calendar result = super.getRecievedTime(orderId);
    
        Logger.functionEnd(result);
        
        return result;
    }
    
    @Override
    public int getBuyer(int orderId) {
        Logger.functionStart("OrderOperations getBuyer(orderId: " + orderId + ")");
        
        int result = super.getBuyer(orderId);
    
        Logger.functionEnd(result);
        
        return result;
    }
    
    @Override
    public int getLocation(int orderId) {
        Logger.functionStart("OrderOperations getLocation(orderId: " + orderId + ")");
        
        int result = super.getLocation(orderId);
    
        Logger.functionEnd(result);
        
        return result;
    }
}