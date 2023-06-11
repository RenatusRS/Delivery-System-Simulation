package rs.etf.sab.tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import rs.etf.sab.operations.*;

import java.math.BigDecimal;
import java.util.Calendar;

public class PublicModuleTest {
	private TestHandler testHandler;
	private GeneralOperations generalOperations;
	private ShopOperations shopOperations;
	private CityOperations cityOperations;
	private ArticleOperations articleOperations;
	private BuyerOperations buyerOperations;
	private OrderOperations orderOperations;
	private TransactionOperations transactionOperations;

	@Before
	public void setUp() throws Exception {
		this.testHandler = TestHandler.getInstance();
		Assert.assertNotNull(this.testHandler);
		
		this.shopOperations = this.testHandler.getShopOperations();
		Assert.assertNotNull(this.shopOperations);
		
		this.cityOperations = this.testHandler.getCityOperations();
		Assert.assertNotNull(this.cityOperations);
		
		this.articleOperations = this.testHandler.getArticleOperations();
		Assert.assertNotNull(this.articleOperations);
		
		this.buyerOperations = this.testHandler.getBuyerOperations();
		Assert.assertNotNull(this.buyerOperations);
		
		this.orderOperations = this.testHandler.getOrderOperations();
		Assert.assertNotNull(this.orderOperations);
		
		this.transactionOperations = this.testHandler.getTransactionOperations();
		Assert.assertNotNull(this.transactionOperations);
		
		this.generalOperations = this.testHandler.getGeneralOperations();
		Assert.assertNotNull(this.generalOperations);
		
		this.generalOperations.eraseAll();
	}
	
	@After
	public void tearDown() throws Exception {
		this.generalOperations.eraseAll();
	}
	
	@Test
	public void test() {
		try {
			Calendar initialTime = Calendar.getInstance();
			initialTime.clear();
			initialTime.set(2018, 0, 1);
			this.generalOperations.setInitialTime(initialTime);
			Calendar receivedTime = Calendar.getInstance();
			receivedTime.clear();
			receivedTime.set(2018, 0, 22);
			
			
			int cityB = this.cityOperations.createCity("B");
			int cityC1 = this.cityOperations.createCity("C1");
			int cityA = this.cityOperations.createCity("A");
			int cityC2 = this.cityOperations.createCity("C2");
			int cityC3 = this.cityOperations.createCity("C3");
			int cityC4 = this.cityOperations.createCity("C4");
			int cityC5 = this.cityOperations.createCity("C5");
			
			this.cityOperations.connectCities(cityB, cityC1, 8);
			this.cityOperations.connectCities(cityC1, cityA, 10);
			this.cityOperations.connectCities(cityA, cityC2, 3);
			this.cityOperations.connectCities(cityC2, cityC3, 2);
			this.cityOperations.connectCities(cityC3, cityC4, 1);
			this.cityOperations.connectCities(cityC4, cityA, 3);
			this.cityOperations.connectCities(cityA, cityC5, 15);
			this.cityOperations.connectCities(cityC5, cityB, 2);
			
			
			int shopA = this.shopOperations.createShop("shopA", "A");
			int shopC2 = this.shopOperations.createShop("shopC2", "C2");
			int shopC3 = this.shopOperations.createShop("shopC3", "C3");
			
			this.shopOperations.setDiscount(shopA, 20);
			this.shopOperations.setDiscount(shopC2, 50);
			
			int laptop = this.articleOperations.createArticle(shopA, "laptop", 1000);
			int monitor = this.articleOperations.createArticle(shopC2, "monitor", 200);
			int stolica = this.articleOperations.createArticle(shopC3, "stolica", 100);
			int sto = this.articleOperations.createArticle(shopC3, "sto", 200);
			
			this.shopOperations.increaseArticleCount(laptop, 10);
			this.shopOperations.increaseArticleCount(monitor, 10);
			this.shopOperations.increaseArticleCount(stolica, 10);
			this.shopOperations.increaseArticleCount(sto, 10);
			
			int buyer = this.buyerOperations.createBuyer("kupac", cityB);
			this.buyerOperations.increaseCredit(buyer, new BigDecimal("20000"));
			int order = this.buyerOperations.createOrder(buyer);
			
			this.orderOperations.addArticle(order, laptop, 5);
			this.orderOperations.addArticle(order, monitor, 4);
			this.orderOperations.addArticle(order, stolica, 10);
			this.orderOperations.addArticle(order, sto, 4);
			
			Assert.assertNull(this.orderOperations.getSentTime(order));
			Assert.assertEquals("created", this.orderOperations.getState(order));
			this.orderOperations.completeOrder(order);
			Assert.assertEquals("sent", this.orderOperations.getState(order));
			
			int buyerTransactionId = this.transactionOperations.getTransationsForBuyer(buyer).get(0).intValue();
			Assert.assertEquals(initialTime, this.transactionOperations.getTimeOfExecution(buyerTransactionId));
			
			Assert.assertNull(this.transactionOperations.getTransationsForShop(shopA));
			
			
			BigDecimal shopAAmount = (new BigDecimal("5")).multiply(new BigDecimal("1000")).setScale(3);
			BigDecimal shopAAmountWithDiscount = (new BigDecimal("0.8")).multiply(shopAAmount).setScale(3);
			BigDecimal shopC2Amount = (new BigDecimal("4")).multiply(new BigDecimal("200")).setScale(3);
			BigDecimal shopC2AmountWithDiscount = (new BigDecimal("0.5")).multiply(shopC2Amount).setScale(3);
			
			BigDecimal shopC3Amount = (new BigDecimal("10")).multiply(new BigDecimal("100")).add((new BigDecimal("4")).multiply(new BigDecimal("200"))).setScale(3);
			BigDecimal shopC3AmountWithDiscount = shopC3Amount;
			
			BigDecimal amountWithoutDiscounts = shopAAmount.add(shopC2Amount).add(shopC3Amount).setScale(3);
			BigDecimal amountWithDiscounts = shopAAmountWithDiscount.add(shopC2AmountWithDiscount).add(shopC3AmountWithDiscount).setScale(3);
			
			BigDecimal systemProfit = amountWithDiscounts.multiply(new BigDecimal("0.05")).setScale(3);
			BigDecimal shopAAmountReal = shopAAmountWithDiscount.multiply(new BigDecimal("0.95")).setScale(3);
			BigDecimal shopC2AmountReal = shopC2AmountWithDiscount.multiply(new BigDecimal("0.95")).setScale(3);
			BigDecimal shopC3AmountReal = shopC3AmountWithDiscount.multiply(new BigDecimal("0.95")).setScale(3);
			
			
			Assert.assertEquals(amountWithDiscounts, this.orderOperations.getFinalPrice(order));
			Assert.assertEquals(amountWithoutDiscounts.subtract(amountWithDiscounts), this.orderOperations.getDiscountSum(order));
			
			Assert.assertEquals(amountWithDiscounts, this.transactionOperations.getBuyerTransactionsAmmount(buyer));
			Assert.assertEquals(this.transactionOperations.getShopTransactionsAmmount(shopA), (new BigDecimal("0")).setScale(3));
			Assert.assertEquals(this.transactionOperations.getShopTransactionsAmmount(shopC2), (new BigDecimal("0")).setScale(3));
			Assert.assertEquals(this.transactionOperations.getShopTransactionsAmmount(shopC3), (new BigDecimal("0")).setScale(3));
			Assert.assertEquals((new BigDecimal("0")).setScale(3), this.transactionOperations.getSystemProfit());
			
			this.generalOperations.time(2);
			Assert.assertEquals(initialTime, this.orderOperations.getSentTime(order));
			Assert.assertNull(this.orderOperations.getRecievedTime(order));
			Assert.assertEquals(this.orderOperations.getLocation(order), cityA);
			
			this.generalOperations.time(9);
			Assert.assertEquals(this.orderOperations.getLocation(order), cityA);
			
			this.generalOperations.time(8);
			Assert.assertEquals(this.orderOperations.getLocation(order), cityC5);
			
			this.generalOperations.time(5);
			Assert.assertEquals(this.orderOperations.getLocation(order), cityB);
			Assert.assertEquals(receivedTime, this.orderOperations.getRecievedTime(order));
			
			Assert.assertEquals(shopAAmountReal, this.transactionOperations.getShopTransactionsAmmount(shopA));
			Assert.assertEquals(shopC2AmountReal, this.transactionOperations.getShopTransactionsAmmount(shopC2));
			Assert.assertEquals(shopC3AmountReal, this.transactionOperations.getShopTransactionsAmmount(shopC3));
			Assert.assertEquals(systemProfit, this.transactionOperations.getSystemProfit());
			
			int shopATransactionId = this.transactionOperations.getTransactionForShopAndOrder(order, shopA);
			Assert.assertNotEquals(-1L, shopATransactionId);
			Assert.assertEquals(receivedTime, this.transactionOperations.getTimeOfExecution(shopATransactionId));
		} catch (AssertionError e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}
}


/* Location:              D:\repos\SAB-Project\SAB_project_2223.jar!\rs\etf\sab\tests\PublicModuleTest.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */