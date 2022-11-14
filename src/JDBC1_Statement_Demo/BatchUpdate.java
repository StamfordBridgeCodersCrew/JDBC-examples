package JDBC1_Statement_Demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class BatchUpdate {
    private static final String UPDATE_QUERY1 = "UPDATE Users1 SET name = \"Jack Grealish\" WHERE id = 1;";
    private static final String UPDATE_QUERY2 = "UPDATE Users1 SET name = \"Bernardo Silva\" WHERE id = 2;";
    private static final String UPDATE_QUERY3 = "UPDATE Users1 SET name = \"Fernandinho\" WHERE id = 3;";
    private static final String UPDATE_QUERY4 = "UPDATE Users1 SET name = \"Raheem Sterling\" WHERE id = 4;";
    private static final String UPDATE_QUERY5 = "UPDATE Users1 SET name = \"Riyad Mahrez\" WHERE id = 5;";
    private static final String UPDATE_QUERY6 = "UPDATE Users1 SET name = \"Pep Guardiola\" WHERE id = 6;";

    public static void main(String[] args) {
        BatchUpdate example1 = new BatchUpdate();
        example1.batchUpdateDemo();
    }

    private void batchUpdateDemo() {

        // MySQL Connection Parameters
        String db_uri = "jdbc:mysql://localhost:3306/mysqljdbc?useSSL=false";
        String db_user = "root";
        String db_password = "root";

        // 1. Establishing Connection
        try (Connection connection = DriverManager.getConnection(db_uri, db_user, db_password)) {

            // 2. Create Statement object (stmt) from Connection object(connection)
            Statement stmt = connection.createStatement();
            connection.setAutoCommit(false);

            // Add the SQL Commands to the Batch
            stmt.addBatch(UPDATE_QUERY1);
            stmt.addBatch(UPDATE_QUERY2);
            stmt.addBatch(UPDATE_QUERY3);
            stmt.addBatch(UPDATE_QUERY4);
            stmt.addBatch(UPDATE_QUERY5);
            stmt.addBatch(UPDATE_QUERY6);

            // Submit the SQL Commands in the batch to the DB
            int[] rows_affected = stmt.executeBatch();
            System.out.println("Rows affected corresponding to each SQL Query in the Batch = "+ Arrays.toString(rows_affected));

            connection.commit();
        }
        catch (SQLException e) {
            // Print SQL Exception Info
            printSQLException(e);
        }
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
