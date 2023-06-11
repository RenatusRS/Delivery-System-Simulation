package rs.etf.sab.tests;

import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;


public final class TestRunner {
	private static final int MAX_POINTS_ON_PUBLIC_TEST = 10;
	private static final Class[] UNIT_TEST_CLASSES = new Class[]{BuyerOperationsTest.class, CityOperationsTest.class, GeneralOperationsTest.class, ShopOperationsTest.class};
	
	
	private static final Class[] UNIT_TEST_CLASSES_PRIVATE = new Class[0];
	
	
	private static final Class[] MODULE_TEST_CLASSES = new Class[]{PublicModuleTest.class};
	private static final Class[] MODULE_TEST_CLASSES_PRIVATE = new Class[0];
	
	
	private static double runUnitTestsPublic() {
		int numberOfSuccessfulCases = 0;
		int numberOfAllCases = 0;
		double points = 0.0D;
		JUnitCore jUnitCore = new JUnitCore();
		
		for (Class testClass : UNIT_TEST_CLASSES) {
			System.out.println("\n" + testClass.getName());
			
			Request request = Request.aClass(testClass);
			Result result = jUnitCore.run(request);
			
			numberOfAllCases = result.getRunCount();
			numberOfSuccessfulCases = result.getRunCount() - result.getFailureCount();
			if (numberOfSuccessfulCases < 0)
				numberOfSuccessfulCases = 0;
			System.out.println("Successful: " + numberOfSuccessfulCases);
			System.out.println("All: " + numberOfAllCases);
			double points_curr = numberOfSuccessfulCases * 6.0D / numberOfAllCases / UNIT_TEST_CLASSES.length;
			System.out.println("Points: " + points_curr);
			points += points_curr;
		}
		
		
		return points;
	}
	
	private static double runModuleTestsPublic() {
		int numberOfSuccessfulCases = 0;
		int numberOfAllCases = 0;
		double points = 0.0D;
		JUnitCore jUnitCore = new JUnitCore();
		
		for (Class testClass : MODULE_TEST_CLASSES) {
			System.out.println("\n" + testClass.getName());
			
			Request request = Request.aClass(testClass);
			Result result = jUnitCore.run(request);
			
			numberOfAllCases = result.getRunCount();
			numberOfSuccessfulCases = result.getRunCount() - result.getFailureCount();
			if (numberOfSuccessfulCases < 0)
				numberOfSuccessfulCases = 0;
			System.out.println("Successful: " + numberOfSuccessfulCases);
			System.out.println("All: " + numberOfAllCases);
			double points_curr = (numberOfSuccessfulCases / MODULE_TEST_CLASSES.length * 4);
			System.out.println("Points: " + points_curr);
			points += points_curr;
		}
		
		
		return points;
	}
	
	private static double runPublic() {
		double res = 0.0D;
		res += runUnitTestsPublic();
		res += runModuleTestsPublic();
		return res;
	}
	
	private static double runUnitTestsPrivate() {
		int numberOfSuccessfulCases = 0;
		int numberOfAllCases = 0;
		double points = 0.0D;
		JUnitCore jUnitCore = new JUnitCore();
		
		for (Class testClass : UNIT_TEST_CLASSES_PRIVATE) {
			System.out.println("\n" + testClass.getName());
			
			Request request = Request.aClass(testClass);
			Result result = jUnitCore.run(request);
			
			numberOfAllCases = result.getRunCount();
			numberOfSuccessfulCases = result.getRunCount() - result.getFailureCount();
			if (numberOfSuccessfulCases < 0)
				numberOfSuccessfulCases = 0;
			System.out.println("Successful: " + numberOfSuccessfulCases);
			System.out.println("All: " + numberOfAllCases);
			double points_curr = numberOfSuccessfulCases * 2.0D / numberOfAllCases / UNIT_TEST_CLASSES_PRIVATE.length;
			System.out.println("Points: " + points_curr);
			points += points_curr;
		}
		
		return points;
	}
	
	private static double runModuleTestsPrivate() {
		int numberOfSuccessfulCases = 0;
		int numberOfAllCases = 0;
		double points = 0.0D;
		JUnitCore jUnitCore = new JUnitCore();
		
		for (Class testClass : MODULE_TEST_CLASSES_PRIVATE) {
			System.out.println("\n" + testClass.getName());
			
			Request request = Request.aClass(testClass);
			Result result = jUnitCore.run(request);
			
			numberOfAllCases = result.getRunCount();
			numberOfSuccessfulCases = result.getRunCount() - result.getFailureCount();
			if (numberOfSuccessfulCases < 0)
				numberOfSuccessfulCases = 0;
			System.out.println("Successful:" + numberOfSuccessfulCases);
			System.out.println("All:" + numberOfAllCases);
			double points_curr = numberOfSuccessfulCases * 8.0D / numberOfAllCases / MODULE_TEST_CLASSES_PRIVATE.length;
			System.out.println("Points: " + points_curr);
			points += points_curr;
		}
		
		return points;
	}
	
	
	private static double runPrivate() {
		double res = 0.0D;
		res += runUnitTestsPrivate();
		res += runModuleTestsPrivate();
		return res;
	}
	
	public static void runTests() {
		double resultsPublic = runPublic();
		System.out.println("Points won on public tests is: " + resultsPublic + " out of 10");
	}
}


/* Location:              D:\repos\SAB-Project\SAB_project_2223.jar!\rs\etf\sab\tests\TestRunner.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */