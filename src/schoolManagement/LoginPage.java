package schoolManagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginPage extends JDialog {
    private JTextField tfID;
    private JButton loginButton;
    private JPasswordField pfPassword;
    private JButton createAccountButton;
    private JPanel loginPanel;
    private JButton signUpButton;
    private JTextField tfNewID;
    private JTextField tfNewFName;
    private JTextField tfNewContact;
    private JPasswordField pfNewCreatePassword;
    private JPasswordField pfNewConfirmPassword;
    private JPanel signUpPanel;
    private JTextField tfNewLName;
    private JTextField tfNewGender;
    private JButton cancelButton;
    private JButton CancelButton;
    public static String loggedInUser ="";

    public LoginPage(JFrame parent){
        super(parent);
        JFrame frame = new JFrame("LoginPage");
        frame.setContentPane(loginPanel);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        //frame.pack();
        frame.setSize(new Dimension(450, 500));
        frame.setLocationRelativeTo(parent);


        signUpPanel.setVisible(false);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = tfID.getText();
                String password = String.valueOf(pfPassword.getPassword());

                user = authenticateUser(id, password);

                if (user != null){
                    loggedInUser = id;
                    frame.setVisible(false);
                    studentDashboard board = new studentDashboard(null);
                    board.setVisible(true);

                }else {
                    JOptionPane.showMessageDialog(LoginPage.this,
                            "Invalid ID or Password",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signUpPanel.setVisible(true);


            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                signUpPanel.setVisible(false);
            }
        });
        CancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        frame.setVisible(true);

    }


    public User user;

    private User authenticateUser(String id, String password) {
        User user = null;

        final String DB_URL = "jdbc:mysql://localhost:3306/school";
        final String USERNAME = "root";
        final String PASSWORD = "re@g@n2000";
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql1 = "SELECT * FROM students WHERE Registration_no=? AND Password=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql1);
            preparedStatement.setString(1,id);
            preparedStatement.setString(2,password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.id = resultSet.getString("Registration_no");
                user.password = resultSet.getString("password");
            }
            stmt.close();
            conn.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }
    public static void main(String[] args) {
        LoginPage myForm = new LoginPage( null);

    }
}
