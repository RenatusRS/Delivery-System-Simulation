package rs.etf.sab.operations;

import java.math.BigDecimal;
import java.util.List;

public interface BuyerOperations {
	int createBuyer(String paramString, int paramInt);
	
	int setCity(int paramInt1, int paramInt2);
	
	int getCity(int paramInt);
	
	BigDecimal increaseCredit(int paramInt, BigDecimal paramBigDecimal);
	
	int createOrder(int paramInt);
	
	List<Integer> getOrders(int paramInt);
	
	BigDecimal getCredit(int paramInt);
}


/* Location:              D:\repos\SAB-Project\SAB_project_2223.jar!\rs\etf\sab\operations\BuyerOperations.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */