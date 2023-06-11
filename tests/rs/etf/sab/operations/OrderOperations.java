package rs.etf.sab.operations;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

public interface OrderOperations {
	int addArticle(int paramInt1, int paramInt2, int paramInt3);
	
	int removeArticle(int paramInt1, int paramInt2);
	
	List<Integer> getItems(int paramInt);
	
	int completeOrder(int paramInt);
	
	BigDecimal getFinalPrice(int paramInt);
	
	BigDecimal getDiscountSum(int paramInt);
	
	String getState(int paramInt);
	
	Calendar getSentTime(int paramInt);
	
	Calendar getRecievedTime(int paramInt);
	
	int getBuyer(int paramInt);
	
	int getLocation(int paramInt);
}


/* Location:              D:\repos\SAB-Project\SAB_project_2223.jar!\rs\etf\sab\operations\OrderOperations.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */