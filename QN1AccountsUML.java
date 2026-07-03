import java.util.ArrayList;

// Interface
interface Statement {
    String generateStatement();
}

// Abstract Account Class
abstract class Account implements Statement {
    protected String accountNumber;
    protected double balance;

    public Account(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    @Override
    public String generateStatement() {
        return "Account Number: " + accountNumber +
                "\nBalance: " + String.format("%.2f", balance);
    }

    public abstract boolean withdraw(double amount);
}

// SavingsAccount Class
class SavingsAccount extends Account {
    private double rate;

    public SavingsAccount(String accountNumber, double balance, double rate) {
        super(accountNumber, balance);
        this.rate = rate;
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0)
            return false;

        if (balance - amount >= 0) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public void addInterest() {
        balance += balance * rate / 100;
    }

    @Override
    public String generateStatement() {
        return "Savings Account\n" +
                super.generateStatement();
    }
}

// CurrentAccount Class
class CurrentAccount extends Account {
    private double overdraftLimit;

    public CurrentAccount(String accountNumber, double balance, double overdraftLimit) {
        super(accountNumber, balance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0)
            return false;

        if (balance - amount >= -overdraftLimit) {
            balance -= amount;
            return true;
        }
        return false;
    }

    @Override
    public String generateStatement() {
        return "Current Account\n" +
                "Account Number: " + accountNumber +
                "\nBalance: " + String.format("%.2f", balance) +
                "\nOverdraft Limit: " + overdraftLimit;
    }
}

// Customer Class
class Customer {
    private String name;
    private ArrayList<Account> accounts;

    public Customer(String name) {
        this.name = name;
        accounts = new ArrayList<>();
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public double totalWorth() {
        double total = 0;
        for (Account account : accounts) {
            total += account.getBalance();
        }
        return total;
    }

    public String getName() {
        return name;
    }
}

// Main Class
public class QN1AccountsUML {
    public static void main(String[] args) {

        Customer customer = new Customer("Alan");

        SavingsAccount savings = new SavingsAccount("S001", 1000, 5);
        CurrentAccount current = new CurrentAccount("C001", 500, 300);

        customer.addAccount(savings);
        customer.addAccount(current);

        savings.deposit(500);
        savings.withdraw(200);
        savings.addInterest();

        if (current.withdraw(700)) {
            System.out.println("Current account withdrawal successful.");
        } else {
            System.out.println("Current account withdrawal failed.");
        }

        System.out.println("\nSavings Account Statement");
        System.out.println(savings.generateStatement());

        System.out.println("\nCurrent Account Statement");
        System.out.println(current.generateStatement());

        System.out.printf("\nCustomer Total Worth = %.2f%n", customer.totalWorth());
    }
}