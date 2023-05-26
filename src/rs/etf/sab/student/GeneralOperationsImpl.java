package rs.etf.sab.student;

import java.util.Calendar;
import rs.etf.sab.operations.GeneralOperations;
import rs.etf.sab.student.utils.DB;


class GeneralOperationsImpl implements GeneralOperations {
    private Calendar time = Calendar.getInstance();
    
    @Override
    public void setInitialTime(Calendar time) {
        this.time = time;
    }
    
    @Override
    public Calendar time(int days) {
        time.setTimeInMillis(time.getTimeInMillis() + (long) days * 24 * 60 * 60 * 1000);
        
        // TODO: Update all orders
        
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