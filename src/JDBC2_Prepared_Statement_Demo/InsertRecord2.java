// Program 2 , Pg 5

package JDBC2_Prepared_Statement_Demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class InsertRecord2 {
    private static final String INSERT_USERS_SQL =
            "INSERT INTO Users1(id, name, email, country, password)\n\r"+
            "VALUES (?, ?, ?, ?, ?);";

    public static void main(String[] args) {
        InsertRecord2 demo1 = new InsertRecord2();
        demo1.insertRecords();
    }

    public void insertRecords() {
        // MySQL Connection Parameters
        String db_uri = "jdbc:mysql://localhost:3306/mysqljdbc?useSSL=false";
        String db_user = "root";
        String db_password = "root";

        // 1. Establish connection with MySQL
        // Create the Connection object
        try (Connection connection = DriverManager.getConnection(db_uri, db_user, db_password)) {

            // 2. Create a PreparedStatement pstmt object uing Connection object connection
            // At this line the query is compiled
            PreparedStatement pstmt = connection.prepareStatement(INSERT_USERS_SQL);

            // Getting values from user input
            Scanner Sc = new Scanner(System.in);

            while (true)
            {
                // Take the user inputs
                System.out.print("\nEnter User id : ");
                int id = Sc.nextInt();
                Sc.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

                System.out.print("\nEnter User name : ");
                String name = Sc.nextLine();

                System.out.print("\nEnter User email : ");
                String email = Sc.nextLine();

                System.out.print("\nEnter User country : ");
                String country = Sc.nextLine();

                System.out.print("\nEnter User password : ");
                String password = Sc.nextLine();

                // 3. Since we have used Dynamic SQL Query
                // Now tis time to fill in the Positional Parameters using pstmt
                // If we dont fill even 1 Positional Parameter , We will get SQL Exception
                pstmt.setInt(1, id);
                pstmt.setString(2, name);
                pstmt.setString(3, email);
                pstmt.setString(4, country);
                pstmt.setString(5, password);

                // 4. Execute the SQL Query using pstmt
                // We are executing Non Select Queries --> Hence pstmt.executeUpdate()
                int rows_affected = pstmt.executeUpdate();
                System.out.println("Record inserted successfully !!!");
                System.out.println("Number of records affected = "+rows_affected);

                // Remaining tasks
                System.out.println("Do U wanna insert more records [yes/no] ?? ");
                String option = Sc.next();

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
