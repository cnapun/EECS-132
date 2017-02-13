/* Nikil Pancha
 * Junit test class for StudentAccount */

import junit.framework.TestCase;

public class StudentAccountTest extends TestCase {
  
  // field to use in all test case constructors
  String account = "3.1415926535897932";
  String name = "Anthrax";
  
  // makes sure constructor stores the correct values
  public void testConstructor() {
    StudentAccount test = new StudentAccount(account, name); //account for testing
    
    assertEquals(account, test.getAccountNumber());
    assertEquals(name, test.getName());
  }
  
  // tests name setter
  public void testName() {
    StudentAccount test = new StudentAccount(account, "Evil"); //account for testing
    
    test.setName(name);
    assertEquals(name, test.getName());
  }
  
  // tests address getter and setter
  public void testAddress() {
    StudentAccount test = new StudentAccount(account, name); //account for testing
    
    test.setAddress("1 Two 11 Tree Ln.");
    assertEquals("1 Two 11 Tree Ln.", test.getAddress());
  }
  
  // tests library account getter and setter
  public void testLibAccount() {
    LibraryAccount libraryAccount = new LibraryAccount(account); //account for testing
    StudentAccount test = new StudentAccount(account, name); //account for testing
    
    test.setLibraryAccount(libraryAccount);
    assertEquals(libraryAccount, test.getLibraryAccount());
  }
  
  // tests dining account getter and setter
  public void testDinAccount() {
    CreditAccount diningAccount = new CreditAccount(account, 0.2); //account for testing
    StudentAccount test = new StudentAccount(account, name); //account for testing
    
    test.setDiningAccount(diningAccount);
    assertEquals(diningAccount, test.getDiningAccount());
  }
  
  // tests tuition account getter and setter
  public void testTuiAccount() {
    CreditAccount tuitionAccount = new CreditAccount(account, 0.1);
    StudentAccount test = new StudentAccount(account, name);
    
    test.setTuitionAccount(tuitionAccount);
    assertEquals(tuitionAccount, test.getTuitionAccount());
  }
  
  // tests getBalance by first making sure it returns zero if no if statements are entered
  // and then tests to make sure it correctly sums 1, 2, and 3 account balances
  public void testGetBalance() {
    LibraryAccount libraryAccount = new LibraryAccount(account); //account for testing
    CreditAccount tuitionAccount = new CreditAccount(account, 0.1); //account for testing
    CreditAccount diningAccount = new CreditAccount(account, 0.2); //account for testing
    StudentAccount test = new StudentAccount(account, name); //account for testing
    
    assertEquals(0., test.getBalance()); //nothing yet
    test.setLibraryAccount(libraryAccount);
    libraryAccount.charge(30);
    assertEquals(30., test.getBalance()); //library balance = 30.
    test.setDiningAccount(diningAccount);
    diningAccount.charge(50);
    assertEquals(80., test.getBalance()); //dining + library = 30. + 50. = 80. 
    test.setTuitionAccount(tuitionAccount);
    tuitionAccount.charge(999.99);
    assertEquals(1079.99, test.getBalance()); //tuition + dining + library = 30. + 50. + 999.99 = 1079.99
  }
  
  // tests charge without a tuition account
  public void testChargeNoTuition() {
    CreditAccount tuitionAccount = new CreditAccount(account, 0.1); //account for testing
    StudentAccount test = new StudentAccount(account, name); //account for testing
    
    test.charge(100);
    assertEquals(0., tuitionAccount.getBalance());
    assertEquals(100., test.getBalance()); // makes sure refundAmount works properly
  }
  
  // tests charge with a tuition account
  public void testChargeWithTuition() {
    CreditAccount tuitionAccount = new CreditAccount(account, 0.1); //account for testing
    StudentAccount test = new StudentAccount(account, name); //account for testing
    
    test.charge(100.); //refundAmount = -100
    test.setTuitionAccount(tuitionAccount);
    test.charge(350.);
    assertEquals(450., tuitionAccount.getBalance()); //charged using refundAmount too, 350. - (-100.) = 450.
    assertEquals(450., test.getBalance());
  }
  
  /* Testing credit with a tuition account and dining account is the exact same,
   * so it is not repeated */
  
  // tests credit with no attached accounts
  public void testCreditNoAcct() {
    StudentAccount test = new StudentAccount(account, name); //account for testing
    
    test.credit(100.);
    assertEquals(100., test.getRefundAmount());
  }
  
  // tests credit with one tuition account that should not receive any money
  public void testCreditTuitionFully() {
    StudentAccount test = new StudentAccount(account, name); //account for testing
    CreditAccount tuitionAccount = new CreditAccount(account, 0.1); //account for testing
    
    test.setTuitionAccount(tuitionAccount);
    tuitionAccount.charge(100.);
    tuitionAccount.endOfMonth();
    tuitionAccount.credit(200);
    test.credit(50);
    assertEquals(-100., tuitionAccount.getBalance()); //100. - 200.
    assertEquals(50., test.getRefundAmount()); //no money goes towards paying tuition 
  }
  
  // tests credit when credited amount will even out the tuition account
  public void testCreditTuitionEven() {
    StudentAccount test = new StudentAccount(account, name); //account for testing
    CreditAccount tuitionAccount = new CreditAccount(account, 0.1); //account for testing
    
    test.setTuitionAccount(tuitionAccount);
    tuitionAccount.charge(100.);
    tuitionAccount.endOfMonth();
    tuitionAccount.credit(50);
    test.credit(50);
    assertEquals(0., tuitionAccount.getBalance()); //100. - 50. - 50 = 0.
  }
  
  // tests credit when only part of the credited amount goes to tuition
  public void testCreditTuitionPartly() {
    StudentAccount test = new StudentAccount(account, name); //account for testing
    CreditAccount tuitionAccount = new CreditAccount(account, 0.1); //account for testing
    
    test.setTuitionAccount(tuitionAccount);
    tuitionAccount.charge(100.);
    tuitionAccount.endOfMonth();
    tuitionAccount.credit(75);
    test.credit(50);
    assertEquals(0., tuitionAccount.getBalance()); //enough money credited to fully pay tuition
    assertEquals(25., test.getRefundAmount()); //-(100. - 75. -50.) = 25.
  }
  
  // tests credit with library account when more money is credited than the balance of library
  public void testCreditLibrary() {
    StudentAccount test = new StudentAccount(account, name); //account for testing
    LibraryAccount libraryAccount = new LibraryAccount(account); //account for testing
    
    libraryAccount.charge(10);
    test.setLibraryAccount(libraryAccount);
    test.credit(30);
    assertEquals(0., libraryAccount.getBalance()); //enough money credited to fully pay library
    assertEquals(20., test.getRefundAmount()); //-(10. - 30.) = 20.
  }
  
  // tests credit in library account when amount will not completely reduce library account balance
  public void testCreditLibraryFull() {
    StudentAccount test = new StudentAccount(account, name); //account for testing
    LibraryAccount libraryAccount = new LibraryAccount(account); //account for testing
    
    libraryAccount.charge(90);
    test.setLibraryAccount(libraryAccount);
    test.credit(30);
    assertEquals(60., libraryAccount.getBalance()); //90. - 30. = 60.
    assertEquals(0., test.getRefundAmount()); //all money goes to library
  }
  
  /* tests credit with all three accounts just to make sure there are no errors in
   * the interaction of the three if statements */
  public void testCreditAllThree() {
    StudentAccount test = new StudentAccount(account, name); //account for testing
    CreditAccount tuitionAccount = new CreditAccount(account, 0.1); //account for testing
    LibraryAccount libraryAccount = new LibraryAccount(account); //account for testing
    CreditAccount diningAccount = new CreditAccount(account, 0.2); //account for testing
    
    test.setLibraryAccount(libraryAccount);
    test.setTuitionAccount(tuitionAccount);
    test.setDiningAccount(diningAccount);
    tuitionAccount.charge(100.);
    tuitionAccount.endOfMonth();
    tuitionAccount.credit(80.); //monthlyPayment = 100, amountPaidThisMonth = 80, difference = 20
    diningAccount.charge(100.);
    diningAccount.endOfMonth();
    diningAccount.credit(90); //monthlyPayment = 100, amountPaidThisMonth = 90, difference = 10
    libraryAccount.charge(30); //balance = 30
    test.credit(100);
    assertEquals(0., diningAccount.getBalance()); //fully paid
    assertEquals(0., tuitionAccount.getBalance()); //fully paid
    assertEquals(0., libraryAccount.getBalance()); //fully paid
    assertEquals(40., test.getRefundAmount()); //100. - 20. - 10. - 30. = 40.
  }
}
