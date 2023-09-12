import java.sql.*;
public class DatabaseConnection
{

    private static final String URL = "jdbc:mysql://localhost:3306/JavaBank?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "durda1130";

    public static Connection getConnection() throws SQLException
    {

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }


    public static void main(String[] args)
    {
        Connection testcon = null;

        try
        {
            testcon = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("/" + testcon + "/");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
