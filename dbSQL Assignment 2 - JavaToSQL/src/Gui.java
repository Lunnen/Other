import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
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
    protected JPanel topButtons;
    protected JPanel answerField;
    protected JPanel bottomField;
    protected JPanel bottomFunction;
    protected JPanel loginChange;

    protected String name;
    protected JTextField tf;
    protected JTextField inputID;
    protected JTextArea ta;
    protected JTextArea bottomInfo;
    protected JButton actonIDchosen;
    protected JButton topLeftButton;
    protected JButton topRightButton;
    protected JButton changeLoginType;

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
                tf.setBorder(BorderFactory.createTitledBorder("Search: "));
                name = "-1";

                setAppSettings();

                //JPanel settings
                this.rootPanel = new JPanel(null);
                this.search = new JPanel(new GridLayout(1, 1, 5, 5));
                this.search.setBounds(5, 5, 675, 50);

                this.topButtons = new JPanel(new GridLayout(1, 1, 5, 5));
                this.topButtons.setBounds(5, 60, 675, 40);

                this.answerField = new JPanel(new GridLayout(1, 1, 5, 5));
                this.answerField.setBounds(5, 105, 675, 240);

                this.bottomFunction = new JPanel(new GridLayout(1, 2, 5, 5));
                this.bottomFunction.setBounds(5, 350, 675, 40);

                this.bottomField = new JPanel(new GridLayout(1, 1, 5, 5));
                this.bottomField.setBounds(0, 390, 685, 230);
                bottomField.setBorder(BorderFactory.createTitledBorder("You have borrowed: "));

                this.loginChange = new JPanel(new GridLayout(1, 1, 5, 5));
                this.loginChange.setBounds(5, 620, 675, 35);

                ActionListener al = e -> this.name = tf.getText();
                tf.addActionListener(al);
                tf.setBackground(Color.lightGray);
                ta = new JTextArea();
                ta.setBackground(Color.lightGray);
                bottomInfo = new JTextArea();
                bottomInfo.setBackground(Color.lightGray);

                this.topLeftButton = new JButton("Search books");
                this.topRightButton = new JButton("Search paper shelf");
                this.inputID = new JTextField(20);
                inputID.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Book ID to borrow: "));
                this.actonIDchosen = new JButton("Borrow this Book");
                this.changeLoginType = new JButton("Change login");

                // ***********************************************************
                ActionListener inputListenerObject = action -> Backend.runCommand(this, action.getActionCommand(), tf.getText()); //Send which button pushed to method and text typed
                topLeftButton.addActionListener(inputListenerObject);
                topRightButton.addActionListener(inputListenerObject);
                // ***********************************************************
                ActionListener listenToBookId = action -> Backend.runCommand(this, action.getActionCommand(), inputID.getText());
                actonIDchosen.addActionListener(listenToBookId);
                // ***********************************************************
                ActionListener loginChangeListener = action -> switchGui("default");
                changeLoginType.addActionListener(loginChangeListener);
                // ***********************************************************

                //Contents of panels
                this.search.add(tf);
                this.topButtons.add(topLeftButton);
                this.topButtons.add(topRightButton);
                this.answerField.add(ta);
                this.bottomFunction.add(inputID);
                this.bottomFunction.add(actonIDchosen);
                this.bottomField.add(bottomInfo);
                this.loginChange.add(changeLoginType);

                //Set order of panels
                this.add(rootPanel);
                this.rootPanel.add(this.search);
                this.rootPanel.add(this.topButtons);
                this.rootPanel.add(this.answerField);
                this.rootPanel.add(this.bottomFunction);
                this.rootPanel.add(this.bottomField);
                this.rootPanel.add(this.loginChange);

                this.setLocationRelativeTo(null);
                this.setVisible(true);
                this.setResizable(false);

                //Show what user currently has borrowed
                this.setBottomInfo(Backend.runQuery(this, "Borrow this Book", "5", "select * from books where borrower = ? ;", "library"));
                break;
            //***************************************************************************************** <--
            case "librarian":
                loginUser = "librarian";
                loginPassword = "abc12345";

                tf = new JTextField("", 20);
                tf.setBorder(BorderFactory.createTitledBorder("Search books borrowed by: "));
                name = "-1";

                setAppSettings();

                //JPanel settings
                this.rootPanel = new JPanel(null);
                this.search = new JPanel(new GridLayout(1, 1, 5, 5));
                this.search.setBounds(5, 5, 675, 50);

                this.topButtons = new JPanel(new GridLayout(1, 1, 5, 5));
                this.topButtons.setBounds(5, 60, 675, 40);

                this.answerField = new JPanel(new GridLayout(1, 1, 5, 5));
                this.answerField.setBounds(5, 105, 675, 150);

                this.bottomFunction = new JPanel(new GridLayout(1, 2, 5, 5));
                this.bottomFunction.setBounds(5, 260, 675, 40);

                this.bottomField = new JPanel(new GridLayout(1, 1, 5, 5));
                this.bottomField.setBounds(0, 300, 685, 320);
                bottomField.setBorder(BorderFactory.createTitledBorder("All books in the library: "));

                this.loginChange = new JPanel(new GridLayout(1, 1, 5, 5));
                this.loginChange.setBounds(5, 620, 675, 35);

                ActionListener libal = e -> this.name = tf.getText();
                tf.addActionListener(libal);
                tf.setBackground(Color.lightGray);
                ta = new JTextArea();
                ta.setBackground(Color.lightGray);
                bottomInfo = new JTextArea();
                bottomInfo.setBackground(Color.lightGray);

                this.topLeftButton = new JButton("Search by id");
                this.topRightButton = new JButton("Search by name");
                this.inputID = new JTextField(20);
                inputID.setBorder(BorderFactory.createTitledBorder("Return book with ID: "));
                this.actonIDchosen = new JButton("Return to shelf");
                this.changeLoginType = new JButton("Change login");

                // ***********************************************************
                ActionListener libinputListenerObject = action -> Backend.runCommand(this, action.getActionCommand(), tf.getText()); //Send which button pushed to method and text typed
                topLeftButton.addActionListener(libinputListenerObject);
                topRightButton.addActionListener(libinputListenerObject);
                // ***********************************************************
                ActionListener liblistenToBookId = action -> {
                    Backend.runCommand(this, action.getActionCommand(), inputID.getText());
                    this.setBottomInfo(Backend.runQuery(this, "Select Books", "none", "select * from books;", "library"));
                };
                actonIDchosen.addActionListener(liblistenToBookId);
                // ***********************************************************
                ActionListener libloginChangeListener = action -> switchGui("default");
                changeLoginType.addActionListener(libloginChangeListener);
                // ***********************************************************

                //Contents of panels
                this.search.add(tf);
                this.topButtons.add(topLeftButton);
                this.topButtons.add(topRightButton);
                this.answerField.add(ta);
                this.bottomFunction.add(inputID);
                this.bottomFunction.add(actonIDchosen);
                this.bottomField.add(bottomInfo);
                this.loginChange.add(changeLoginType);

                //Set order of panels
                this.add(rootPanel);
                this.rootPanel.add(this.search);
                this.rootPanel.add(this.topButtons);
                this.rootPanel.add(this.answerField);
                this.rootPanel.add(this.bottomFunction);
                this.rootPanel.add(this.bottomField);
                this.rootPanel.add(this.loginChange);

                this.setLocationRelativeTo(null);
                this.setVisible(true);
                this.setResizable(false);

                this.setBottomInfo(Backend.runQuery(this, "Select Books", "none", "select * from books;", "library"));

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
                this.bottomFunction = new JPanel(new GridLayout(1, 2, 5, 5));
                this.bottomFunction.setBounds(5, 5, 675, 40);
                this.topButtons = new JPanel(new GridLayout(1, 2, 5, 5));
                this.topButtons.setBounds(5, 50, 675, 40);
                this.bottomField = new JPanel(new GridLayout(1, 1, 5, 5));
                this.bottomField.setBounds(0, 95, 685, 525);
                bottomField.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "All personnel: "));
                this.loginChange = new JPanel(new GridLayout(1, 1, 5, 5));
                this.loginChange.setBounds(5, 620, 675, 35);

                ActionListener admal = e -> this.name = tf.getText();
                tf.addActionListener(admal);
                tf.setBackground(Color.lightGray);
                ta = new JTextArea();
                ta.setBackground(Color.lightGray);
                bottomInfo = new JTextArea();
                bottomInfo.setBackground(Color.lightGray);
                this.topLeftButton = new JButton("Insert new employee");
                this.topRightButton = new JButton("Delete employee");
                this.actonIDchosen = new JButton("Change employee values");
                this.changeLoginType = new JButton("Change login");
                this.inputID = new JTextField(20);
                this.inputID.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Enter employee ID"));

                ActionListener hrlistenButton = action -> {
                    switch (action.getActionCommand()) {
                        case "Change employee values" -> runOptions("change");
                        case "Insert new employee" -> runOptions("insert");
                        case "Delete employee" -> runOptions("delete");
                        case "Change login" -> switchGui("default");
                    }
                };
                actonIDchosen.addActionListener(hrlistenButton);
                topLeftButton.addActionListener(hrlistenButton);
                topRightButton.addActionListener(hrlistenButton);
                changeLoginType.addActionListener(hrlistenButton);
                // ***********************************************************

                //Contents of panels
                this.bottomFunction.add(inputID);
                this.bottomFunction.add(actonIDchosen);
                this.topButtons.add(topLeftButton);
                this.topButtons.add(topRightButton);
                this.bottomField.add(bottomInfo);
                this.loginChange.add(changeLoginType);

                //Set order of panels
                this.add(rootPanel);
                this.rootPanel.add(this.bottomFunction);
                this.rootPanel.add(this.topButtons);
                this.rootPanel.add(this.bottomField);
                this.rootPanel.add(this.loginChange);

                this.setLocationRelativeTo(null);
                this.setVisible(true);
                this.setResizable(false);

                // Show all employees at the start of GUI
                this.setBottomInfo(Backend.runQuery(this, "Select Employees", "none", "select * from employees;", "employeeDB"));
                //Backend.getAllOf(this, "employees", "employeeDB");

                break;
            //***************************************************************************************** <--

        }
    }

    public void set(String text) {
        this.ta.setText("Answer: \n" + text);

    }

    public void setBottomInfo(String text) {
        this.bottomInfo.setText(text);

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

    public void runOptions(String inputChoice) {

        switch (inputChoice) {

            case "change":
                /* Do something ONLY  if the employee exists in the database */
                if (Backend.runQuery(this, "Def", inputID.getText(), "select * from employees where id = ?;", "EmployeeDB").length() > 0) {

                    JTextField[] Field = new JTextField[8];
                    Field[0] = new JTextField(inputID.getText(), 20);
                    Field[0].setEditable(false);

                    for (int i = 1; i < Field.length; i++) {
                        Field[i] = new JTextField(20);
                    }

                    JButton updateBtn = new JButton("Update value");
                    JButton cancelButton = new JButton("Cancel");

                    JButton validate = new JButton("Validate");
                    JPanel myPanel = new JPanel(new GridLayout(9, 1, 5, 5));
                    myPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                    myPanel.add(new JLabel("Employee ID chosen: "));
                    myPanel.add(Field[0]);
                    myPanel.add(new JLabel("Name: "));
                    myPanel.add(Field[1]);
                    myPanel.add(new JLabel("Address:"));
                    myPanel.add(Field[2]);
                    myPanel.add(new JLabel("Phone number 1:"));
                    myPanel.add(Field[3]);
                    myPanel.add(new JLabel("Phone number 2: *Optional"));
                    myPanel.add(Field[4]);
                    myPanel.add(new JLabel("Phone number 3: *Optional"));
                    myPanel.add(Field[5]);
                    myPanel.add(new JLabel("Salary:"));
                    myPanel.add(Field[6]);
                    myPanel.add(new JLabel("Vacation days left:"));
                    myPanel.add(Field[7]);
                    myPanel.add(updateBtn);
                    myPanel.add(cancelButton);

                    //Get all the current values of chose employee
                    String getValues = Backend.runQuery(this, "Change employee values", Field[0].getText(), "select * from employees where id = ?;", "EmployeeDB");
                    String[] gottenValues = getValues.split(", "); // Split the gotten string into values of their own.

                    //Get DB values and show them inside OptionPane GUI.
                    for (int i = 1; i < gottenValues.length; i++) {
                        Field[i].setText(gottenValues[i]);
                    }

                    ActionListener validation = action -> {

                    };
                    validate.addActionListener(validation);

                    // Name of the columns that's updated
                    String[] columnInput = new String[8];
                    columnInput[1] = "name";
                    columnInput[2] = "address";
                    columnInput[3] = "phonenumber1";
                    columnInput[4] = "phonenumber2";
                    columnInput[5] = "phonenumber3";
                    columnInput[6] = "salary";
                    columnInput[7] = "vacationleft";

                    JDialog dialog = new JDialog(this, "Change employee values");

                    dialog.add(myPanel);
                    dialog.setSize(400, 300);
                    dialog.setIconImage(new ImageIcon("./ico/icon.png").getImage());
                    dialog.setLocationRelativeTo(null); //Centers window location upon creation
                    dialog.setVisible(true);
                    dialog.setResizable(false);

                    ActionListener dialogListener = action -> {
                        // Checks if the inputed values are in the right format
                        if (action.getActionCommand().equals("Update value")) {
                            if (runFieldCheck(Field)) { // IF field is validated correctly, accept update of database.
                                /* If a change has been entered, update to database. */
                                for (int i = 1; i < columnInput.length; i++) {
                                    if (!Field[i].getText().equals(gottenValues[i])) {
                                        Backend.updateValue(this, Field[i].getText(), "update employees set " + columnInput[i] + " = ? where id = " + Field[0].getText() + " ;", "employeeDB");
                                    }
                                }
                                this.setBottomInfo(Backend.runQuery(this, "Select Employees", "none", "select * from employees;", "employeeDB"));
                                dialog.dispose();
                            } else {
                                runFieldValidation(Field);
                            }
                        } else {
                            dialog.dispose();
                        }
                    };
                    cancelButton.addActionListener(dialogListener);
                    updateBtn.addActionListener(dialogListener);
                }
                break;
            // *******************************************************************************************************************************
            case "insert":

                JTextField[] Field = new JTextField[8];

                for (int i = 0; i < Field.length; i++) {
                    Field[i] = new JTextField(20);
                }

                JButton insertion = new JButton("Insert to Database");
                JButton cancelButton = new JButton("Cancel");

                JPanel myPanel = new JPanel(new GridLayout(8, 1, 5, 5));
                myPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                myPanel.add(new JLabel("Name: "));
                myPanel.add(Field[1]);
                myPanel.add(new JLabel("Address:"));
                myPanel.add(Field[2]);
                myPanel.add(new JLabel("Phone number 1:"));
                myPanel.add(Field[3]);
                myPanel.add(new JLabel("Phone number 2: *Optional"));
                myPanel.add(Field[4]);
                myPanel.add(new JLabel("Phone number 3: *Optional"));
                myPanel.add(Field[5]);
                myPanel.add(new JLabel("Salary:"));
                myPanel.add(Field[6]);
                myPanel.add(new JLabel("Vacation days left:"));
                myPanel.add(Field[7]);
                myPanel.add(insertion);
                myPanel.add(cancelButton);

                JDialog dialog = new JDialog(this, "Insert new employee");

                dialog.add(myPanel);
                dialog.setSize(400, 300);
                dialog.setIconImage(new ImageIcon("./ico/icon.png").getImage());
                dialog.setLocationRelativeTo(null); //Centers window location upon creation
                dialog.setVisible(true);
                dialog.setResizable(false);

                ActionListener dialogListener = action -> {
                    // Checks if the inputed values are in the right format
                    if (action.getActionCommand().equals("Insert to Database")) {
                        if (runFieldCheck(Field)) { // IF field is validated correctly, accept update of database.
                            Backend.updateValue(this, "No need", "insert into employees (name, address, phonenumber1, phonenumber2, phonenumber3, salary, vacationleft) " +
                                    "values ('" + Field[1].getText() + "','" + Field[2].getText() + "','" + Field[3].getText() + "','" + Field[4].getText() + "','" + Field[5].getText() + "','" + Field[6].getText() + "','" + Field[7].getText() + "') ;", "employeeDB");
                            this.setBottomInfo(Backend.runQuery(this, "Select Employees", "none", "select * from employees;", "employeeDB"));
                            dialog.dispose();
                        } else {
                            runFieldValidation(Field);
                        }
                    } else {
                        dialog.dispose();
                    }

                };
                cancelButton.addActionListener(dialogListener);
                insertion.addActionListener(dialogListener);

                break;
            // *******************************************************************************************************************************
            case "delete":

                String employeeDeletion = Backend.runQuery(this, "Change employee values", inputID.getText(), "select * from employees where id = ?;", "EmployeeDB");

                if (employeeDeletion.length() > 0) { // IF employee chosen exist, continue.

                    int selectedOption = JOptionPane.showConfirmDialog(null, "Are you sure you want to DELETE this EMPLOYEE? \n\n" + employeeDeletion + "\n\n", "Deletion of employee!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (selectedOption == JOptionPane.YES_OPTION) {
                        Backend.updateValue(this, inputID.getText(), "delete from employees where id = ?", "employeeDB");
                        this.setBottomInfo(Backend.runQuery(this, "Select Employees", "none", "select * from employees;", "employeeDB"));
                    }
                }
                break;
        }
    }
    /* Return true if all fields have valid input */
    public boolean runFieldCheck(JTextField[] Field) {
        return (Backend.checkInput(Field[1].getText(), "^" + "[A-ZÅÄÖ][a-zåäö\\-\\s\\.]{1,}([A-ZÅÄÖ][a-zåäö\\-\\s\\.]{1,})?([A-ZÅÄÖ][a-zåäö\\-\\s\\.]{1,})?([A-ZÅÄÖ][a-zåäö\\-\\s\\.]{1,})?([A-ZÅÄÖ][a-zåäö\\-\\s\\.]{1,})" + "$") &&
                Backend.checkInput(Field[2].getText(), "^" + "[A-ZÅÄÖ][a-zåäö]+([\\s]{1}[A-ZÅÄÖa-zåäö]+)?([\\s]{1}[A-ZÅÄÖa-zåäö]+)?([\\s]{1}[A-ZÅÄÖa-zåäö]+)?[\\s][0-9]{1,3}([\\-][0-9]{1,3})?([\\s][Ll][Gg][Hh][\\s][0-9]{4})?" + "$") &&
                Backend.checkInput(Field[3].getText(), "^" + "0[0-9]{1,3}[\\s/-][0-9\\s]{5,10}" + "$") &&
                Backend.checkInput(Field[4].getText(), "(^$)|(^0[0-9]{1,3}[\\s/-][0-9\\s]{5,10}$)") &&
                Backend.checkInput(Field[5].getText(), "(^$)|(^0[0-9]{1,3}[\\s/-][0-9\\s]{5,10}$)") &&
                Backend.checkInput(Field[6].getText(), "^" + "\\d{1,8}" + "$") &&
                Backend.checkInput(Field[7].getText(), "^" + "\\d{1,3}" + "$"));
    }
    public void runFieldValidation(JTextField[] Field) {
        /* Checks if the inputed values are in the right format and colors the lines in green or red depending on if they're correct */
        Field[1].setBorder(new LineBorder((Backend.checkInput(Field[1].getText(), "^" + "[A-ZÅÄÖ][a-zåäö\\-\\s\\.]{1,}([A-ZÅÄÖ][a-zåäö\\-\\s\\.]{1,})?([A-ZÅÄÖ][a-zåäö\\-\\s\\.]{1,})?([A-ZÅÄÖ][a-zåäö\\-\\s\\.]{1,})?([A-ZÅÄÖ][a-zåäö\\-\\s\\.]{1,})" + "$") ? Color.GREEN : Color.RED), 2));
        Field[2].setBorder(new LineBorder((Backend.checkInput(Field[2].getText(), "^" + "[A-ZÅÄÖ][a-zåäö]+([\\s]{1}[A-ZÅÄÖa-zåäö]+)?([\\s]{1}[A-ZÅÄÖa-zåäö]+)?([\\s]{1}[A-ZÅÄÖa-zåäö]+)?[\\s][0-9]{1,3}([\\-][0-9]{1,3})?([\\s][Ll][Gg][Hh][\\s][0-9]{4})?" + "$") ? Color.GREEN : Color.RED), 2));
        Field[3].setBorder(new LineBorder((Backend.checkInput(Field[3].getText(), "^" + "0[0-9]{1,3}[\\s/-][0-9\\s]{5,10}" + "$") ? Color.GREEN : Color.RED), 2));
        Field[4].setBorder(new LineBorder((Backend.checkInput(Field[4].getText(), "(^$)|(^0[0-9]{1,3}[\\s/-][0-9\\s]{5,10}$)") ? Color.GREEN : Color.RED), 2));
        Field[5].setBorder(new LineBorder((Backend.checkInput(Field[5].getText(), "(^$)|(^0[0-9]{1,3}[\\s/-][0-9\\s]{5,10}$)") ? Color.GREEN : Color.RED), 2));
        Field[6].setBorder(new LineBorder((Backend.checkInput(Field[6].getText(), "^" + "\\d{1,8}" + "$") ? Color.GREEN : Color.RED), 2));
        Field[7].setBorder(new LineBorder((Backend.checkInput(Field[7].getText(), "^" + "\\d{1,3}" + "$") ? Color.GREEN : Color.RED), 2));
    }
}

