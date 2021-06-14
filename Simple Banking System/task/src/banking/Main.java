package banking;

/**
 * The Main Class of my Solution for the Simple Banking System project on JetBrains Academy
 */
public class Main {

    /**
     * Starts the banking system with the database entered in the command line,
     * if the user followed the syntax "-filename [pathToDB]"
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length == 2 && args[0].equals("-fileName")) {
            BankingSystem bankingSystem = new BankingSystem(args[1]);

            bankingSystem.run();
        }
    }
}

