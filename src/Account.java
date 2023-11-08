public class Account {
    String customerName;
    String mobileNumber;
    double balance;
    int accountNumber;

    public Account(String customerName, String mobileNumber, double balance){
        this.customerName = customerName;
        this.mobileNumber = mobileNumber;
        this.balance = balance;
    }

    public String getCustomerName(){
        return customerName;
    }

    public String getMobileNumber(){
        return mobileNumber;
    }

    public double getBalance(){
        return balance;
    }

    public int getAccountNumber(){
        return accountNumber;
    }


    public void setCustomerName(String cn){
        this.customerName=cn;
    }

    public void setMobileNumber(String mn){
        this.mobileNumber=mn;
    }

    public void setBalance(double bl){
        this.balance=bl;
    }
}
