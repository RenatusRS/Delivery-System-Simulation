package rs.etf.sab.tests;

import rs.etf.sab.operations.*;

import javax.validation.constraints.NotNull;

public class TestHandler {
	private static TestHandler testHandler = null;
	
	private final ArticleOperations articleOperations;
	private final BuyerOperations buyerOperations;
	private final CityOperations cityOperations;
	private final GeneralOperations generalOperations;
	private final OrderOperations orderOperations;
	private final ShopOperations shopOperations;
	private final TransactionOperations transactionOperations;
	
	public TestHandler(@NotNull ArticleOperations articleOperations, @NotNull BuyerOperations buyerOperations, @NotNull CityOperations cityOperations, @NotNull GeneralOperations generalOperations, @NotNull OrderOperations orderOperations, @NotNull ShopOperations shopOperations, @NotNull TransactionOperations transactionOperations) {
		this.articleOperations = articleOperations;
		this.buyerOperations = buyerOperations;
		this.cityOperations = cityOperations;
		this.generalOperations = generalOperations;
		this.orderOperations = orderOperations;
		this.shopOperations = shopOperations;
		this.transactionOperations = transactionOperations;
	}
	
	
	public static void createInstance(@NotNull ArticleOperations articleOperations, @NotNull BuyerOperations buyerOperations, @NotNull CityOperations cityOperations, @NotNull GeneralOperations generalOperations, @NotNull OrderOperations orderOperations, @NotNull ShopOperations shopOperations, @NotNull TransactionOperations transactionOperations) {
		testHandler = new TestHandler(articleOperations, buyerOperations, cityOperations, generalOperations, orderOperations, shopOperations, transactionOperations);
	}
	
	
	static TestHandler getInstance() {
		return testHandler;
	}
	
	public ArticleOperations getArticleOperations() {
		return this.articleOperations;
	}
	
	public BuyerOperations getBuyerOperations() {
		return this.buyerOperations;
	}
	
	public CityOperations getCityOperations() {
		return this.cityOperations;
	}
	
	public GeneralOperations getGeneralOperations() {
		return this.generalOperations;
	}
	
	public OrderOperations getOrderOperations() {
		return this.orderOperations;
	}
	
	public ShopOperations getShopOperations() {
		return this.shopOperations;
	}
	
	public TransactionOperations getTransactionOperations() {
		return this.transactionOperations;
	}
}


/* Location:              D:\repos\SAB-Project\SAB_project_2223.jar!\rs\etf\sab\tests\TestHandler.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */