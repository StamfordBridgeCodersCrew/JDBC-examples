package JDBC3_BLOB_CLOB;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Logger;

public class BLOBDemo1 {
    private static final String SQL_QUERY =
            "INSERT INTO teams\n\r"+
            "VALUES (?,?);";

    public static void main(String[] args) {
        BLOBDemo1 demo = new BLOBDemo1();
        demo.insertBlobData();
    }

    public void insertBlobData() {
        // MySQL Connection Parameters
        String db_uri = "jdbc:mysql://localhost:3306/mysqljdbc?useSSL=false";
        String db_user = "root";
        String db_password = "root";

        try (Connection connection = DriverManager.getConnection(db_uri, db_user, db_password)) {
            // 2. Create a PreparedStatement pstmt object uing Connection object connection
            // At this line the query is compiled
            PreparedStatement pstmt = connection.prepareStatement(SQL_QUERY);

            Scanner Sc = new Scanner(System.in);
            while (true)
            {
                // Taking the User Inputs
                System.out.print("\nEnter team name : ");
                String team_name = Sc.nextLine();
                System.out.print("\nEnter team image file : ");
                String file_name = Sc.nextLine();

                // 3. Since we have a Dynamic SQL Query
                // Tis time to fill in the Positional parameters using pstmt
                // If we dont fill even 1 Positional parameter , We will get SQLException
                pstmt.setString(1, team_name);

                File f = new File(file_name);
                try (FileInputStream fis = new FileInputStream(f)) {
                    pstmt.setBinaryStream(2, fis, (int)f.length());
                }catch (IOException e) {
                    pstmt.setString(2, file_name);
                    System.out.println("IOException = "+e.getMessage());
                }

                // 4. Execute the SQL Query using pstmt
                // We are executing Non Select Query --> Hence use pstmt.executeUpdate()
                int rows_affected = pstmt.executeUpdate();

                if (rows_affected == 1)
                    System.out.println("Record inserted !!!");
                else
                    System.out.println("Record not inserted !!!");
            }
        }
        catch (SQLException e) {
            // Print SQL Exception Info
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
