# Online Retail Simulation System
> Project for **Database Software Tools** / **Softverski Alati Baza Podataka** class.

Simulation of an online retail system that serves as an intermediary between customers and shops. It's developed in **Java 1.8** and uses **SQL Server Management Studio 17.0** for database management, including stored procedures and triggers in **Transact-SQL**.

For more details on class structures and methods, refer to the documentation [here](https://renatusrs.github.io/SAB-Project/).

## Features
- **Multi-Location Support**: Shops can operate in multiple cities.
- **Multi-Location Ordering**: Orders can contain items from multiple shops.
- **Order Status**: Tracks orders through the states of "created", "sent", and "arrived".
- **Payment Transactions**: Utilizes a virtual account system with transaction logs.
- **Dynamic Discounts**: Offers variable discounts based on shop and customer activity.
- **Optimized Delivery**: Uses a two-step delivery process for efficiency.

## Requirements
- **Java JDK 1.8**: For all business logic and class implementations.
- **SQL Server Management Studio 17.0**: For database management and running stored procedures and triggers in Transact-SQL.

## How-To Run
1. Restore the database from `database.bak` found in the `sql` folder using **SQL Server Management Studio**.
   - *Alternative*: Use the `database.sql` file.
2. Set database connection details in `studentMain.java` and `utils/DB.java`.
3. Include libraries from the `lib` folder in the project.
4. Run `studentMain.java`.
