package JDBC1_Statement_Demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class BatchInsert {
    private static final String INSERT_QUERY1 = "INSERT INTO Users1 VALUES (6, 'Ruben Dias', 'ruben@gmail.com', 'Portugal', '123');";
    private static final String INSERT_QUERY2 = "INSERT INTO Users1 VALUES (7, 'Nathan Ake', 'nathan@gmail.com', 'Netherlands', '123');";
    private static final String INSERT_QUERY3 = "INSERT INTO Users1 VALUES (8, 'Benjamin Mendy', 'benjamin@gmail.com', 'France', '123');";
    private static final String INSERT_QUERY4 = "INSERT INTO Users1 VALUES (9, 'Ilkay Gundogan', 'gundogan@gmail.com', 'Germany', '123');";
    private static final String INSERT_QUERY5 = "INSERT INTO Users1 VALUES (10, 'Phil Foden', 'foden@gmail.com', 'England', '123');";
    private static final String INSERT_QUERY6 = "INSERT INTO Users1 VALUES (11, 'Gabriel Jesus', 'jesus@gmail.com', 'Brazil', '123');";

    public static void main(String[] args) {
        BatchInsert example1 = new BatchInsert();
        example1.batchInsertDemo();
    }

    private void batchInsertDemo() {
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
            stmt.addBatch(INSERT_QUERY1);
            stmt.addBatch(INSERT_QUERY2);
            stmt.addBatch(INSERT_QUERY3);
            stmt.addBatch(INSERT_QUERY4);
            stmt.addBatch(INSERT_QUERY5);
            stmt.addBatch(INSERT_QUERY6);

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
