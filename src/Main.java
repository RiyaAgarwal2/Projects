import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3305/bank?useSSL=true", "root", "root");
            System.out.println("Success");

            ArrayList<Account> list = new ArrayList<>();
            int defaultacctNumber = 1;

            while (true) {
                System.out.println("Enter your choice");
                System.out.println("1. Create an account");
                System.out.println("2. Deposit Amount in existing account");
                System.out.println("3. Withdraw amount from existing account");
                System.out.println("4. View details of an account");
                System.out.println("5. Exit Program");

                Scanner in = new Scanner(System.in);
                int n = in.nextInt();
                switch (n)
                {
                    case 1:
                    {
                        System.out.println("Enter Customer Name");
                        String name = in.next();
                        System.out.println("Enter 10 digits Mobile Number");
                        String number = in.next();
                        try
                        {
                            if(number.length()<10 || number.length()>10)
                            {
                                throw new MobileNumberInvalid("Invalid Mobile Number! Please Enter 10 digits Mobile Number");
                            }
                            else
                            {
                                System.out.println("Enter Balance");
                                double balance = in.nextDouble();
                                Account acct = new Account(name, number, balance);
                                acct.accountNumber = defaultacctNumber;
                                int j = acct.accountNumber;
                                ++defaultacctNumber;
                                list.add(acct);
                            /*PreparedStatement ps = con.prepareStatement("select account_number from accounts where account_number=?");
                            ps.setInt(1,j);
                            ResultSet rs = ps.executeQuery();
                            while(rs.next())
                            {

                                PreparedStatement pss = con.prepareStatement("select max(account_number) from accounts if exist");
                                ResultSet rss = pss.executeQuery();
                        while(rss.next())
                        {
                            j = rss.getInt("account_number")+1;
                        }*/

                                PreparedStatement ps = con.prepareStatement("insert into accounts values (?,?,?,?)");
                                ps.setInt(1,j);
                                ps.setString(2,name);
                                ps.setString(3,number);
                                ps.setDouble(4,balance);
                                int i = ps.executeUpdate();
                                if(i>0)
                                {
                                    System.out.println("Success");
                                }
                                else
                                {
                                    System.out.println("Unsuccessful");
                                }
                                System.out.println("Your account has been created successfully!!");
                                System.out.println("********************************");
                            }
                        }
                        catch(MobileNumberInvalid e)
                        {
                            System.out.println(e);
                        }
                        //String cutnumber = number.substring(0, 10);


                        break;
                    }
                    case 2:
                    {
                        System.out.println("Enter Account Number");
                        int accountNumber = in.nextInt();
                        try {
                            if (list.size() >= accountNumber) {
                                Account acct = list.get(accountNumber - 1);
                                System.out.println("Enter Amount to be deposited");
                                double balance = in.nextDouble();

                                if (balance <= 0) {
                                    throw new BalanceEntryNonNegative("Amount should be positive");
                                }
                                double newBalance = acct.getBalance() + balance;
                                if (balance > 0) {
                                    PreparedStatement ps = con.prepareStatement("update accounts set balance =? where account_number=?");
                                    ps.setDouble(1, newBalance);
                                    ps.setInt(2, accountNumber);
                                    int i = ps.executeUpdate();
                                    if (i > 0) {
                                        System.out.println("Success");
                                    } else {
                                        System.out.println("Unsuccessful");
                                    }
                                }
                                acct.setBalance(newBalance);
                            } else {
                                throw new AccountdoesnotExist("Account does not exist");
                            }
                        } catch (AccountdoesnotExist e) {
                            System.out.println(e);
                        } catch (BalanceEntryNonNegative e) {
                            System.out.println(e);
                        }
                        System.out.println("********************************");
                        break;

                    }

                    case 3:
                    {
                        System.out.println("Enter Account Number");
                        int accountNumber = in.nextInt();
                        try {
                            if (list.size() >= accountNumber)
                            {
                                Account acct = list.get(accountNumber - 1);
                                System.out.println("Enter Amount to be withdrawn");
                                double balance = in.nextDouble();
                                if (balance > acct.getBalance())
                                {
                                    throw new WithdrawalAmount("Insufficient Amount");
                                }
                                if (balance <= 0)
                                {
                                    throw new BalanceEntryNonNegative("Amount should be positive");
                                }
                                double newBalance = acct.getBalance() - balance;
                                if(balance>0 && newBalance>0)
                                {
                                    PreparedStatement ps = con.prepareStatement("update accounts set balance =? where account_number=?");
                                    ps.setDouble(1,newBalance);
                                    ps.setInt(2,accountNumber);
                                    int i = ps.executeUpdate();
                                    if(i>0){
                                        System.out.println("Success");
                                    }
                                    else{
                                        System.out.println("Unsuccessful");
                                    }
                                }
                                acct.setBalance(newBalance);
                            }
                            else
                            {
                                throw new AccountdoesnotExist("Account does not exist");
                            }
                        } catch (AccountdoesnotExist e) {
                            System.out.println(e);
                        } catch (BalanceEntryNonNegative e) {
                            System.out.println(e);
                        } catch (WithdrawalAmount e) {
                            System.out.println(e);
                        }
                        System.out.println("********************************");
                        break;
                    }
                    case 4:
                    {
                        System.out.println("Enter Account Number");
                        int accountNumber = in.nextInt();
                        try {
                            if (list.size() >= accountNumber)
                            {
                                //Account acct = list.get(accountNumber - 1);
                               /* System.out.println("Your Account details are shown below");
                                System.out.println("Customer Name: " + acct.getCustomerName());
                                System.out.println("Mobile Number: " + acct.getMobileNumber());
                                System.out.println("Account Balance: " + acct.getBalance());
                                System.out.println("Account Number: " + acct.getAccountNumber());
                                */
                                PreparedStatement ps = con.prepareStatement("select * from accounts where account_number=?");
                                ps.setInt(1,accountNumber);
                                ResultSet rs = ps.executeQuery();
                                while(rs.next())
                                {
                                    System.out.println("Your Account details are shown below");
                                    int an = rs.getInt("account_number");
                                    String name = rs.getString("name");
                                    String number = rs.getString("mobile_number");
                                    double balance = rs.getDouble("balance");
                                    System.out.println("Account Number: " + an);
                                    System.out.println("Account Holder's Name: " + name);
                                    System.out.println("Mobile Number: " + number);
                                    System.out.println("Account Balance " + balance);
                                }

                            } else {
                                throw new AccountdoesnotExist("Account does not exist");
                            }
                        } catch (AccountdoesnotExist e) {
                            System.out.println(e);
                        }

                        System.out.println("********************************");
                        break;
                    }
                    case 5:
                    {
                        System.exit(0);
                    }
                }
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}


class AccountdoesnotExist extends Exception{
    public AccountdoesnotExist(String message)
    {
        super(message);
    }
}

class MobileNumberInvalid extends Exception{
    public MobileNumberInvalid(String message)
    {
        super(message);
    }
}

class BalanceEntryNonNegative extends Exception{

    public BalanceEntryNonNegative(String message)
    {
        super(message);
    }
}

class WithdrawalAmount extends Exception{

    public WithdrawalAmount(String message)
    {
        super(message);
    }
}