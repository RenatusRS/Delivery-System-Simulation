package rs.etf.sab.operations;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

public interface TransactionOperations {
	BigDecimal getBuyerTransactionsAmmount(int paramInt);
	
	BigDecimal getShopTransactionsAmmount(int paramInt);
	
	List<Integer> getTransationsForBuyer(int paramInt);
	
	int getTransactionForBuyersOrder(int paramInt);
	
	int getTransactionForShopAndOrder(int paramInt1, int paramInt2);
	
	List<Integer> getTransationsForShop(int paramInt);
	
	Calendar getTimeOfExecution(int paramInt);
	
	BigDecimal getAmmountThatBuyerPayedForOrder(int paramInt);
	
	BigDecimal getAmmountThatShopRecievedForOrder(int paramInt1, int paramInt2);
	
	BigDecimal getTransactionAmount(int paramInt);
	
	BigDecimal getSystemProfit();
}


/* Location:              D:\repos\SAB-Project\SAB_project_2223.jar!\rs\etf\sab\operations\TransactionOperations.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */