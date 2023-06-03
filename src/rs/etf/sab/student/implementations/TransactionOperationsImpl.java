package rs.etf.sab.student.implementations;


import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import rs.etf.sab.operations.TransactionOperations;
import rs.etf.sab.student.utils.Entry;
import rs.etf.sab.student.utils.DB;
import rs.etf.sab.student.utils.Result;
import rs.etf.sab.student.utils.Where;


public class TransactionOperationsImpl implements TransactionOperations {
    
    
    @Override
    public BigDecimal getBuyerTransactionsAmmount(int buyerId) {
        Result transactions = DB.select("Transaction", new Where("BuyerID", "=", buyerId));
        
        BigDecimal amount = BigDecimal.ZERO;
        
        for (Entry transaction : transactions) {
            amount = amount.add((BigDecimal) transaction.get("Amount"));
        }
        
        return amount;
    }
    
    @Override
    public BigDecimal getShopTransactionsAmmount(int shopId) {
        Result transactions = DB.select("Transaction", new Where("ShopID", "=", shopId));
        
        BigDecimal amount = BigDecimal.ZERO;
        
        for (Entry transaction : transactions) {
            amount = amount.add((BigDecimal) transaction.get("Amount"));
        }
        
        return amount;
    }
    
    @Override
    public List<Integer> getTransationsForBuyer(int buyerId) {
        Result transactions = DB.select("Transaction", new Where("BuyerID", "=", buyerId));
        
        return (List<Integer>) transactions.getAll("TransactionID");
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
        
        return (List<Integer>) transactions.getAll("TransactionID");
    }
    
    @Override
    public Calendar getTimeOfExecution(int transactionId) {
        Result transactions = DB.select("Transaction", new Where("TransactionID", "=", transactionId));
        
        return transactions.isEmpty() ? null : (Calendar) transactions.get("ExecutionTime");
    }
    
    @Override
    public BigDecimal getAmmountThatBuyerPayedForOrder(int orderId) {
        Result transaction = DB.select("Transaction", new Where[] {
                new Where("OrderID", "=", orderId),
                new Where("ShopID", "IS", null)
        });
        
        return (BigDecimal) transaction.get("Amount");
    }
    
    @Override
    public BigDecimal getAmmountThatShopRecievedForOrder(int shopId, int orderId) {
        Result transaction = DB.select("Transaction", new Where[] {
            new Where("OrderID", "=", orderId),
            new Where("ShopID", "=", shopId)
        });
        
        return (BigDecimal) transaction.get("Amount");
    }
    
    @Override
    public BigDecimal getTransactionAmount(int transactionId) {
        Result transaction = DB.select("Transaction", new Where("TransactionID", "=", transactionId));
        
        return (BigDecimal) transaction.get("Amount");
    }
    
    @Override
    public BigDecimal getSystemProfit() {
        Result transactions = DB.select("Transaction");
        
        BigDecimal amount = BigDecimal.ZERO;
        
        for (Entry transaction : transactions) {
            BigDecimal change = (BigDecimal) transaction.get("Amount");
            
            amount = transactions.get("BuyerID") != null ? amount.add(change) : amount.subtract(change);
        }
        
        return amount;
    }
}