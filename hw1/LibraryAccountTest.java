/* Nikil Pancha
 * JUnit test class for LibraryAccount
 */

import junit.framework.TestCase;

public class LibraryAccountTest extends TestCase {
  
  // fields to use in test cases
  String account = "3.14159265";
  int balLimit = 50;
  double bookFine = 1.5;
  double reserveFine = 3.0;
  
  // verifies constructor with 1 argument stores account
  public void testConstructor1() {
    LibraryAccount test = new LibraryAccount(account); //account for testing
    
    assertEquals(test.getAccountNumber(), account);
  }
  
  // verifies constructor with 4 arguments stores all of the variables correctly
  public void testConstructor2() {
    LibraryAccount test = new LibraryAccount(account, balLimit, bookFine,
                                             reserveFine); //account for testing
    
    assertEquals(account, test.getAccountNumber());
    assertEquals(balLimit, test.getBalanceLimit());
    assertEquals(bookFine, test.getBookFine());
    assertEquals(reserveFine, test.getReserveFine());
  }
  
  // tests fine setters
  public void testFineSetters() {
    LibraryAccount test = new LibraryAccount(account); //account for testing
    
    test.setBookFine(bookFine);
    test.setReserveFine(reserveFine);
    assertEquals(bookFine, test.getBookFine());
    assertEquals(reserveFine, test.getReserveFine());
  }
  
  // tests increment, decrement, and case below 0.
  public void testIncDec() {
    LibraryAccount test = new LibraryAccount(account); //account for testing
    
    for (int i = 1; i < 10; i++) {
      test.incrementOverdueBooks();
      test.incrementOverdueReserve();
      assertEquals(i, test.getNumberOverdueBooks());
      assertEquals(i, test.getNumberOverdueReserve());
    }
    for (int i = 1; i < 10; i++) {
      test.decrementOverdueBooks();
      test.decrementOverdueReserve();
      assertEquals(9 - i, test.getNumberOverdueBooks());
      assertEquals(9 - i, test.getNumberOverdueReserve());
    }
    // make sure number can't go below 0
    test.decrementOverdueBooks();
    test.decrementOverdueReserve();
    assertEquals(0, test.getNumberOverdueBooks());
    assertEquals(0, test.getNumberOverdueReserve());
  }
  
  // tests canBorrow method with both cases
  public void testCanBorrow() {
    LibraryAccount test = new LibraryAccount(account, balLimit, bookFine,
                                             reserveFine); //account for testing
    
    assertEquals(true, test.canBorrow());
    test.charge(55);
    assertEquals(false, test.canBorrow());
  }
  
  // tests endOfDay, makes sure correct balance is added
  public void testEndOfDay() {
    LibraryAccount test = new LibraryAccount(account, balLimit, bookFine,
                                             reserveFine); //account for testing
    
    test.incrementOverdueBooks();
    test.incrementOverdueBooks();
    test.incrementOverdueReserve();
    test.incrementOverdueReserve();
    test.incrementOverdueReserve();
    test.endOfDay();
    assertEquals(12., test.getBalance()); //2 * 1.5 + 3 * 3. = 12.
  }
}
