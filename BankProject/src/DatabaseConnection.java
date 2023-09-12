import java.sql.*;
public class DatabaseConnection
{

    private static final String URL = "jdbc:mysql://localhost:3306/JavaBank?allowPublicKeyRetrieval=true&useSSL=false";
    private static final String USER = "bot22";
    private static final String PASSWORD = "Access";

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
