/* Nikil Pancha
 * JUnit test class for Account
 */

import junit.framework.TestCase;

public class AccountTest extends TestCase {
  
  // tests constructor with one argument by verifying the account number
  public void testConstructor1() {
    Account test = new Account("test"); //account for testing
    
    assertEquals("test", test.getAccountNumber());
  }
  
  // tests constructor with two arguments by verifying the account number and balance limit
  public void testConstructor2() {
    Account test = new Account("test", 15); //account for testing
    
    assertEquals("test", test.getAccountNumber());
    assertEquals(15, test.getBalanceLimit());
  }
  
  // tsts balance limit setter
  public void testBalanceLimit() {
    Account test = new Account("test", 10); //account for testing
    int  n = 16; //somewhat random number for testing
    
    test.setBalanceLimit(n);
    assertEquals(n, test.getBalanceLimit());
  }
  
  // tests charge method
  public void testCharge() {
    Account test = new Account("test"); //account for testing
    double n = 130.47; //somewhat random number for testing
    
    test.charge(n);
    assertEquals(0 + n, test.getBalance());
  }
  
  // tests credit method
  public void testCredit() {
    Account test = new Account("test"); //account for testing
    double n = 156.12; //arbitrary number for testing
    
    test.credit(n);
    assertEquals(0 - n, test.getBalance());
  }
}
