package rs.etf.sab.operations;

import java.util.Calendar;

public interface GeneralOperations {
	void setInitialTime(Calendar paramCalendar);
	
	Calendar time(int paramInt);
	
	Calendar getCurrentTime();
	
	void eraseAll();
}


/* Location:              D:\repos\SAB-Project\SAB_project_2223.jar!\rs\etf\sab\operations\GeneralOperations.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */