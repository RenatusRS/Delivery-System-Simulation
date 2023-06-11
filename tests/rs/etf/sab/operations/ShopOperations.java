package rs.etf.sab.operations;

import java.util.List;

public interface ShopOperations {
	int createShop(String paramString1, String paramString2);
	
	int setCity(int paramInt, String paramString);
	
	int getCity(int paramInt);
	
	int setDiscount(int paramInt1, int paramInt2);
	
	int increaseArticleCount(int paramInt1, int paramInt2);
	
	int getArticleCount(int paramInt);
	
	List<Integer> getArticles(int paramInt);
	
	int getDiscount(int paramInt);
}


/* Location:              D:\repos\SAB-Project\SAB_project_2223.jar!\rs\etf\sab\operations\ShopOperations.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */