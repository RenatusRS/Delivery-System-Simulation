package rs.etf.sab.student;

import rs.etf.sab.student.implementations.GeneralOperationsImpl;
import rs.etf.sab.student.utils.Logger;

import java.util.Calendar;


class lu190691_GeneralOperations extends GeneralOperationsImpl {
    @Override
    public void setInitialTime(Calendar time) {
        Logger.functionStart("GeneralOperations setInitialTime(time: " + time + ")");
        
        super.setInitialTime(time);
    
        Logger.functionEnd("void");
    }
    
    @Override
    public Calendar time(int days) {
        Logger.functionStart("GeneralOperations time(days: " + days + ")");
        
        Calendar result = super.time(days);
    
        Logger.functionEnd(result);
        
        return result;
    }
    
    @Override
    public Calendar getCurrentTime() {
        Logger.functionStart("GeneralOperations getCurrentTime()");
        
        Calendar result = super.getCurrentTime();
    
        Logger.functionEnd(result);
        
        return result;
    }
    
    @Override
    public void eraseAll() {
        Logger.functionStart("GeneralOperations eraseAll()");
        
        super.eraseAll();
    
        Logger.functionEnd("void");
    }
}