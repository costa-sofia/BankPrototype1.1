import java.sql.*;
public class Account
{
    int accountNumber;
    String accountHolder;
    double accountBalance = 0;

    /*Constructors*/
    public Account(String accountHolder, double amt)
    {
        boolean isValid = false;

        try
        {
            // connection
            Connection testcon = DatabaseConnection.getConnection();
            String query = "SELECT COUNT(accnumber) FROM accounts WHERE accname = ?;";
            PreparedStatement preparedStatement = testcon.prepareStatement(query);
            preparedStatement.setString(1, accountHolder);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
            {
                int count = resultSet.getInt(1);
                isValid = count == 0; // If count equals 0, the holder do not have an account yet
            }

            preparedStatement.close();

            if (isValid)
            {

                this.accountBalance = amt;
                this.accountHolder = accountHolder;
                try (CallableStatement calStmt = testcon.prepareCall("{call createAccount(?, ?)}")) {
                    // Set parameters
                    calStmt.setString(1, accountHolder);
                    calStmt.setDouble(2, amt);

                    // Execute the stored procedure
                    calStmt.execute();

                    // Retrieve the result set from the stored procedure call
                    try (ResultSet rstSet = calStmt.getResultSet()) {
                        // Process and display the results
                        while (rstSet.next()) {
                            this.accountNumber = rstSet.getInt("accnumber");
                            System.out.println("Account Number: " + rstSet.getInt("accnumber") + " | Account Holder: " + this.accountHolder + " | Balance: $" + this.accountBalance);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
                System.out.println("Account holder name already in use");
                // Handle the invalid account case
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Account(int accountNumber)
    {
        boolean isValid = false;

        try
        {

            // connection
            Connection testcon = DatabaseConnection.getConnection();


            String query = "SELECT COUNT(*) FROM accounts WHERE accnumber = ?";
            PreparedStatement preparedStatement = testcon.prepareStatement(query);
            preparedStatement.setInt(1, accountNumber);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
            {
                int count = resultSet.getInt(1);
                isValid = count > 0; // If count > 0, the account number is valid
            }

            preparedStatement.close();

            if (isValid) {
                // CallableStatement in parameters
                CallableStatement calStmt = null;

                resultSet = null;

                // prepare the stored procedure call
                calStmt = testcon.prepareCall("{call detailsAccount(?)}");
                //calStmt = testcon.prepareCall("{call detailsAccount(account_number)}");

                // set parameters
                calStmt.setInt(1, this.accountNumber);

                // calling stored procedure
                calStmt.execute();

                // Retrieve the result set from the stored procedure call
                resultSet = calStmt.getResultSet();

                while (resultSet.next())
                {
                    this.accountNumber = resultSet.getInt("accnumber");
                    this.accountHolder = resultSet.getString("accname");
                    this.accountBalance = resultSet.getDouble("accbalance");
                    System.out.println("|---Account Number: " +  resultSet.getInt("accnumber") + " | Account Holder: " + resultSet.getString("accname") + " | Balance: $" + resultSet.getDouble("accbalance"));
                }

                // Close resources
                resultSet.close();
                calStmt.close();
            }
            else
            {
                System.out.println("|--------Invalid account number-------|");
                // Handle the invalid account case
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //deposit method
    void deposit(double amt) {
        this.accountBalance = this.accountBalance + amt;
        System.out.println("$" + amt + " Credited | Balance: $" + this.accountBalance);
        System.out.println("|-------------------------------------|");

        try {

            // Get a database connection
            Connection testcon = DatabaseConnection.getConnection();

            //turn off auto commit
            testcon.setAutoCommit(false);

            // Create a prepared statement
            PreparedStatement updateStmt = testcon.prepareStatement("UPDATE accounts SET accbalance = ? WHERE accnumber = ?");


            // Set the new balance and account number as parameters
            updateStmt.setDouble(1, this.accountBalance);
            updateStmt.setInt(2, this.accountNumber);

            // Execute the update
            int rowsUpdated = updateStmt.executeUpdate();

            if (rowsUpdated > 0)
            {
                System.out.println("|---Balance updated in the database---|");
            }
            else
            {
                System.out.println("|---Failed to update balance in the database---|");
            }

            // Commit the transaction
            testcon.commit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    //withdraw method
    void withdraw(double amt)
    {
        if(this.accountBalance >= amt)
        {
            this.accountBalance = this.accountBalance - amt;
            System.out.println("$" + amt + " Debited | Balance: $" + this.accountBalance);

            try {
                // Get a database connection
                Connection testcon = DatabaseConnection.getConnection();

                // Create a prepared statement
                PreparedStatement updateStmt = testcon.prepareStatement("UPDATE accounts SET accbalance = ? WHERE accnumber = ?");

                //turn off auto commit
                testcon.setAutoCommit(false);

                // Set the new balance and account number as parameters
                updateStmt.setDouble(1, this.accountBalance);
                updateStmt.setInt(2, this.accountNumber); // Replace with the actual account number field name

                // Execute the update
                int rowsUpdated = updateStmt.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("Balance updated in the database.");
                } else {
                    System.out.println("Failed to update balance in the database.");
                }

                // Commit the transaction
                testcon.commit();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {

            System.out.println("ERROR: Insufficient Balance");
        }
    }

    //getting account details method
    void getDetails() throws Exception {
        try (Connection testcon = DatabaseConnection.getConnection(); CallableStatement calStmt = testcon.prepareCall("{call detailsAccount(?)}"))
        {
            // Set parameters
            calStmt.setInt(1, this.accountNumber);

            // Execute the stored procedure
            calStmt.execute();

            // Retrieve the result set from the stored procedure call
            try (ResultSet resultSet = calStmt.getResultSet()) {
                // Process and display the results
                while (resultSet.next()) {
                    System.out.println("Account Number: " + resultSet.getInt("accnumber") + " | Account Holder: " + resultSet.getString("accname") + " | Balance: $" + resultSet.getDouble("accbalance"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
