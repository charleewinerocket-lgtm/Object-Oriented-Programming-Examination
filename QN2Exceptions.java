import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// Custom Checked Exception
class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

// Bank Account Class
class BankAccount {
    private double balance = 1000.0;

    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount > balance) {
            throw new InsufficientFundsException("Insufficient funds.");
        }

        balance -= amount;
        System.out.println("Withdrawal successful.");
        System.out.println("Remaining balance: " + balance);
    }
}

public class QN2Exceptions {

    // Question (a)
    static int mystery() {
        try {
            System.out.println("A");
            return 1;
        } catch (Exception e) {
            System.out.println("B");
            return 2;
        } finally {
            System.out.println("C");
            return 3;
        }
    }

    // Question (b)
    static int risky(int x) {
        try {
            if (x == 0)
                throw new ArithmeticException();
            return 10 / x;
        } catch (ArithmeticException e) {
            System.out.println("caught");
            return -1;
        } finally {
            System.out.println("done");
        }
    }

    public static void main(String[] args) {

        // (a)
        System.out.println("Question (a)");
        System.out.println(mystery());

        // (b)
        System.out.println("\nQuestion (b)");
        System.out.println("Return value: " + risky(0));
        System.out.println("Return value: " + risky(2));

        // (c)
        System.out.println("\nQuestion (c)");
        BankAccount account = new BankAccount();

        try {
            account.withdraw(1200);
        } catch (InsufficientFundsException e) {
            System.out.println(e.getMessage());
        }

        // (d)
        System.out.println("\nQuestion (d)");

        try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {

            String line = br.readLine();
            System.out.println(line);

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}