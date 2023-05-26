package rs.etf.sab.student;


import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import rs.etf.sab.operations.TransactionOperations;
import rs.etf.sab.student.utils.DB;
import rs.etf.sab.student.utils.SelectResult;
import rs.etf.sab.student.utils.Where;


class TransactionOperationsImpl implements TransactionOperations {
    
    
    @Override
    public BigDecimal getBuyerTransactionsAmmount(int buyerId) {
        SelectResult transactions = DB.select("Transaction", new Where("BuyerID", "=", buyerId));
        
        BigDecimal amount = BigDecimal.ZERO;
        
        for (HashMap<String, Object> transaction : transactions) {
            amount = amount.add((BigDecimal) transaction.get("Amount"));
        }
        
        return amount;
    }
    
    @Override
    public BigDecimal getShopTransactionsAmmount(int shopId) {
        SelectResult transactions = DB.select("Transaction", new Where("ShopID", "=", shopId));
        
        BigDecimal amount = BigDecimal.ZERO;
        
        for (HashMap<String, Object> transaction : transactions) {
            amount = amount.add((BigDecimal) transaction.get("Amount"));
        }
        
        return amount;
    }
    
    @Override
    public List<Integer> getTransationsForBuyer(int buyerId) {
        SelectResult transactions = DB.select("Transaction", new Where("BuyerID", "=", buyerId));
        
        ArrayList<Integer> transactionIds = new ArrayList<>();
        
        for (HashMap<String, Object> transaction : transactions) {
            transactionIds.add((int) transaction.get("TransactionID"));
        }
        
        return transactionIds;
    }
    
    @Override
    public int getTransactionForBuyersOrder(int orderId) {
        SelectResult transaction = DB.select("Transaction", new Where("OrderID", "=", orderId));
        
        return transaction.isEmpty() ? -1 : (int) transaction.get("TransactionID");
    }
    
    @Override
    public int getTransactionForShopAndOrder(int orderId, int shopId) {
        SelectResult transaction = DB.select("Transaction", new Where[] {
            new Where("OrderID", "=", orderId),
            new Where("ShopID", "=", shopId)
        });
        
        return transaction.isEmpty() ? -1 : (int) transaction.get("TransactionID");
    }
    
    @Override
    public List<Integer> getTransationsForShop(int shopId) {
        SelectResult transactions = DB.select("Transaction", new Where("ShopID", "=", shopId));
        
        ArrayList<Integer> transactionIds = new ArrayList<>();
        
        for (HashMap<String, Object> transaction : transactions) {
            transactionIds.add((int) transaction.get("TransactionID"));
        }
        
        return transactionIds;
    }
    
    @Override
    public Calendar getTimeOfExecution(int transactionId) {
        SelectResult transactions = DB.select("Transaction", new Where("TransactionID", "=", transactionId));
        
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