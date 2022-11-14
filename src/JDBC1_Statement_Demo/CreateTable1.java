package JDBC1_Statement_Demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable1
{
    private static final String create_table_mysql =
            "CREATE TABLE Users1(\r\n" +
                "id  int(3) primary key,\r\n" +
                "name varchar(20),\r\n" + "email varchar(20),\r\n" + "country varchar(20),\r\n" +
                "password varchar(20)\r\n" +
            ");";

    public static void main(String[] args) throws SQLException {
        CreateTable1 example1 = new CreateTable1();
        example1.createTable1();
    }

    public void createTable1() throws SQLException {

        // MySQL JDBC Connection parameters
        String db_uri = "jdbc:mysql://localhost:3306/mysqljdbc?useSSL=false";
        String user = "root";
        String password = "root";

        // 1. Establishing Connection with MySQL
        try (Connection connection = DriverManager.getConnection(db_uri, user, password) ) {

            // 2. Create a Statement object(stmt) using Connection object (connection)
            Statement stmt = connection.createStatement();

            // 3. Execute the SQL Query
            stmt.execute(create_table_mysql);
        }
        catch (SQLException e) {
            // Print SQL Exception information
            // e.printStackTrace();
            printSQLException(e);
        }
        // 4. try-with-resource statement will auto close the opened connection.
    }

    public static void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}