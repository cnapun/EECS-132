/* Nikil Pancha
 * This class represents a student's account.  There can be three
 * associated accounts: dining, tuition, and library, and several methods
 * are overriden to account for the possibility of multiple connected accounts*/

public class StudentAccount extends Account {
  
  // string to store student name
  private String name;
  
  // string to store student address
  private String address;
  
  // amount owed to the student by the institution
  private double refundAmount;
  
  // library account that can be set later
  private LibraryAccount libraryAccount;
  
  // tuition account that can be set later
  private CreditAccount tuitionAccount;
  
  // dining account that can be set later
  private CreditAccount diningAccount;
  
  // constructor that assigns an account number and student name
  public StudentAccount(String accountNumber, String name) {
    super(accountNumber);
    this.name = name;
  }
  
  // changes the student's name
  public void setName(String name) {
    this.name = name;
  }
  
  // retrieves the student's name
  public String getName() {
    return name;
  }
  
  // sets the student's address
  public void setAddress(String address) {
    this.address = address;
  }
  
  // retrieves the student's address
  public String getAddress() {
    return address;
  }
  
  // sets library account
  public void setLibraryAccount(LibraryAccount libraryAccount) {
    this.libraryAccount = libraryAccount;
  }
  
  // retrieves library account
  public LibraryAccount getLibraryAccount() {
    return libraryAccount;
  }
  
  // sets tuition account
  public void setTuitionAccount(CreditAccount tuitionAccount) {
    this.tuitionAccount = tuitionAccount;
  }
  
  // retrieves tuition account
  public CreditAccount getTuitionAccount() {
    return tuitionAccount;
  }
  
  // sets dining account
  public void setDiningAccount(CreditAccount diningAccount) {
    this.diningAccount = diningAccount;
  }
  
  // retrieves dining account
  public CreditAccount getDiningAccount() {
    return diningAccount;
  }
  
  /*overrides getBalance to sum all 3 account balances if they
   * exist, then subtract the refund amount*/
  public double getBalance() {
    double sum = 0.0; //variable to sum balances of all accounts
    
    if (libraryAccount != null)
      sum += libraryAccount.getBalance();
    if (diningAccount != null)
      sum += diningAccount.getBalance();
    if (tuitionAccount != null)
      sum += tuitionAccount.getBalance();
    return sum - refundAmount;
  }
  
  public void charge(double amount) {
    double difference = amount - refundAmount; //difference between charged amount and
    //refund amount to simplify further calculations
    
    if ((difference > 0) && (tuitionAccount != null)) {
      tuitionAccount.charge(difference);
      refundAmount = 0;
    } else {
      refundAmount = -difference;
    }
  }
  
  //getter for refundAmount (not required in rubric, but it makes 
  //sense and simplifies testing)
  public double getRefundAmount() {
    return refundAmount;
  }
  
  public void credit(double amount) {
    double runningAmount = amount; //track amount left to credit
    
    if (tuitionAccount != null) {
      double difference1 = -tuitionAccount.getAmountPaidThisMonth()
        + tuitionAccount.getMonthlyPayment(); // find this difference to simplify further calculations.
      if (difference1 > 0) {
        if (difference1 < runningAmount) {
          tuitionAccount.credit(difference1);
          runningAmount -= difference1;
        } else {
          tuitionAccount.credit(runningAmount);
          return;
        }
      }
    }
    if (diningAccount != null) {
      double difference2 = -diningAccount.getAmountPaidThisMonth() +
        diningAccount.getMonthlyPayment(); // find this difference to simplify further calculations.
      if (difference2 > 0) {
        if (difference2 < runningAmount) {
          diningAccount.credit(difference2);
          runningAmount -= difference2;
        } else {
          diningAccount.credit(runningAmount);
          return;
        }
      }
    }
    if (libraryAccount != null) {
      if (libraryAccount.getBalance() >= runningAmount) {
        libraryAccount.credit(runningAmount);
        return;
      } else {
        runningAmount -= libraryAccount.getBalance();
        libraryAccount.credit(libraryAccount.getBalance());
      }
    }
    refundAmount += runningAmount;
  }
}
