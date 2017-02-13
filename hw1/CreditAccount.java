/* Nikil Pancha
 * This class is an account where the customer borrows money and then
 * at the end of the month, they pay the previous month's payment. If
 * they don't pay enough, they are charged interest.*/

public class CreditAccount extends Account {
  
  // field to store interest rate
  private double interestRate;
  
  // field to store amount to pay to avoid interest payment
  private double monthlyPayment;
  
  // field to store amount paid on previous month's payment
  private double amountPaidThisMonth;
  
  // constructor that creates an account and sets the interest rate
  public CreditAccount(String accountNumber, double intRate) {
    super(accountNumber);
    interestRate = intRate;
  }
  
  // sets interest rate
  public void setInterestRate(double intRate) {
    interestRate = intRate;
  }
  
  // retrieves interest rate
  public double getInterestRate() {
    return interestRate;
  }
  
  /*overrides credit to decrease amount needed to avoid interest
   * by amount credited and decrease balance by the same amount*/
  public void credit(double creditedValue) {
    super.credit(creditedValue);
    amountPaidThisMonth += (creditedValue);
  }
  
  // retrieves monthly payment needed to avoid interest
  public double getMonthlyPayment() {
    return monthlyPayment;
  }
  
  // retrieves amount going towards previous month's payment
  public double getAmountPaidThisMonth() {
    return amountPaidThisMonth;
  }
  
  /*ends the month: if the previous month's payment is not fully paid, add
   * interest to current balance, set payment to the current balance*/
  public void endOfMonth() {
    if (getAmountPaidThisMonth() < getMonthlyPayment())
      charge(getBalance() * getInterestRate() / 12.0);
    monthlyPayment = getBalance(); 
    amountPaidThisMonth = 0;
  }
}
