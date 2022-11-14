package JDBC1_Statement_Demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateRecord1 {

    private static final String UPDATE_USERS_SQL =
            "UPDATE Users1 "+
            "SET name = \"Memphis Depay\" "+
            "WHERE id = 1;";

    public static void main(String[] args) {
        UpdateRecord1 example1 = new UpdateRecord1();
        example1.updateRecords();
    }

    public void updateRecords() {

        // MySQL Connection Parameters
        String db_uri = "jdbc:mysql://localhost:3306/mysqljdbc?useSSL=false";
        String user = "root";
        String password = "root";

        // 1. Establishing Connection
        try (Connection connection = DriverManager.getConnection(db_uri, user, password)) {

            // 2. Creating Statement object using Connection object connection
            Statement stmt = connection.createStatement();

            // 3. Execute the SQL Query using the Statement Object stmt
            int rows_affected = stmt.executeUpdate(UPDATE_USERS_SQL);
            System.out.println("No of rows affected = "+rows_affected);
        }
        catch (SQLException e) {
            // Print SQL Exception info
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


