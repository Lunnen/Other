import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Backend {

    public Backend() {
    }

    public static String checkBorrowed(String input) {
        return input != null ? "Lånad av kort: " + input : "LEDIG";
    }

    public static boolean checkInput(String input, String comparePattern) {
        Pattern p = Pattern.compile(comparePattern);

        Matcher matcher = p.matcher(input);

        while (matcher.find()) {
            if (matcher.group().length() != 0) {
                return true;
            }
        }
        return false;
    }

    public static void runCommand(Gui gui, String inputButton, String input) {
        input = input.toLowerCase();

        if (inputButton.equals("Borrow this Book")) { // !! Better to have this IF, than to have an entire switch-case function in OLD style, just for this case.
            if (runQuery(gui, "getIntValue", input, "select borrower from books where id = ?;", "library") == null) { // If the book's available to borrow. If not, nothing happens = You can't borrow a books that's already borrowed.
                System.out.println("Im here");
                updateValue(gui, input, "update books set borrower = 5 where id = ?;", "library"); //update borrower to id 5(customer) on selected
                gui.setBorrowed(runQuery(gui, inputButton, "5", "select * from books where borrower = ?;", "library")); //<--- Customer borrowerID is 5
            } else {
                gui.set("Already borrowed by someone!");
            }
        }
        switch (inputButton) {
            case "Search paper shelf" -> runQuery(gui, inputButton, "%" + input + "%", "select title, shelf from papers where title like ? ;", "library");
            case "Search books" -> runQuery(gui, inputButton, "%" + input + "%", "select * from books where title like ? ;", "library");
            case "Search by id" -> runQuery(gui, inputButton, input, "select * from books where borrower = ? ;", "library");
            case "Search by name" -> runQuery(gui, inputButton, input, "select id, isbn, title, author, pages, class from books, borrowers where books.borrower=borrowers.librarycard and borrowername = ? ;", "library");
            case "Return to shelf" -> updateValue(gui, input, "update books set borrower = NULL where id = ?;", "library");
        }
    }

    public static String runQuery(Gui gui, String inputButton, String input, String inputQuery, String inputDatabase) {

        String answer = "";

        if (checkInput(input, "[a-zåäö0-9]+")) {
            try {
                String inputUsername = gui.loginUser;
                String inputPassword = gui.loginPassword;

                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/" + inputDatabase + "", inputUsername, inputPassword);

                PreparedStatement p = connection.prepareStatement(inputQuery);
                p.setString(1, input);
                ResultSet rs = p.executeQuery();

                while (rs.next()) {
                    answer = switch (inputButton) {
                        case "getIntValue" -> (rs.getString(1));
                        case "Search paper shelf" -> answer + (rs.getString(1) + ",  " + rs.getString(2)) + "\n";
                        case "Search books borrowed" -> answer + (rs.getInt(1) + ", " + rs.getString(2) + ", " + rs.getString(3) + ", " + rs.getString(4) + ", " + rs.getString(5) + ", " + rs.getString(6) + ", " + checkBorrowed(rs.getString(7))) + "\n";
                        case "Search by name" -> answer + (rs.getInt(1) + ",  " + rs.getString(2) + ", " + rs.getString(3) + ", " + rs.getString(4) + ", " + rs.getString(5) + ", " + rs.getString(6)) + "\n";
                        case "Change employee values" -> answer + (rs.getString(1) + ", " + rs.getString(2) + ", " + rs.getString(3) + ", " + rs.getString(4) + ", " + rs.getString(5) + ", " + rs.getString(6) + ", " + rs.getString(7)) + ", " + rs.getString(8) + "\n";
                        case "Borrow this Book" -> answer + (rs.getInt(1) + ",  " + rs.getString(2) + ", " + rs.getString(3) + ", " + rs.getString(4) + ", " + rs.getString(5) + ", " + rs.getString(6)) + "\n";
                        default -> answer + (rs.getInt(1) + ", " + rs.getString(2) + ", " + rs.getString(3) + ", " + rs.getString(4) + ", " + rs.getString(5) + ", " + rs.getString(6) + ", " + checkBorrowed(rs.getString(7))) + "\n";
                    };
                }
                p.close();
                connection.close();

                if (!inputButton.equals("Borrow this Book") && !inputButton.equals("getIntValue")) { //This command should run, unless you borrow a book.
                    gui.set(!answer.equals("") ? answer : "No Result."); //If theres a result, show it. If not - show "No result".
                }

            } catch (Exception e) {
                gui.set(e.toString());
            }
        }
        return answer;
    }

    public static void updateValue(Gui gui, String inputId, String inputQuery, String inputDatabase) {

        try {
            String inputUsername = gui.loginUser;
            String inputPassword = gui.loginPassword;

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/" + inputDatabase + "", inputUsername, inputPassword);

            PreparedStatement ps = connection.prepareStatement(inputQuery);
            if(! inputId.equals("No need")) { ps.setString(1, inputId); }

            ps.executeUpdate();
            ps.close();
            connection.close();
        } catch (SQLException se) {
            gui.set(se.toString());
        }
    }

    public static void getAllOf(Gui gui, String inputTable, String inputDatabase) {

        String answer = "";

        try {
            String inputUsername = gui.loginUser;
            String inputPassword = gui.loginPassword;

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/" + inputDatabase + "", inputUsername, inputPassword);

            PreparedStatement ps = connection.prepareStatement("select * from " + inputTable + ";");
            //ps.setString(1, null);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (inputDatabase.equals("library")) {
                    answer = answer + (rs.getInt(1) + ",  " + rs.getString(2) + ",  " + rs.getString(3) + ", " + rs.getString(4) + ", " + rs.getString(5) + ", " + rs.getString(6)) + ", " + checkBorrowed(rs.getString(7)) + "\n";
                }
                if (inputDatabase.equals("employeeDB")) {
                    answer = answer + ("ID: " + rs.getInt(1) + ",  "
                            + "Name: " + rs.getString(2) + ", "
                            + "Salary: " + rs.getInt(7) + ", "
                            + "Vacation days left: " + rs.getInt(8) + "\n"
                            + "Address: " + rs.getString(3) + ", "
                            + "Phone numbers: " + rs.getString(4) + ", " + rs.getString(5) + ", " + rs.getString(6)) + "\n\n";
                }
            }
            gui.setBorrowed(answer);

            ps.executeQuery();
            ps.close();
            connection.close();
        } catch (SQLException se) {
            gui.set(se.toString());
        }
    }

}