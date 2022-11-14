package JDBC2_Prepared_Statement_Demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class DeleteRecord {
    private static final String DELETE_USERS_SQL =
            "DELETE FROM Users1\n"+
            "WHERE name = ?;";

    public static void main(String[] args) {
        DeleteRecord demo1 = new DeleteRecord();
        demo1.deleteRecords();
    }

    public void deleteRecords() {
        // MySQL Connection parameters
        String db_uri = "jdbc:mysql://localhost:3306/mysqljdbc?useSSL=false";
        String db_user = "root";
        String db_password = "root";

        // 1. Establish Connection with MySQL
        try (Connection connection = DriverManager.getConnection(db_uri, db_user, db_password)) {
            // 2. Make a PreparedStatement object pstmt from Connection object connection
            // At this line , Query is compiled
            PreparedStatement pstmt = connection.prepareStatement(DELETE_USERS_SQL);

            // Now we will take input from the User
            Scanner Sc = new Scanner(System.in);

            while (true)
            {
                // Taking the User Input
                System.out.print("\nEnter name of user to be deleted : ");
                String name = Sc.nextLine();
                //Sc.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

                // 3. Since we have used Dynamic SQL Query
                // Now tis time to fill in the Positional Parameters using pstmt
                // If we dont fill even 1 Positional Parameter , We will get SQL Exception
                pstmt.setString(1, name);

                // 4. Execute the SQL Query using pstmt
                // We are executing Non Select Queries --> Hence pstmt.executeUpdate()
                int rows_affected = pstmt.executeUpdate();
                System.out.println("Records containing name = "+name+" is deleted ");
                System.out.println("Number of rows deleted = "+rows_affected);

                // Remaining tasks
                System.out.println("Do U wanna delete more records [yes/no] ?? ");
                String option = Sc.nextLine();

                if(option.equalsIgnoreCase("no"))
                    break;
            }
        }
        catch (SQLException e) {
            // Print SQL Exception info
            printSQLException(e);
        }
        // 5. try-with-resource statement will auto close the opened connection.
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
