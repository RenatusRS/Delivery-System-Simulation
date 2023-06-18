package rs.etf.sab.student.implementations;


import rs.etf.sab.operations.TransactionOperations;
import rs.etf.sab.student.utils.DB;
import rs.etf.sab.student.utils.Entry;
import rs.etf.sab.student.utils.Result;
import rs.etf.sab.student.utils.Where;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class TransactionOperationsImpl implements TransactionOperations {
    
    
    @Override
    public BigDecimal getBuyerTransactionsAmmount(int buyerId) {
        Result transactions = DB.select("Transaction", new Where("BuyerID", "=", buyerId));
        
        BigDecimal amount = BigDecimal.ZERO;
        
        for (Entry transaction : transactions) {
            amount = amount.add((BigDecimal) transaction.get("Amount"));
        }
        
        // TODO: sum of all transactions, 0 if there are not transactions, -1 if failure
        
        return amount.setScale(3);
    }
    
    @Override
    public BigDecimal getShopTransactionsAmmount(int shopId) {
        Result transactions = DB.select("Transaction", new Where("ShopID", "=", shopId));
        
        BigDecimal amount = BigDecimal.ZERO;
        
        for (Entry transaction : transactions) {
            amount = amount.add((BigDecimal) transaction.get("Amount"));
        }
        
        // TODO: sum of all transactions, 0 if there are not transactions, -1 if failure
        
        return amount.setScale(3);
    }
    
    @Override
    public List<Integer> getTransationsForBuyer(int buyerId) {
        Result transactions = DB.select("Transaction", new Where("BuyerID", "=", buyerId));
        
        return transactions.isEmpty() ? null : (List<Integer>) transactions.getAll("TransactionID");
    }
    
    @Override
    public int getTransactionForBuyersOrder(int orderId) {
        Result transaction = DB.select("Transaction", new Where[] {
            new Where("OrderID", "=", orderId),
            new Where("ShopID", "IS", null)
        });
        
        return transaction.isEmpty() ? -1 : (int) transaction.get("TransactionID");
    }
    
    @Override
    public int getTransactionForShopAndOrder(int orderId, int shopId) {
        Result transaction = DB.select("Transaction", new Where[] {
            new Where("OrderID", "=", orderId),
            new Where("ShopID", "=", shopId)
        });
        
        return transaction.isEmpty() ? -1 : (int) transaction.get("TransactionID");
    }
    
    @Override
    public List<Integer> getTransationsForShop(int shopId) {
        Result transactions = DB.select("Transaction", new Where("ShopID", "=", shopId));
        
        List<Integer> result = (List<Integer>) transactions.getAll("TransactionID");
        
        return result.isEmpty() ? null : result;
    }
    
    @Override
    public Calendar getTimeOfExecution(int transactionId) {
        Result transactions = DB.select("Transaction", new Where("TransactionID", "=", transactionId));
        
        if (transactions.isEmpty()) return null;
    
        Calendar calendar = Calendar.getInstance();
        calendar.setTime((Date) transactions.get("ExecutionTime"));
        
        // TODO: time of execution, null if payment is not done or if failure
        
        return calendar;
    }
    
    @Override
    public BigDecimal getAmmountThatBuyerPayedForOrder(int orderId) {
        Result transaction = DB.select("Transaction", new Where[] {
                new Where("OrderID", "=", orderId),
                new Where("ShopID", "IS", null)
        });
        
        return ((BigDecimal) transaction.get("Amount")).setScale(3);
    }
    
    @Override
    public BigDecimal getAmmountThatShopRecievedForOrder(int shopId, int orderId) {
        Result transaction = DB.select("Transaction", new Where[] {
            new Where("OrderID", "=", orderId),
            new Where("ShopID", "=", shopId)
        });
        
        return ((BigDecimal) transaction.get("Amount")).setScale(3);
    }
    
    @Override
    public BigDecimal getTransactionAmount(int transactionId) {
        Result transaction = DB.select("Transaction", new Where("TransactionID", "=", transactionId));
        
        return ((BigDecimal) transaction.get("Amount")).setScale(3);
    }
    
    @Override
    public BigDecimal getSystemProfit() {
        Result transactions = DB.select("Transaction");
        
        BigDecimal amount = BigDecimal.ZERO;
    
        HashMap<Integer, Boolean> recieved = new HashMap<>();
        
        for (Entry transaction : transactions) {
            if (transaction.get("ShopID") != null) recieved.put((int) transaction.get("OrderID"), true);
        }
        
        for (Entry transaction : transactions) {
            if (recieved.get((int) transaction.get("OrderID")) == null) continue;
            
            BigDecimal change = (BigDecimal) transaction.get("Amount");
            
            amount = transaction.get("BuyerID") != null ? amount.add(change) : amount.subtract(change);
        }
        
        return amount.setScale(3);
    }
}