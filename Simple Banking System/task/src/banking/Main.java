package banking;

import org.sqlite.SQLiteDataSource;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:" + args[1];
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        try (Connection con = dataSource.getConnection()) {
            // Statement creation
            try (Statement statement = con.createStatement()) {
                // Statement execution
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS card (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "number TEXT NOT NULL," +
                        "pin TEXT NOT NULL," +
                        "balance INTEGER DEFAULT 0)");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            int input = Gui.openingPage();
            while (input != 0) {
                if (input == 1) {
                    Gui.registerPage(url);
                    input = Gui.openingPage();
                } else if (input == 2) {
                    String cardNum = Gui.logInPage(url);
                    if (cardNum == null) {
                        input = Gui.openingPage();
                    } else {
                        int homePageInput = Gui.homePage(url, cardNum);
                        while (homePageInput != 0) {
                            if (homePageInput == 1) {
                                homePageInput = Gui.homePage(url, cardNum);
                            } else if (homePageInput == 2) {
                                input = Gui.openingPage();
                                break;
                            }
                        }
                        if (homePageInput == 0) {
                            input = 0;
                        }
                    }
                } else {
                    System.out.println("Wrong input. Try again.");
                    System.out.println();
                    input = Gui.openingPage();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Bye!");
    }
}