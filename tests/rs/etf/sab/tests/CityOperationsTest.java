package rs.etf.sab.tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import rs.etf.sab.operations.CityOperations;
import rs.etf.sab.operations.GeneralOperations;
import rs.etf.sab.operations.ShopOperations;

import java.util.List;


public class CityOperationsTest {
	private TestHandler testHandler;
	private GeneralOperations generalOperations;
	private CityOperations cityOperations;
	private ShopOperations shopOperations;
	
	@Before
	public void setUp() throws Exception {
		this.testHandler = TestHandler.getInstance();
		Assert.assertNotNull(this.testHandler);
		
		this.cityOperations = this.testHandler.getCityOperations();
		Assert.assertNotNull(this.cityOperations);
		
		this.generalOperations = this.testHandler.getGeneralOperations();
		Assert.assertNotNull(this.generalOperations);
		
		
		this.shopOperations = this.testHandler.getShopOperations();
		Assert.assertNotNull(this.shopOperations);
		
		this.generalOperations.eraseAll();
	}
	
	@After
	public void tearDown() throws Exception {
		this.generalOperations.eraseAll();
	}
	
	@Test
	public void createCity() {
		int cityVranje = this.cityOperations.createCity("Vranje");
		Assert.assertEquals(1L, this.cityOperations.getCities().size());
		Assert.assertEquals(cityVranje, this.cityOperations.getCities().get(0).intValue());
	}
	
	@Test
	public void insertShops() {
		int cityId = this.cityOperations.createCity("Vranje");
		
		int shopId1 = this.shopOperations.createShop("Gigatron", "Vranje");
		int shopId2 = this.shopOperations.createShop("Teranova", "Vranje");
		
		List<Integer> shops = this.cityOperations.getShops(cityId);
		Assert.assertEquals(2L, shops.size());
		Assert.assertTrue((shops.contains(Integer.valueOf(shopId1)) && shops.contains(Integer.valueOf(shopId2))));
	}
	
	@Test
	public void connectCities() {
		int cityVranje = this.cityOperations.createCity("Vranje");
		int cityLeskovac = this.cityOperations.createCity("Leskovac");
		int cityNis = this.cityOperations.createCity("Nis");
		
		Assert.assertNotEquals(-1L, cityLeskovac);
		Assert.assertNotEquals(-1L, cityVranje);
		Assert.assertNotEquals(-1L, cityNis);
		
		this.cityOperations.connectCities(cityNis, cityVranje, 50);
		this.cityOperations.connectCities(cityVranje, cityLeskovac, 70);
		
		List<Integer> connectedCities = this.cityOperations.getConnectedCities(cityVranje);
		Assert.assertEquals(2L, connectedCities.size());
		Assert.assertTrue((connectedCities.contains(Integer.valueOf(cityLeskovac)) && connectedCities.contains(Integer.valueOf(cityNis))));
	}
}


/* Location:              D:\repos\SAB-Project\SAB_project_2223.jar!\rs\etf\sab\tests\CityOperationsTest.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */