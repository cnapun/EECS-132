/* Nikil Pancha
 * This class is a basic account that can increase and decrease
 * account balance and store a balance limit and account number.
 */

public class Account {
  
  // string field to hold account number
  private String accountNumber;
  
  // field to store account balance
  private double balance;
  
  /*field to store the maximum balance allowed
   * defaults to zero so it will need to be manually set*/
  private int balanceLimit;
  
  // constructor to create an account from a string account number
  public Account(String actNumber) {
    accountNumber = actNumber;
  }
  
  /*constructor to create a new Account object with a certain account
   * number and balance limit*/
  public Account(String actNumber, int balLimit) {
    balanceLimit = balLimit;
    accountNumber = actNumber;
  }
  
  // retrieves the account number
  public String getAccountNumber() {
    return accountNumber;
  }
  
  // retrieves the current account balance
  public double getBalance() {
    return balance;
  }
  
  // adds a double amount of money to the current account balance
  // charging a negative amount is equivalent to crediting
  public void charge(double plusCharge) {
    balance += plusCharge;
  }
  
  // subtracts a double amount of money from the current account balance
  public void credit(double minusCharge) {
    balance -= minusCharge;
  }
  
  // changes the balance limit to the input value
  public void setBalanceLimit(int newLimit) {
    balanceLimit = newLimit;
  }
  
  // returns the balance limit
  public int getBalanceLimit() {
    return balanceLimit;
  }
}
