package rs.etf.sab.student;

import rs.etf.sab.operations.*;
import rs.etf.sab.student.utils.DB;
import rs.etf.sab.tests.TestHandler;
import rs.etf.sab.tests.TestRunner;

import java.sql.SQLException;

public class StudentMain {
    public static final GeneralOperations generalOperations = new lu190691_GeneralOperations();

    public static void main(String[] args) {
        
        try {
            DB.setConnection("SAB2223", "localhost", 1433);
        } catch (SQLException e) {
            System.out.println("Connection to database failed.");
            e.printStackTrace();
            return;
        }
        
        ArticleOperations articleOperations = new lu190691_ArticleOperations(); // Change this for your implementation (points will be negative if interfaces are not implemented).
        BuyerOperations buyerOperations = new lu190691_BuyerOperations();
        CityOperations cityOperations = new lu190691_CityOperations();
        OrderOperations orderOperations = new lu190691_OrderOperations();
        ShopOperations shopOperations = new lu190691_ShopOperations();
        TransactionOperations transactionOperations = new lu190691_TransactionOperations();
        
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
