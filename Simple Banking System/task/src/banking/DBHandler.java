package banking;

import org.sqlite.SQLiteDataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Handles the access to the database, including the reading and writing of the card data
 */
public class DBHandler {
    private final SQLiteDataSource dataSource;

    /**
     * Initiates the data source and creates the card table in the database, if it doesn't already exist
     *
     * @param dbFileName the path to the database file
     */
    public DBHandler(String dbFileName) {
        dataSource = new SQLiteDataSource();
        dataSource.setUrl("jdbc:sqlite:" + dbFileName);

        try (Connection con = dataSource.getConnection()) {
            try (Statement statement = con.createStatement()) {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS card(" +
                        "id INTEGER PRIMARY KEY," +
                        "number TEXT," +
                        "pin TEXT," +
                        "balance INTEGER DEFAULT 0)");

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads all banking cards from the database and returns them as an arraylist
     *
     * @return The arraylist containing the card data from the database
     */
    public ArrayList<Account> getCards() {
        ArrayList<Account> res = new ArrayList<>();

        try (Connection con = dataSource.getConnection()) {
            try (Statement statement = con.createStatement()) {
                ResultSet cards = statement.executeQuery("Select * From card");

                while (cards.next()) {
                    res.add(new Account(cards.getString("number"), Integer.parseInt(cards.getString("pin")), cards.getInt("balance")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    /**
     * Clears the card table and then saves the data of every card in an arraylist in said table.
     * To prevent data loss, the changes will only be committed after every INSERT statement has been executed.
     *
     * @param cards the arraylist of cards that will be saved in the databse
     */
    public void setCards(ArrayList<Account> cards) {
        try (Connection con = dataSource.getConnection()) {
            try (Statement statement = con.createStatement()) {
                con.setAutoCommit(false);

                statement.executeUpdate("Delete From card");

                for (Account card: cards) {
                    statement.executeUpdate("INSERT INTO card (number, pin, balance) VALUES ('" +
                            card.getCardNumber() + "', '" + card.getPin() + "', " + card.getBalance() + ")");
                }

                con.commit();
            } catch (SQLException e) {
                con.rollback();
                e.printStackTrace();
            } finally {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
