package schoolManagement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.Vector;

public class studentSignUpPage extends JDialog {
    private JTextField tfFName;
    private JPasswordField pfPassword;
    private JPasswordField pfConfirmPassword;
    private JButton btnRegister;
    private JTextField tfLName;
    private JTextField tfRegno;
    private JTextField tfGender;
    private JTextField tfAge;
    private JTextField tfClass;
    private JTextField tfContact;
    private JPanel registrationPanel;
    private JButton btnCancel;
    private JTabbedPane tabbedPane1;
    private JPanel tablePanel;
    private JTable studentTable;
    private JButton updateButton;
    private JButton deleteButton;
    private JScrollPane scrollPanel;
    String[] columnNames = {"Reg Number","First Name","Last Name","Gender","Age","Class","Contact"};

    public studentSignUpPage(JFrame parent){
         super(parent);
         initiateTable();
         createTable();
         setTitle("Register student");
         JPanel panel = new JPanel();
         setContentPane(registrationPanel);
         //setMinimumSize(new Dimension(450, 500));
         //pack();
         setSize(new Dimension(800, 500));
         setModal(true);
         setLocationRelativeTo(parent);
         setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                registerUser();
                createTable();
                clearFields();
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Update();
                createTable();
                clearFields();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delete();
                createTable();
                clearFields();
            }
        });
         setVisible(true);


     }

    private void initiateTable() {
        Object[][] data = {};
        studentTable.setModel(new DefaultTableModel( data,columnNames) {
            Class[] types = new Class [] {
                    java.lang.Object.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        studentTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table1MouseClicked(evt);
            }
        });
        scrollPanel.setViewportView(studentTable);
    }

    private void createTable() {
        final String DB_URL = "jdbc:mysql://localhost:3306/school";
        final String USERNAME = "root";
        final String PASSWORD = "re@g@n2000";
        try
        {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM students";
            PreparedStatement pst = conn.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();
            ResultSetMetaData StData = rs.getMetaData();

            int q = StData.getColumnCount();

            DefaultTableModel RecordTable = (DefaultTableModel)studentTable.getModel();
            RecordTable.setRowCount(0);

            while(rs.next()){

                Vector columnData = new Vector();

                for (int i = 1; i <= q; i++) {
                    columnData.add(rs.getString("Registration_no"));
                    columnData.add(rs.getString("First_name"));
                    columnData.add(rs.getString("Last_name"));
                    columnData.add(rs.getString("Gender"));
                    columnData.add(rs.getString("Age"));
                    columnData.add(rs.getString("Class"));
                    columnData.add(rs.getString("Contact"));
                }

                RecordTable.addRow(columnData);

            }

        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }

    private void table1MouseClicked(MouseEvent evt) {
        DefaultTableModel RecordTable = (DefaultTableModel)studentTable.getModel();
        int SelectedRows = studentTable.getSelectedRow();

        tfRegno.setText(RecordTable.getValueAt(SelectedRows, 0).toString());
        tfFName.setText(RecordTable.getValueAt(SelectedRows, 1).toString());
        tfLName.setText(RecordTable.getValueAt(SelectedRows, 2).toString());
        tfGender.setText(RecordTable.getValueAt(SelectedRows, 3).toString());
        tfAge.setText(RecordTable.getValueAt(SelectedRows, 4).toString());
        tfClass.setText(RecordTable.getValueAt(SelectedRows, 5).toString());
        tfContact.setText(RecordTable.getValueAt(SelectedRows, 6).toString());

    }

    private void clearFields(){
        JTextField[] jTextFields = new JTextField[]{
            tfRegno,tfFName,tfLName,tfGender,tfAge,tfClass,tfContact,pfConfirmPassword,pfPassword
        };

        for(JTextField textField:jTextFields){
            if (textField!=null){
                textField.setText("");
            }

        }
    }


    private void Update() {
        String fname = tfFName.getText();
        String lname = tfLName.getText();
        String regno = tfRegno.getText();
        String gender = tfGender.getText();
        String Class = tfClass.getText();
        String Age = tfAge.getText();
        String contact = tfContact.getText();
        String password = String.valueOf(pfPassword.getPassword());
        String confirm = String.valueOf(pfConfirmPassword.getPassword());

        if (fname.isEmpty() || lname.isEmpty() || regno.isEmpty() || gender.isEmpty() || Class.isEmpty() || Age.isEmpty() || contact.isEmpty() || password.isEmpty() || confirm.isEmpty()){
            JOptionPane.showMessageDialog(this,
                    "all fields must be entered",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(!password.equals(confirm)){
            JOptionPane.showMessageDialog(this,
                    "password doesn't match",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        student2 = updateStudentDatabase(fname, lname, regno, gender, Class, Age, contact, password);
        if (student2 != null){
            JOptionPane.showMessageDialog(this,
                    "Student data has been updated",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            //dispose();
        }else {
            JOptionPane.showMessageDialog(this,
                    "Student data not updated",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    public Student student2;
    private Student updateStudentDatabase(String fname, String lname, String regno, String gender, String Class, String Age, String contact, String password){
        Student student2 = null;
        final String DB_URL = "jdbc:mysql://localhost:3306/school";
        final String USERNAME = "root";
        final String PASSWORD = "re@g@n2000";

        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "UPDATE `school`.`students` SET `First_name` = ?, `Last_name` = ?, `Gender` = ?, `Age` = ?, `Class` = ?, `Contact` = ? WHERE (`Registration_no` = ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,fname);
            preparedStatement.setString(2,lname);
            preparedStatement.setString(3, gender);
            preparedStatement.setString(4, Age);
            preparedStatement.setString(5,Class);
            preparedStatement.setString(6,contact);
            preparedStatement.setString(7,regno);

            int addedRows = preparedStatement.executeUpdate();
            if(addedRows > 0){
                student2 = new Student();

            }
            stmt.close();
            conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return student2;
    }


    private void delete() {
        String regno = tfRegno.getText();

        if (regno.isEmpty()){
            JOptionPane.showMessageDialog(this,
                    "you have to select a student",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        student3 = deleteFromDatabase(regno);
        if (student3 != null){
            JOptionPane.showMessageDialog(this,
                    "Student data has been Deleted",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            //dispose();
        }else {
            JOptionPane.showMessageDialog(this,
                    "Student data not Deleted",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    public Student student3;
    private Student deleteFromDatabase(String regno){
        Student student3 = null;
        final String DB_URL = "jdbc:mysql://localhost:3306/school";
        final String USERNAME = "root";
        final String PASSWORD = "re@g@n2000";

        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM `school`.`students` WHERE (`Registration_no` = ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1,regno);

            int deletedRows = preparedStatement.executeUpdate();
            if(deletedRows > 0){
                student3 = new Student();

            }
            stmt.close();
            conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return student3;
    }

    public static void main(String[] args) {
        studentSignUpPage myForm = new studentSignUpPage(null);

    }

    private void registerUser() {
        String fname = tfFName.getText();
        String lname = tfLName.getText();
        String regno = tfRegno.getText();
        String gender = tfGender.getText();
        String Class = tfClass.getText();
        String Age = tfAge.getText();
        String contact = tfContact.getText();
        String password = String.valueOf(pfPassword.getPassword());
        String confirm = String.valueOf(pfConfirmPassword.getPassword());

        if (fname.isEmpty() || lname.isEmpty() || regno.isEmpty() || gender.isEmpty() || Class.isEmpty() || Age.isEmpty() || contact.isEmpty() || password.isEmpty() || confirm.isEmpty()){
            JOptionPane.showMessageDialog(this,
                    "all fields must be entered",
                     "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(!password.equals(confirm)){
            JOptionPane.showMessageDialog(this,
                    "password doesn't match",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        student = addStudentToDatabase(fname, lname, regno, gender, Class, Age, contact, password);
        if (student != null){
            JOptionPane.showMessageDialog(this,
                    "Student has been registered",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            //dispose();
        }else {
            JOptionPane.showMessageDialog(this,
                    "Student not registered",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }

    }
    public Student student;
    private Student addStudentToDatabase(String fname, String lname, String regno, String gender, String Class, String Age, String contact, String password){
        Student student = null;
        final String DB_URL = "jdbc:mysql://localhost:3306/school";
        final String USERNAME = "root";
        final String PASSWORD = "re@g@n2000";

        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO students (Registration_no, First_name, Last_name, Gender, Age, Class, Contact, Password)"+
                    "VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,regno);
            preparedStatement.setString(2,fname);
            preparedStatement.setString(3,lname);
            preparedStatement.setString(4, gender);
            preparedStatement.setString(5, Age);
            preparedStatement.setString(6,Class);
            preparedStatement.setString(7,contact);
            preparedStatement.setString(8,password);

            int addedRows = preparedStatement.executeUpdate();
            if(addedRows > 0){
                student = new Student();
                student.regno = regno;
                student.fname = fname;
                student.lname = lname;
                student.gender = gender;
                student.Age = Age;
                student.Class = Class;
                student.contact = contact;
                student.password = password;
            }
            stmt.close();
            conn.close();
        }catch (SQLIntegrityConstraintViolationException e){
            JOptionPane.showMessageDialog(this,"Student already exists","User Info",JOptionPane.INFORMATION_MESSAGE);
        }catch (Exception e){
            e.printStackTrace();
        }

        return student;
    }




}
