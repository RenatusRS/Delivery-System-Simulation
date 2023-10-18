package rs.etf.sab.student;


import rs.etf.sab.student.implementations.TransactionOperationsImpl;
import rs.etf.sab.student.utils.Logger;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;


class TransactionOperations extends TransactionOperationsImpl {
    
    
    @Override
    public BigDecimal getBuyerTransactionsAmmount(int buyerId) {
        Logger.functionStart("TransactionOperations getBuyerTransactionsAmmount(buyerId: " + buyerId + ")");
        
        BigDecimal result = super.getBuyerTransactionsAmmount(buyerId);
    
        Logger.functionEnd(result);
        
        return result;
    }
    
    @Override
    public BigDecimal getShopTransactionsAmmount(int shopId) {
        Logger.functionStart("TransactionOperations getShopTransactionsAmmount(shopId: " + shopId + ")");
        
        BigDecimal result = super.getShopTransactionsAmmount(shopId);
    
        Logger.functionEnd(result);
        
        return result;
    }
    
    @Override
    public List<Integer> getTransationsForBuyer(int buyerId) {
        Logger.functionStart("TransactionOperations getTransationsForBuyer(buyerId: " + buyerId + ")");
        
        List<Integer> result = super.getTransationsForBuyer(buyerId);
    
        Logger.functionEnd(result);
        
        return result;
    }
    
    @Override
    public int getTransactionForBuyersOrder(int orderId) {
        Logger.functionStart("TransactionOperations getTransactionForBuyersOrder(orderId: " + orderId + ")");
        
        int result = super.getTransactionForBuyersOrder(orderId);
    
        Logger.functionEnd(result);
        
        return result;
    }
    
    @Override
    public int getTransactionForShopAndOrder(int orderId, int shopId) {
        Logger.functionStart("TransactionOperations getTransactionForShopAndOrder(orderId: " + orderId + ", shopId: " + shopId + ")");
        
        int result = super.getTransactionForShopAndOrder(orderId, shopId);
    
        Logger.functionEnd(result);
        
        return result;
    }
    
    @Override
    public List<Integer> getTransationsForShop(int shopId) {
        Logger.functionStart("TransactionOperations getTransationsForShop(shopId: " + shopId + ")");
        
        List<Integer> result = super.getTransationsForShop(shopId);
    
        Logger.functionEnd(result);
        
        return result;
    }
    
    @Override
    public Calendar getTimeOfExecution(int transactionId) {
        Logger.functionStart("TransactionOperations getTimeOfExecution(transactionId: " + transactionId + ")");
        
        Calendar result = super.getTimeOfExecution(transactionId);
    
        Logger.functionEnd(result);
        
        return result;
    }
    
    @Override
    public BigDecimal getAmmountThatBuyerPayedForOrder(int orderId) {
        Logger.functionStart("TransactionOperations getAmmountThatBuyerPayedForOrder(orderId: " + orderId + ")");
        
        BigDecimal result = super.getAmmountThatBuyerPayedForOrder(orderId);
    
        Logger.functionEnd(result);
        
        return result;
    }
    
    @Override
    public BigDecimal getAmmountThatShopRecievedForOrder(int shopId, int orderId) {
        Logger.functionStart("TransactionOperations getAmmountThatShopRecievedForOrder(shopId: " + shopId + ", orderId: " + orderId + ")");
        
        BigDecimal result = super.getAmmountThatShopRecievedForOrder(shopId, orderId);
    
        Logger.functionEnd(result);
        
        return result;
    }
    
    @Override
    public BigDecimal getTransactionAmount(int transactionId) {
        Logger.functionStart("TransactionOperations getTransactionAmount(transactionId: " + transactionId + ")");
        
        BigDecimal result = super.getTransactionAmount(transactionId);
    
        Logger.functionEnd(result);
        
        return result;
    }
    
    @Override
    public BigDecimal getSystemProfit() {
        Logger.functionStart("TransactionOperations getSystemProfit()");
        
        BigDecimal result = super.getSystemProfit();
    
        Logger.functionEnd(result);
        
        return result;
    }
}