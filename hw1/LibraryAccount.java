/* Nikil Pancha
 * This class is an account that tracks overdue items (books and reserve
 * items) and increases the balance at each end of day*/

public class LibraryAccount extends Account {
  
  // field to store fine per overdue book
  private double bookFine;
  
  // field to store fine per overdue reserved item
  private double reserveFine;
  
  // field to store number of overdue books
  private int overdueBooks;
  
  // field to store number of overdue reserved items
  private int overdueReserve;
  
  // constructor that takes an account number
  public LibraryAccount(String accountNumber) {
    super(accountNumber);
  }
  
  // constructor that sets more fields at once than the basic one
  public LibraryAccount(String accountNumber, int balanceLimit, double bookFine,
                        double reserveFine) {
    super(accountNumber, balanceLimit);
    this.bookFine = bookFine;
    this.reserveFine = reserveFine;
  }
  
  // sets the book fine
  public void setBookFine(double fine) {
    bookFine = fine;
  }
  
  // retrieves the book fine
  public double getBookFine() {
    return bookFine;
  }
  
  // sets the reserved item fine
  public void setReserveFine(double fine) {
    reserveFine = fine;
  }
  
  // retrieves the reserved item fine
  public double getReserveFine() {
    return reserveFine;
  }
  
  // increases number of overdue books
  public void incrementOverdueBooks() {
    overdueBooks++;
  }
  
  // decreases number of overdue books, cannot go below 0
  public void decrementOverdueBooks() {
    if (getNumberOverdueBooks() > 0)
      overdueBooks--;
  }
  
  // retrieves the number of overdue books
  public int getNumberOverdueBooks() {
    return overdueBooks;
  }
  
  // increases the number of overdue reserved items
  public void incrementOverdueReserve() {
    overdueReserve++;
  }
  
  // decreases the number of overdue reserved items, cannot go below 0
  public void decrementOverdueReserve() {
    if (getNumberOverdueReserve() > 0)
      overdueReserve--;
  }
  
  // retrieves the number of overdue reserved items
  public int getNumberOverdueReserve() {
    return overdueReserve;
  }
  
  // tells whether or not this account can borrow another item
  public boolean canBorrow() {
    return getBalance() <= getBalanceLimit();
  }
  
  // ends the day, increases account balance by correct amount
  public void endOfDay() {
    charge(bookFine * overdueBooks + reserveFine * overdueReserve);
  }
}
