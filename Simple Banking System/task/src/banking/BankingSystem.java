package banking;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * The Banking System, handles the User input and Output as well as a list of the accounts within the System
 */
public class BankingSystem {
    private ArrayList<Account> accounts;                //The existing accounts in the System
    private Account currentAccount;                     //The account that is currently being used
    private final Scanner scanner;                      //The scanner to read the user input
    private final DBHandler dbHandler;                  //The DBHandler to handle access to the database
    private boolean exit;                               //Whether or not the system should be closed

    /**
     * Creates a new banking system, initiates the Scanner and the DBHandler and loads
     * all accounts from the database
     *
     * @param dbFileName the name of the database file
     */
    public BankingSystem(String dbFileName) {
        dbHandler = new DBHandler(dbFileName);
        currentAccount = null;
        scanner = new Scanner(System.in);
        exit = false;
    }

    /**
     * Starts the System after loading the accounts from the database
     */
    public void run() {
        accounts = dbHandler.getCards();                //Load the accounts from the database

        mainMenu();                                     //Start the user interaction

        dbHandler.setCards(accounts);                   //Save the accounts to the database when closing the system
    }

    /**
     * The main menu for user interaction. Prints the options the user can take and
     * performs actions based on his input
     */
    private void mainMenu() {
        while (!exit) {
            System.out.println();
            System.out.println("1. Create an account");
            System.out.println("2. Log into account");
            System.out.println("0. Exit");

            switch (checkUserInput("[012]", scanner.nextLine())) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    logIn();
                    break;
                case 0:
                    exit = true;
                    break;
            }
        }

        System.out.println("Bye!");
    }

    /**
     * Creates a new bank account, adds it to the list and prints its card number and pin
     */
    private void createAccount() {
        System.out.println();
        currentAccount = new Account();
        accounts.add(currentAccount);

        System.out.println("Your card has been created");
        System.out.println("Your card number:");
        System.out.println(currentAccount.getCardNumber());
        System.out.println("Your card PIN:");
        System.out.printf("%04d%n", currentAccount.getPin());
    }

    /**
     * Allows the user to log into his account if he enters his card number and
     * the correct pin.
     */
    private void logIn() {
        System.out.println();
        System.out.println("Enter your card number:");
        String cardNumber = scanner.nextLine();
        System.out.println("Enter your PIN:");
        int pin = checkUserInput("\\d{4}", scanner.nextLine());
        currentAccount = accounts.stream()
                .filter(e -> e.logIn(cardNumber, pin))
                .findFirst()
                .orElse(null);

        if (currentAccount == null) {
            System.out.println("Wrong card number or PIN!");
        } else {
            System.out.println("You have successfully logged in!");
            System.out.println();
            loggedIn();
        }
    }

    /**
     * Prints the options a user has after logging in and performs them based on his input.
     * This includes checking the account balance, adding income the the account, transferring money
     * to another account, closing the account and logging out. The user can also exit the system in this menu.
     */
    private void loggedIn() {
        System.out.println();
        System.out.println("1. Balance");
        System.out.println("2. Add income");
        System.out.println("3. Do transfer");
        System.out.println("4. Close account");
        System.out.println("5. Log out");
        System.out.println("0. Exit");

        switch (checkUserInput("[012345]", scanner.nextLine())) {
            case 1:
                System.out.println("Balance: " + (int) currentAccount.getBalance());
                System.out.println();
                loggedIn();
                break;
            case 2:
                System.out.println();
                System.out.println("Enter income:");
                int income = checkUserInput("\\d+", scanner.nextLine());
                if (income != -1) {
                    currentAccount.addIncome(income);
                }
                System.out.println("Income was added!");
                loggedIn();
                break;
            case 3:
                transfer();
                loggedIn();
                break;
            case 4:
                accounts.remove(currentAccount);
                currentAccount = null;
                System.out.println();
                System.out.println("The account has been closed!");
                break;
            case 5:
                currentAccount.logOut();
                break;
            case 0:
                exit = true;
                break;
        }
    }

    /**
     * Allows the user to transfer money to another account, but only if he enters an existing card number
     * that is not his own, and if the amount he wishes to transfer does not exceed his current balance.
     */
    private void transfer() {
        System.out.println();
        System.out.println("Transfer");
        System.out.println("Enter card number");
        String cardNumber = scanner.nextLine();

        if (!cardNumber.equals(Account.luhn(cardNumber.substring(0, 15)))) {
            System.out.println("Probably you made a mistake in the card number. Please try again!");
            return;
        }

        Account target = accounts.stream()
                .filter(e -> e.getCardNumber().equals(cardNumber))
                .findFirst()
                .orElse(null);

        if (target == null) {
            System.out.println("Such a card does not exist.");
            return;
        }

        if (target.getCardNumber().equals(currentAccount.getCardNumber())) {
            System.out.println("You can't transfer money to the same account!");
            return;
        }

        System.out.println("Enter how much money you want to transfer:");
        int amount = checkUserInput("\\d+", scanner.nextLine());

        if (amount == -1 || amount > currentAccount.getBalance()) {
            System.out.println("Not enough money!");
            return;
        }

        currentAccount.transfer(target, amount);
        System.out.println("Success!");
    }

    /**
     * checks if a String consist of number specified in a regular expression and
     * returns the number as an integer. If the String does not match the regex or can not be converted
     * to integer, -1 will be returned. This is used to check if the user has entered one of the possible
     * numbers the perform an action in a menu, or to check card numbers, pins and amounts of money entered by the user.
     *
     * @param regex the regular expression the string must match
     * @param userInput the String that will be checked
     * @return the integer value of the String or -1
     */
    private static int checkUserInput(String regex, String userInput) {
        try {
            if (userInput.matches(regex)) {
                return Integer.parseInt(userInput);
            } else {
                return -1;
            }
        } catch (Exception e) {
            return -1;
        }
    }
}
