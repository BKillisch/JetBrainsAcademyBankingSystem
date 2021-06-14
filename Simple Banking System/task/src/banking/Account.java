package banking;

import java.util.Random;

/**
 * Saves the data of an account and handles the changes of said data
 */
public class Account {
    private final String cardNumber;
    private final int pin;
    private int balance;
    private boolean loggedIn;

    /**
     * Returns the number of the card
     *
     * @return the number of the card
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * Returns the pin of the card
     *
     * @return the pin of the card
     */
    public int getPin() {
        return pin;
    }

    /**
     * Returns the balance of the card
     *
     * @return the balance of the card
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Returns if the user is logged into this account
     *
     * @return if the user is logged into this account
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }

    /**
     * Checks if the entered parameters match the card number and pin of this account.
     * If yes, the field loggedIn will be set to True
     *
     * @param cardNumber The netered card number
     * @param pin The entered pin
     * @return Whether to login was successful or not
     */
    public boolean logIn(String cardNumber, int pin) {
        if (this.cardNumber.equals(cardNumber) && this.pin == pin) {
            loggedIn = true;
        }

        return loggedIn;
    }

    /**
     * Sets the loggedIn field to false
     */
    public void logOut() {
        loggedIn = false;
    }

    /**
     * Takes the first 15 digits of a card number and calculates its 16th digit via the luhn algorithm
     *
     * @param cardNumber The first 15 digits of a card number
     * @return The entered 15 digits + the correct 16th digit, or "" if the parameter is not a 15 digit number
     */
    public static String luhn(String cardNumber) {
        if (!cardNumber.matches("\\d{15}")) {
            return "";
        }

        int checksum = 0;
        int current;
        for (int i = 0; i < 15; i++) {
            current = Character.getNumericValue(cardNumber.charAt(i));

            if (i % 2 == 0) {
                current *= 2;
            }

            if (current > 9) {
                current -= 9;
            }

            checksum += current;
        }

        int x = 0;

        while ((checksum + x) % 10 != 0) {
            x++;
        }

        return cardNumber + x;
    }

    /**
     * Increases the accounts balance by the entered amount
     *
     * @param income the amount to increase the balance by
     */
    public void addIncome(int income) {
        balance += income;
    }

    /**
     * Decreases this accounts balance by a certain amount and increases another accounts balance by the same amount.
     * Only works if this account is logged in.
     *
     * @param target the account the money will be transferred to
     * @param amount the amount of money to be transferred
     */
    public void transfer(Account target, int amount) {
        if (isLoggedIn()) {
            target.addIncome(amount);
            balance -= amount;
        }
    }

    /**
     * Creates an account with a random card number that passes the luhn-algorithm,
     * a random pin and a balance of 0.
     */
    public Account() {
        Random random = new Random();

        StringBuilder cardNumberBuilder = new StringBuilder();
        cardNumberBuilder.append("400000");
        String digits = "0123456789";
        for (int i = 0; i < 9; i++) {
            cardNumberBuilder.append(digits.charAt(random.nextInt(10)));
        }

        cardNumber = luhn(cardNumberBuilder.toString());

        pin = random.nextInt(10000);

        balance = 0;

        loggedIn = false;
    }

    /**
     * Creates an account based on the parameters
     *
     * @param cardNumber the card number the account will have
     * @param pin the pin the account will have
     * @param balance the balance the account will have
     */
    public Account(String cardNumber, int pin, int balance) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.balance = balance;

        loggedIn = false;
    }
}