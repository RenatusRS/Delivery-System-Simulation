package rs.etf.sab.student;

import rs.etf.sab.operations.*;
import rs.etf.sab.student.utils.DB;
import rs.etf.sab.tests.TestHandler;
import rs.etf.sab.tests.TestRunner;

import java.sql.SQLException;

public class StudentMain {
    public static final GeneralOperations generalOperations = new GeneralOperations();

    public static void main(String[] args) {
        
        try {
            DB.setConnection("SAB2223", "localhost", 1433);
        } catch (SQLException e) {
            System.out.println("Connection to database failed.");
            e.printStackTrace();
            return;
        }
        
        ArticleOperations articleOperations = new ArticleOperations(); // Change this for your implementation (points will be negative if interfaces are not implemented).
        BuyerOperations buyerOperations = new BuyerOperations();
        CityOperations cityOperations = new CityOperations();
        OrderOperations orderOperations = new OrderOperations();
        ShopOperations shopOperations = new ShopOperations();
        TransactionOperations transactionOperations = new TransactionOperations();
        
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
