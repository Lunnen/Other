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

    // Base for Customer/Librarian/HRadmin
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
    protected JButton actonIDchosen;
    protected JButton searchBooks;
    protected JButton searchPapers;


    /*
    användarid: 'customer' (Marcus Gefvert lånekort 5) - abc12345
    bibliotekare-id: 'librarian' - abc12345
    Administratör för löner - 'hradmin' - abc12345
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

                this.loginMessage = new JLabel("Login as: ", SwingConstants.CENTER);
                this.loginMessage.setFont(new Font("serif", Font.PLAIN, 32));
                this.loginAsCustomer = new JButton("A customer");
                this.loginAsLibrarian = new JButton("A librarian");
                this.loginAsAdmin = new JButton("A salary administrator");
                // ***********************************************************
                ActionListener loginListener = action -> {

                    switch (action.getActionCommand()) {
                        case "A customer" -> switchGui("customer");
                        case "A librarian" -> switchGui("librarian");
                        case "A salary administrator" -> switchGui("hradmin");
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

                ActionListener al = e -> this.name = tf.getText();

                tf.addActionListener(al);
                tf.setBackground(Color.lightGray);

                ta = new JTextArea();
                ta.setBackground(Color.lightGray);

                borrowedArea = new JTextArea();
                borrowedArea.setBackground(Color.lightGray);

                this.searchBooks = new JButton("Search books");
                this.searchPapers = new JButton("Search paper shelf");
                this.inputBookID = new JTextField("Book ID to borrow", 20);
                this.actonIDchosen = new JButton("Borrow this Book");

                // ***********************************************************
                ActionListener inputListenerObject = action -> {

                    Backend.runCommand(this, action.getActionCommand(), tf.getText()); //Send which button pushed to method and text typed
                };
                searchBooks.addActionListener(inputListenerObject);
                searchPapers.addActionListener(inputListenerObject);
                // ***********************************************************
                ActionListener listenToBookId = action -> Backend.runCommand(this, action.getActionCommand(), inputBookID.getText());
                actonIDchosen.addActionListener(listenToBookId);
                // ***********************************************************


                //Contents of panels
                this.search.add(tf);

                this.searchButtons.add(searchBooks);
                this.searchButtons.add(searchPapers);

                this.answerField.add(ta);

                this.borrowFunction.add(inputBookID);
                this.borrowFunction.add(actonIDchosen);

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

                ActionListener libal = e -> this.name = tf.getText();

                tf.addActionListener(libal);
                tf.setBackground(Color.lightGray);

                ta = new JTextArea();
                ta.setBackground(Color.lightGray);

                borrowedArea = new JTextArea();
                borrowedArea.setBackground(Color.lightGray);

                this.searchBooks = new JButton("Search by id");
                this.searchPapers = new JButton("Search by name");
                this.inputBookID = new JTextField("Return book with ID", 20);
                this.actonIDchosen = new JButton("Return to shelf");

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
                    Backend.getAllOf(this, "books", "library");
                };
                actonIDchosen.addActionListener(liblistenToBookId);
                // ***********************************************************

                //Contents of panels
                this.search.add(tf);

                this.searchButtons.add(searchBooks);
                this.searchButtons.add(searchPapers);

                this.answerField.add(ta);

                this.borrowFunction.add(inputBookID);
                this.borrowFunction.add(actonIDchosen);

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

                Backend.getAllOf(this, "books", "library");

                break;

            //***************************************************************************************** <--
            case "hradmin":

                loginUser = "hradmin";
                loginPassword = "abc12345";

                tf = new JTextField("", 20);
                tf.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Search books borrowed by: "));
                name = "-1";

                setAppSettings();

                //JPanel settings
                this.rootPanel = new JPanel(null);

                this.borrowFunction = new JPanel(new GridLayout(1, 2, 5, 5));
                this.borrowFunction.setBounds(5, 5, 675, 40);

                this.borrowedField = new JPanel(new GridLayout(1, 1, 5, 5));
                this.borrowedField.setBounds(0, 45, 685, 425);
                borrowedField.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "All personnel: "));

                ActionListener admal = e -> this.name = tf.getText();

                tf.addActionListener(admal);
                tf.setBackground(Color.lightGray);

                ta = new JTextArea();
                ta.setBackground(Color.lightGray);

                borrowedArea = new JTextArea();
                borrowedArea.setBackground(Color.lightGray);

                this.searchBooks = new JButton("Search by id");
                this.searchPapers = new JButton("Search by name");
                this.inputBookID = new JTextField(20);
                this.inputBookID.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Enter employee ID"));
                this.actonIDchosen = new JButton("Change employee values");

                // ***********************************************************
                ActionListener hrlistenButton = action -> {

                    /* Do something ONLY  if the employee exists in the database */
                    if (Backend.runQuery(this, "Def", inputBookID.getText(), "select * from employees where id = ?;", "EmployeeDB").length() > 0) {


                        JTextField FieldID = new JTextField(inputBookID.getText(), 20);
                        FieldID.setEditable(false);
                        JTextField Field1 = new JTextField(20);
                        JTextField Field2 = new JTextField(20);
                        JTextField Field3 = new JTextField(20);
                        JTextField Field4 = new JTextField(20);
                        JTextField Field5 = new JTextField(20);
                        JTextField Field6 = new JTextField(20);
                        JTextField Field7 = new JTextField(20);


                        JPanel myPanel = new JPanel(new GridLayout(8, 1, 5, 5));
                        myPanel.add(new JLabel("Employee ID chosen: "));
                        myPanel.add(FieldID);
                        myPanel.add(new JLabel("Name: "));
                        myPanel.add(Field1);
                        myPanel.add(new JLabel("Address:"));
                        myPanel.add(Field2);
                        myPanel.add(new JLabel("Phone number 1:"));
                        myPanel.add(Field3);
                        myPanel.add(new JLabel("Phone number 2:"));
                        myPanel.add(Field4);
                        myPanel.add(new JLabel("Phone number 3:"));
                        myPanel.add(Field5);
                        myPanel.add(new JLabel("Salary:"));
                        myPanel.add(Field6);
                        myPanel.add(new JLabel("Vacation days left:"));
                        myPanel.add(Field7);


                        String getValues = Backend.runQuery(this, "Change employee values", FieldID.getText(), "select * from employees where id = ?;", "EmployeeDB");
                        System.out.println(getValues);

                        String[] gottenValues = getValues.split(", ");

                        FieldID.setText(gottenValues[0]);
                        Field1.setText(gottenValues[1]);
                        Field2.setText(gottenValues[2]);
                        Field3.setText(gottenValues[3]);
                        Field4.setText(gottenValues[4]);
                        Field5.setText(gottenValues[5]);
                        Field6.setText(gottenValues[6]);
                        Field7.setText(gottenValues[7]);

                        for(String print : gottenValues){
                            System.out.println(print);
                        }

                        int result = JOptionPane.showConfirmDialog(null, myPanel, "Employee values to update", JOptionPane.OK_CANCEL_OPTION);

                        if (result == JOptionPane.OK_OPTION) {

                            if (!Field1.getText().equals(gottenValues[1])) { //If a change has been entered, update to database.
                                Backend.updateValue(this, Field1.getText(), "update employees set name = ? where id = "+FieldID.getText()+" ;", "employeeDB");
                            }
                            if (!Field2.getText().equals(gottenValues[2])) { //If a change has been entered, update to database.
                                Backend.updateValue(this, Field2.getText(), "update employees set address = ? where id = "+FieldID.getText()+" ;", "employeeDB");
                            }
                            if (!Field3.getText().equals(gottenValues[3])) { //If a change has been entered, update to database.
                                Backend.updateValue(this, Field3.getText(), "update employees set phonenumber1 = ? where id = "+FieldID.getText()+" ;", "employeeDB");
                            }
                            if (!Field4.getText().equals(gottenValues[4])) { //If a change has been entered, update to database.
                                Backend.updateValue(this, Field4.getText(), "update employees set phonenumber2 = ? where id = "+FieldID.getText()+" ;", "employeeDB");
                            }
                            if (!Field5.getText().equals(gottenValues[5])) { //If a change has been entered, update to database.
                                Backend.updateValue(this, Field5.getText(),"update employees set phonenumber3 = ? where id = "+FieldID.getText()+" ;", "employeeDB");
                            }
                            if (!Field6.getText().equals(gottenValues[6])) { //If a change has been entered, update to database.
                                Backend.updateValue(this, Field6.getText(),"update employees set salary = ? where id = "+FieldID.getText()+" ;", "employeeDB");
                            }
                            if (!Field7.getText().equals(gottenValues[7])) { //If a change has been entered, update to database.
                                Backend.updateValue(this, Field7.getText(),"update employees set vacationleft = ? where id = "+FieldID.getText()+" ;", "employeeDB");
                            }
                            Backend.getAllOf(this, "employees", "employeeDB");
                        }
                    }

                };
                actonIDchosen.addActionListener(hrlistenButton);
                // ***********************************************************

                //Contents of panels

                this.borrowFunction.add(inputBookID);
                this.borrowFunction.add(actonIDchosen);

                this.borrowedField.add(borrowedArea);

                //Set order of panels
                this.add(rootPanel);
                this.rootPanel.add(this.borrowFunction);
                this.rootPanel.add(this.borrowedField);

                this.setLocationRelativeTo(null);
                this.setVisible(true);
                this.setResizable(false);

                // Show all employees at the start of GUI
                Backend.getAllOf(this, "employees", "employeeDB");

                break;
            //***************************************************************************************** <--

        }
    }

    public void set(String text) {
        this.ta.setText("Answer: \n" + text);

    }

    public void setBorrowed(String text) {
        this.borrowedArea.setText(text);

    }

    /* Sets basic settings for all GUI's */
    public void setAppSettings() {
        this.setTitle("EC SQL assignment 2");
        this.setIconImage(new ImageIcon("./ico/icon.png").getImage());
        this.setSize(700, 700);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void switchGui(String thisType) {
        setVisible(false);
        new Gui(thisType);
    }

}
