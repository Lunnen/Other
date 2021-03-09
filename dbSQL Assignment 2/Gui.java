import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class Gui extends JFrame {

    //Login
    protected JPanel loginPanel;
    protected JLabel loginMessage;
    protected JButton loginAsCustomer;
    protected JButton loginAsLibrarian;
    protected JButton loginAsAdmin;

    // Base for Customer/Librarian/Admin
    protected JPanel rootPanel;
    protected JPanel search;
    protected JPanel searchButtons;
    protected JPanel answerField;
    protected JPanel borrowedField;
    protected JPanel borrowFunction;
    protected JPanel login;

    protected String name;
    protected JTextField tf;
    protected JTextField inputBookID;
    protected JTextArea ta;
    protected JTextArea borrowedArea;
    protected JButton borrowBook;
    protected JButton searchBooks;
    protected JButton searchPapers;


    /*
    användarid: 'customer' (Marcus Gefvert lånekort 5) - abc12345
    bibliotekare-id: 'librarian' - abc12345
    Administratör för löner - 'admin' - abc12345
    */

    String loginUser = "";
    String loginPassword = "";

    public Gui(String guiType) {

        switch (guiType) {

            //***************************************************************************************** <--
            default:
                setAppSettings(); //<--- Set the default title, size and so on...

                //JPanel settings
                this.loginPanel = new JPanel(new GridLayout(4, 1, 5, 5));

                this.loginMessage = new JLabel("Pick your type of login: ");
                this.loginMessage.setFont(new Font("serif", Font.PLAIN, 32));
                this.loginAsCustomer = new JButton("A customer");
                this.loginAsLibrarian = new JButton("A librarian");
                this.loginAsAdmin = new JButton("A salary administrator");
                // ***********************************************************
                ActionListener loginListener = action -> {

                    switch (action.getActionCommand()) {
                        case "A customer" -> {
                            setVisible(false);
                            new Gui("customer");
                        }
                        case "A librarian" -> {
                            setVisible(false);
                            new Gui("librarian");
                        }
                    }
                };
                loginAsCustomer.addActionListener(loginListener);
                loginAsLibrarian.addActionListener(loginListener);
                loginAsAdmin.addActionListener(loginListener);
                // ***********************************************************

                //Contents of panels
                this.loginPanel.add(loginMessage);
                this.loginPanel.add(loginAsCustomer);
                this.loginPanel.add(loginAsLibrarian);
                this.loginPanel.add(loginAsAdmin);

                //Set order of panels
                this.add(loginPanel);

                this.setLocationRelativeTo(null);
                this.setVisible(true);
                this.setResizable(false);
                break;

            //***************************************************************************************** <--
            case "customer":    //Customer GUI

                loginUser = "customer";
                loginPassword = "abc12345";

                tf = new JTextField("", 20);
                tf.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Search: "));
                name = "-1";

                setAppSettings();

                //JPanel settings
                this.rootPanel = new JPanel(null);

                this.search = new JPanel(new GridLayout(1, 1, 5, 5));
                this.search.setBounds(5, 5, 675, 50);

                this.searchButtons = new JPanel(new GridLayout(1, 1, 5, 5));
                this.searchButtons.setBounds(5, 60, 675, 30);

                this.answerField = new JPanel(new GridLayout(1, 1, 5, 5));
                this.answerField.setBounds(5, 95, 675, 200);

                this.borrowFunction = new JPanel(new GridLayout(1, 2, 5, 5));
                this.borrowFunction.setBounds(5, 300, 675, 30);

                this.borrowedField = new JPanel(new GridLayout(1, 1, 5, 5));
                this.borrowedField.setBounds(0, 335, 685, 200);
                borrowedField.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "You have borrowed: "));

                this.login = new JPanel(new GridLayout(1, 2, 5, 5));
                this.login.setBounds(5, 440, 675, 30);

                ActionListener libal = e -> {
                    this.name = tf.getText();
                };

                tf.addActionListener(libal);
                tf.setBackground(Color.lightGray);

                ta = new JTextArea();
                ta.setBackground(Color.lightGray);

                borrowedArea = new JTextArea();
                borrowedArea.setBackground(Color.lightGray);

                this.searchBooks = new JButton("Search books");
                this.searchPapers = new JButton("Search paper shelf");
                this.inputBookID = new JTextField("Book ID to borrow", 20);
                this.borrowBook = new JButton("Borrow this Book");

                // ***********************************************************
                ActionListener inputListenerObject = action -> {

                    Backend.runCommand(this, action.getActionCommand(), tf.getText()); //Send which button pushed to method and text typed
                };
                searchBooks.addActionListener(inputListenerObject);
                searchPapers.addActionListener(inputListenerObject);
                // ***********************************************************
                ActionListener listenToBookId = action -> {

                    Backend.runCommand(this, action.getActionCommand(), inputBookID.getText());
                };
                borrowBook.addActionListener(listenToBookId);
                // ***********************************************************


                //Contents of panels
                this.search.add(tf);

                this.searchButtons.add(searchBooks);
                this.searchButtons.add(searchPapers);

                this.answerField.add(ta);

                this.borrowFunction.add(inputBookID);
                this.borrowFunction.add(borrowBook);

                this.borrowedField.add(borrowedArea);

                //Set order of panels
                this.add(rootPanel);
                this.rootPanel.add(this.search);
                this.rootPanel.add(this.searchButtons);
                this.rootPanel.add(this.answerField);
                this.rootPanel.add(this.borrowFunction);
                this.rootPanel.add(this.borrowedField);
                this.rootPanel.add(this.login);

                this.setLocationRelativeTo(null);
                this.setVisible(true);
                this.setResizable(false);

                Backend.atstartCheckWhatsBorrowed(this); // Show what user currently has borrowed
                break;
            //***************************************************************************************** <--
            case "librarian":
                loginUser = "librarian";
                loginPassword = "abc12345";

                tf = new JTextField("", 20);
                tf.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Search books borrowed by: "));
                name = "-1";

                setAppSettings();

                //JPanel settings
                this.rootPanel = new JPanel(null);

                this.search = new JPanel(new GridLayout(1, 1, 5, 5));
                this.search.setBounds(5, 5, 675, 50);

                this.searchButtons = new JPanel(new GridLayout(1, 1, 5, 5));
                this.searchButtons.setBounds(5, 60, 675, 30);

                this.answerField = new JPanel(new GridLayout(1, 1, 5, 5));
                this.answerField.setBounds(5, 95, 675, 200);

                this.borrowFunction = new JPanel(new GridLayout(1, 2, 5, 5));
                this.borrowFunction.setBounds(5, 300, 675, 30);

                this.borrowedField = new JPanel(new GridLayout(1, 1, 5, 5));
                this.borrowedField.setBounds(0, 335, 685, 325);
                borrowedField.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "All books in the library: "));

                ActionListener al = e -> { this.name = tf.getText(); };

                tf.addActionListener(al);
                tf.setBackground(Color.lightGray);

                ta = new JTextArea();
                ta.setBackground(Color.lightGray);

                borrowedArea = new JTextArea();
                borrowedArea.setBackground(Color.lightGray);

                this.searchBooks = new JButton("Search by id");
                this.searchPapers = new JButton("Search by name");
                this.inputBookID = new JTextField("Return book with ID", 20);
                this.borrowBook = new JButton("Return to shelf");

                // ***********************************************************
                ActionListener libinputListenerObject = action -> {

                    Backend.runCommand(this, action.getActionCommand(), tf.getText()); //Send which button pushed to method and text typed

                    //Backend.runQuery(this, "default", libtf.getText(), "select * from books ;");
                };
                searchBooks.addActionListener(libinputListenerObject);
                searchPapers.addActionListener(libinputListenerObject);
                // ***********************************************************
                ActionListener liblistenToBookId = action -> {

                    Backend.runCommand(this, action.getActionCommand(), inputBookID.getText());
                };
                borrowBook.addActionListener(liblistenToBookId);
                // ***********************************************************

                //Contents of panels
                this.search.add(tf);

                this.searchButtons.add(searchBooks);
                this.searchButtons.add(searchPapers);

                this.answerField.add(ta);

                this.borrowFunction.add(inputBookID);
                this.borrowFunction.add(borrowBook);

                this.borrowedField.add(borrowedArea);

                //Set order of panels
                this.add(rootPanel);
                this.rootPanel.add(this.search);
                this.rootPanel.add(this.searchButtons);
                this.rootPanel.add(this.answerField);
                this.rootPanel.add(this.borrowFunction);
                this.rootPanel.add(this.borrowedField);

                this.setLocationRelativeTo(null);
                this.setVisible(true);
                this.setResizable(false);

                Backend.getAllBooks(this);

                break;

            //***************************************************************************************** <--

        }
    }

    public String getVal() {
        if (this.name.equals("-1")) {
            return "-1";
        }
        String n = name;
        name = "-1";
        return n;
    }

    public void set(String text) {
        this.ta.setText("Answer: \n" + text);

    }

    public void setBorrowed(String text) {
        this.borrowedArea.setText(text);

    }
    public void setAppSettings() {
        this.setTitle("EC SQL assignment 2");
        this.setIconImage(new ImageIcon("./ico/icon.png").getImage());
        this.setSize(700, 700);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


}
