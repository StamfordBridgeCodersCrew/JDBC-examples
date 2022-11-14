package JDBC1_Statement_Demo;

import java.sql.*;

public class InsertMultipleRecords1 {

    private static final String INSERT_MULTIPLE_USERS_SQL =
            "INSERT INTO Users1 " +
            "VALUES (1, 'Marcos Alonso', 'marcus@gmail.com', 'Spain', '123'),"
            + "(2, 'Thiago Silva', 'thiago@gmail.com', 'Brazil', '123'),"
            + "(3, 'Jorginho', 'jorginho@gmail.com', 'Italy', '123'),"
            + "(4, 'Romelu Lukaku', 'romlu@gmail.com', 'Belgium', '123'),"
            + "(5, 'kai Havertz', 'havertz@gmail.com', 'Germany', '123');";

    public static void main(String[] args) throws SQLException{
        InsertMultipleRecords1 example1 = new InsertMultipleRecords1();
        example1.insertMultipleRecords();
    }

    public void insertMultipleRecords() throws SQLException {
        // MySQL Connection Parameters
        String db_uri = "jdbc:mysql://localhost:3306/mysqljdbc?useSSL=false";
        String user = "root";
        String password = "root";

        // 1. Establishing Connection
        try (Connection connection = DriverManager.getConnection(db_uri, user, password)) {

            // 2. Create a Statement object (stmt) from a Connection object (connection)
            Statement stmt = connection.createStatement();

            // 3. Execute the SQL Query using Statement object stmt
            int rows_affected = stmt.executeUpdate(INSERT_MULTIPLE_USERS_SQL);
            System.out.println("No of rows affected = "+rows_affected);
        }
        // 4. try-with-resource statement will auto close the opened connection.

        catch (SQLException e) {
            // Print SQL Exception info
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
