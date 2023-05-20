package rs.etf.sab.student;

import rs.etf.sab.operations.*;
import org.junit.Test;
import rs.etf.sab.tests.TestHandler;
import rs.etf.sab.tests.TestRunner;

import java.util.Calendar;

public class StudentMain {

    public static void main(String[] args) {

        ArticleOperations articleOperations = new ArticleOperationsImpl(); // Change this for your implementation (points will be negative if interfaces are not implemented).
        BuyerOperations buyerOperations = new BuyerOperationsImpl();
        CityOperations cityOperations = new CityOperationsImpl();
        GeneralOperations generalOperations = new GeneralOperationsImpl();
        OrderOperations orderOperations = new OrderOperationsImpl();
        ShopOperations shopOperations = new ShopOperationsImpl();
        TransactionOperations transactionOperations = new TransactionOperationsImpl();

        TestHandler.createInstance(
                articleOperations,
                buyerOperations,
                cityOperations,
                generalOperations,
                orderOperations,
                shopOperations,
                transactionOperations
        );

        TestRunner.runTests();
    }
}
