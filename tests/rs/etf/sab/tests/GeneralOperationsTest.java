package rs.etf.sab.tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import rs.etf.sab.operations.GeneralOperations;

import java.util.Calendar;


public class GeneralOperationsTest {
	private TestHandler testHandler;
	private GeneralOperations generalOperations;
	
	@Before
	public void setUp() throws Exception {
		this.testHandler = TestHandler.getInstance();
		Assert.assertNotNull(this.testHandler);
		
		this.generalOperations = this.testHandler.getGeneralOperations();
		Assert.assertNotNull(this.generalOperations);
		
		this.generalOperations.eraseAll();
	}
	
	@After
	public void tearDown() throws Exception {
		this.generalOperations.eraseAll();
	}
	
	@Test
	public void general() {
		Calendar time = Calendar.getInstance();
		time.clear();
		time.set(2018, 0, 1);
		this.generalOperations.setInitialTime(time);
		
		Calendar currentTime = this.generalOperations.getCurrentTime();
		Assert.assertEquals(time, currentTime);
		
		this.generalOperations.time(40);
		currentTime = this.generalOperations.getCurrentTime();
		Calendar newTime = Calendar.getInstance();
		newTime.clear();
		newTime.set(2018, 1, 10);
		Assert.assertEquals(currentTime, newTime);
	}
}


/* Location:              D:\repos\SAB-Project\SAB_project_2223.jar!\rs\etf\sab\tests\GeneralOperationsTest.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */