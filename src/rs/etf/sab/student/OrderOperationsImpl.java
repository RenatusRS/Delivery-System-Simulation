package rs.etf.sab.student;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import rs.etf.sab.operations.OrderOperations;


class OrderOperationsImpl implements OrderOperations {
    
    @Override
    public int addArticle(int i, int i1, int i2) {
        return 0;
    }
    
    @Override
    public int removeArticle(int i, int i1) {
        return 0;
    }
    
    @Override
    public List<Integer> getItems(int i) {
        return null;
    }
    
    @Override
    public int completeOrder(int i) {
        return 0;
    }
    
    @Override
    public BigDecimal getFinalPrice(int i) {
        return null;
    }
    
    @Override
    public BigDecimal getDiscountSum(int i) {
        return null;
    }
    
    @Override
    public String getState(int i) {
        return null;
    }
    
    @Override
    public Calendar getSentTime(int i) {
        return null;
    }
    
    @Override
    public Calendar getRecievedTime(int i) {
        return null;
    }
    
    @Override
    public int getBuyer(int i) {
        return 0;
    }
    
    @Override
    public int getLocation(int i) {
        return 0;
    }
}