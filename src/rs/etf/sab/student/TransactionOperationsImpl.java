package rs.etf.sab.student;


import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import rs.etf.sab.operations.TransactionOperations;
import rs.etf.sab.student.utils.Column;
import rs.etf.sab.student.utils.DB;
import rs.etf.sab.student.utils.Result;
import rs.etf.sab.student.utils.Where;


class TransactionOperationsImpl implements TransactionOperations {
    
    
    @Override
    public BigDecimal getBuyerTransactionsAmmount(int buyerId) {
        Result transactions = DB.select("Transaction", new Where("BuyerID", "=", buyerId));
        
        BigDecimal amount = BigDecimal.ZERO;
        
        for (Column transaction : transactions) {
            amount = amount.add((BigDecimal) transaction.get("Amount"));
        }
        
        return amount;
    }
    
    @Override
    public BigDecimal getShopTransactionsAmmount(int shopId) {
        Result transactions = DB.select("Transaction", new Where("ShopID", "=", shopId));
        
        BigDecimal amount = BigDecimal.ZERO;
        
        for (Column transaction : transactions) {
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
        Result transaction = DB.select("Transaction", new Where("OrderID", "=", orderId));
        
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
        return null;
    }
    
    @Override
    public BigDecimal getAmmountThatShopRecievedForOrder(int shopId, int orderId) {
        return null;
    }
    
    @Override
    public BigDecimal getTransactionAmount(int transactionId) {
        return null;
    }
    
    @Override
    public BigDecimal getSystemProfit() {
        return null;
    }
}