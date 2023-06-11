package rs.etf.sab.tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import rs.etf.sab.operations.ArticleOperations;
import rs.etf.sab.operations.CityOperations;
import rs.etf.sab.operations.GeneralOperations;
import rs.etf.sab.operations.ShopOperations;

import java.util.List;

public class ShopOperationsTest {
	private TestHandler testHandler;
	private GeneralOperations generalOperations;
	private ShopOperations shopOperations;
	private CityOperations cityOperations;
	private ArticleOperations articleOperations;
	
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
		
		this.generalOperations = this.testHandler.getGeneralOperations();
		Assert.assertNotNull(this.generalOperations);
		
		this.generalOperations.eraseAll();
	}
	
	@After
	public void tearDown() throws Exception {
		this.generalOperations.eraseAll();
	}
	
	@Test
	public void createShop() {
		int cityId = this.cityOperations.createCity("Kragujevac");
		Assert.assertNotEquals(-1L, cityId);
		int shopId = this.shopOperations.createShop("Gigatron", "Kragujevac");
		Assert.assertEquals(shopId, this.cityOperations.getShops(cityId).get(0).intValue());
	}
	
	@Test
	public void setCity() {
		this.cityOperations.createCity("Kragujevac");
		int shopId = this.shopOperations.createShop("Gigatron", "Kragujevac");
		int cityId2 = this.cityOperations.createCity("Subotica");
		
		this.shopOperations.setCity(shopId, "Subotica");
		Assert.assertEquals(shopId, this.cityOperations.getShops(cityId2).get(0).intValue());
	}
	
	@Test
	public void discount() {
		this.cityOperations.createCity("Kragujevac");
		int shopId = this.shopOperations.createShop("Gigatron", "Kragujevac");
		this.shopOperations.setDiscount(shopId, 20);
		Assert.assertEquals(20L, this.shopOperations.getDiscount(shopId));
	}
	
	@Test
	public void articles() {
		this.cityOperations.createCity("Kragujevac");
		int shopId = this.shopOperations.createShop("Gigatron", "Kragujevac");
		
		int articleId = this.articleOperations.createArticle(shopId, "Olovka", 10);
		Assert.assertNotEquals(-1L, articleId);
		
		int articleId2 = this.articleOperations.createArticle(shopId, "Gumica", 5);
		Assert.assertNotEquals(-1L, articleId2);
		
		this.shopOperations.increaseArticleCount(articleId, 5);
		this.shopOperations.increaseArticleCount(articleId, 2);
		int articleCount = this.shopOperations.getArticleCount(articleId);
		Assert.assertEquals(7L, articleCount);
		
		List<Integer> articles = this.shopOperations.getArticles(shopId);
		Assert.assertEquals(2L, articles.size());
		Assert.assertTrue((articles.contains(Integer.valueOf(articleId)) && articles.contains(Integer.valueOf(articleId2))));
	}
}


/* Location:              D:\repos\SAB-Project\SAB_project_2223.jar!\rs\etf\sab\tests\ShopOperationsTest.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */