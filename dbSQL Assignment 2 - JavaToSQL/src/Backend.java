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

    /*  1. If the book's available to borrow. If not, nothing happens = You can't borrow a books that's already borrowed.
      2. update borrower to id 5(customer) on selected book & then refresh page to show that it's borrowed. */
    public static void borrowThisBook(Gui gui, String inputButton, String input){
        if (runQuery(gui, "getIntValue", input, "select borrower from books where id = ?;", "library") == null) {
            updateValue(gui, input, "update books set borrower = 5 where id = ?;", "library"); //
            gui.setBottomInfo(runQuery(gui, inputButton, "5", "select * from books where borrower = ?;", "library"));
        } else {
            gui.set("Already borrowed by someone!");
        }
    }

    public static void runCommand(Gui gui, String inputButton, String input) {
        input = input.toLowerCase();

        switch (inputButton) {
            case "Borrow this Book" -> borrowThisBook(gui, inputButton, input);
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

                if(!inputButton.equals("Select Employees") && !inputButton.equals("Select Books")){
                    p.setString(1, input);
                }

                ResultSet rs = p.executeQuery();

                while (rs.next()) {
                    answer = switch (inputButton) {
                        case "getIntValue" -> (rs.getString(1));
                        case "Search paper shelf" -> answer + (rs.getString(1) + ",  " + rs.getString(2)) + "\n";
                        case "Search by name", "Borrow this Book" -> answer + (rs.getInt(1) + ",  " + rs.getString(2) + ", " + rs.getString(3) + ", " + rs.getString(4) + ", " + rs.getString(5) + ", " + rs.getString(6)) + "\n";
                        case "Change employee values" -> answer + (rs.getString(1) + ", " + rs.getString(2) + ", " + rs.getString(3) + ", " + rs.getString(4) + ", " + rs.getString(5) + ", " + rs.getString(6) + ", " + rs.getString(7)) + ", " + rs.getString(8) + "\n";
                        case "Select Employees" -> answer = answer + ("ID: " + rs.getInt(1) + ",  " + "Name: " + rs.getString(2) + ", " + "Salary: " + rs.getInt(7) + ", " + "Vacation days left: " + rs.getInt(8) + "\n" + "Address: " + rs.getString(3) + ", " + "Phone numbers: " + rs.getString(4) + ", " + rs.getString(5) + ", " + rs.getString(6)) + "\n\n";
                        default -> answer + (rs.getInt(1) + ", " + rs.getString(2) + ", " + rs.getString(3) + ", " + rs.getString(4) + ", " + rs.getString(5) + ", " + rs.getString(6) + ", " + checkBorrowed(rs.getString(7))) + "\n";
                    };
                }
                p.close();
                connection.close();

                switch (inputButton){
                    case "Borrow this Book", "getIntValue", "Select Employees", "Select Books" : break; //in these cases, Don't get an answer.
                    default: gui.set(!answer.equals("") ? answer : "No Result."); //If theres a result, show it. If not - show "No result".
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
}