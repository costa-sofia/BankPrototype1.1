import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Account acc = null; //account reference
        Scanner reader = new Scanner(System.in);
        int operationOpt;

        while(true)
        {
            operationOpt = 0;
            do {
                System.out.println("|-------------------------------------|");
                System.out.println("|---------Welcome to our Bank---------|");
                System.out.println("|--------- 1- Create Account ---------|");
                System.out.println("|--------- 2- Enter Account ----------|");
                System.out.println("|--------- 3- Deposit        ---------|");
                System.out.println("|--------- 4- Withdraw       ---------|");
                System.out.println("|--------- 5- Details        ---------|");
                System.out.println("|--------- 6- Exit           ---------|");
                System.out.println("|--------Choose your operation--------|");
                System.out.println("|-------------------------------------|");
                System.out.print("|--------Operation: ");

                operationOpt = reader.nextInt();
                reader.nextLine();
            }
            while (operationOpt != 1 && operationOpt != 2 && operationOpt != 3 && operationOpt != 4 && operationOpt != 5 && operationOpt != 6);

            switch (operationOpt)
            {
                case 1: //create account
                    if (acc == null) {
                        System.out.println("|-------------------------------------|");

                        System.out.print("|---Enter account holder's name: ");
                        String accountHolder = reader.nextLine();

                        System.out.print("|---Enter initial deposit: $");
                        double amt = reader.nextDouble();

                        acc = new Account(accountHolder, amt);

                        System.out.println("|----Account created  successfully----|");
                        System.out.println("|---Thank you for choosing our bank---|");
                        System.out.println("|-------------------------------------|");

                    } else {
                        System.out.println("|-------------------------------------|");
                        System.out.println("|---FYI: Account is already created---|");
                        System.out.println("|-------------------------------------|");
                    }
                    break;

                case 2: //enter account
                    if (acc == null) {
                        System.out.println("|-------------------------------------|");

                        System.out.print("|---Enter account number: ");
                        int accountNumber = reader.nextInt();
                        reader.nextLine();

                        acc = new Account(accountNumber);

                        System.out.println("|----Account entered  successfully----|");
                        System.out.println("|---Thank you for choosing our bank---|");
                        System.out.println("|-------------------------------------|");

                    } else {
                        System.out.println("|-------------------------------------|");
                        System.out.println("|---FYI: Account is already created---|");
                        System.out.println("|-------------------------------------|");
                    }
                    break;

                case 3: //deposit
                    if (acc != null) {
                        System.out.println("|-------------------------------------|");
                        System.out.println("|---Thank you for choosing our bank---|");
                        System.out.print("|Enter the amount you wish to deposit: $");
                        double amt = reader.nextDouble();
                        acc.deposit(amt);
                    } else {
                        System.out.println("|-------------------------------------|");
                        System.out.println("|------FYI: Create account first------|");
                        System.out.println("|-------------------------------------|");
                    }
                    break;

                case 4: //Withdraw
                    if (acc != null) {
                        System.out.println("|-------------------------------------|");
                        System.out.println("|---Thank you for choosing our bank---|");
                        System.out.print("|Enter the amount you wish to withdraw: ");
                        double amt = reader.nextDouble();
                        acc.withdraw(amt);
                    } else {
                        System.out.println("|-------------------------------------|");
                        System.out.println("|------FYI: Create account first------|");
                        System.out.println("|-------------------------------------|");
                    }
                    break;

                case 5: //Details
                    if (acc != null)
                    {
                        System.out.println("|-------------------------------------|");
                        System.out.println("|---Thank you for choosing our bank---|");
                        acc.getDetails();
                    }
                    else
                    {
                        System.out.println("|-------------------------------------|");
                        System.out.println("|------FYI: Create account first------|");
                        System.out.println("|-------------------------------------|");
                    }
                    break;

                case 6: //Exit
                    System.out.println("|-------------------------------------|");
                    System.out.println("|---Thank you for choosing our bank---|");
                    System.out.println("|---We look forward to see you soon---|");
                    System.out.println("|-------------------------------------|");
                    System.exit(0);
                    break;

            }

        }
    }
}