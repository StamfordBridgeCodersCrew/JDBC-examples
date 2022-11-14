package JDBC1_Statement_Demo;

import java.sql.*;

public class SelectDemo1 {
    private static final String SELECT_QUERY1 =
        "SELECT id, name, email, country, password " +
        "FROM Users1 " +
        "ORDER BY id ASC;";

    public static void main(String[] args) throws SQLException {
        SelectDemo1 example1 = new SelectDemo1();
        example1.fetchRecordInfo();
    }

    public void fetchRecordInfo() throws SQLException {

        // MySQL Connection Parameters
        String db_uri = "jdbc:mysql://localhost:3306/mysqljdbc?useSSL=false";
        String db_user = "root";
        String db_password = "root";

        // 1. Establish Connection
        try (Connection connection = DriverManager.getConnection(db_uri, db_user, db_password)) {
            // 2. Create Statement object (stmt) from Connection object (connection)
            Statement stmt = connection.createStatement();

            // 3. Execute the SELECT Query using stmt.executeQuery()
            // rs --> ResultSet object
            ResultSet rs = stmt.executeQuery(SELECT_QUERY1);

            // 4. Iterate over the ResultSet object
            // Fetch info of each record
            while (rs.next()) {
                // rs --> Current record of the ResultSet
                // Extract all the columns from this current record

                // Users1(id, name, email, country, password)
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                String password = rs.getString("password");

                System.out.println(id+", "+name+", "+email+", "+country+", "+password);
            }
        }
        catch (SQLDataException e) {
            // Print MySQL Exception info
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
