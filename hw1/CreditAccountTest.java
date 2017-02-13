/* Nikil Pancha
 * JUnit test class for CreditAccount
 */

import junit.framework.TestCase;

public class CreditAccountTest extends TestCase {
  // strings to use in test cases
  String account = "3.14159265358979";
  double intRate = 0.12;
  
  /* tests the constructor to make sure both interest rate
   * and account number are set */
  public void testConstructor() {
    CreditAccount test = new CreditAccount(account, intRate); //account for testing
    
    assertEquals(intRate, test.getInterestRate());
    assertEquals(account, test.getAccountNumber());
  }
  
  /* tests the setter for interest rate*/
  public void testIntRateSetter() {
    CreditAccount test = new CreditAccount(account, intRate); //account for testing
    
    test.setInterestRate(0.012);
    assertEquals(0.012, test.getInterestRate());
  }
  
  /* tests functionality of credit, makes sure it credits the account and
   * increases amount paid that month*/
  public void testCredit() {
    CreditAccount test = new CreditAccount(account, intRate); //account for testing
    
    test.credit(50.);
    assertEquals(-50., test.getBalance());
    assertEquals(50., test.getAmountPaidThisMonth());
  }
  
  /* tests end of month, first time to check monthly payment functionality, then to check interest,
   * and then to check handling of negative balance*/
  public void testEndOfMonth() {
    CreditAccount test = new CreditAccount(account, intRate); //account for testing
    
    test.charge(500.);
    test.endOfMonth();
    assertEquals(500., test.getMonthlyPayment());
    test.endOfMonth();
    assertEquals(505., test.getBalance()); //500. + 500. * 0.12/12
  }
}
