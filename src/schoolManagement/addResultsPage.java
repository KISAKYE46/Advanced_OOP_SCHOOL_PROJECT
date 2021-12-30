package schoolManagement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.Vector;

public class addResultsPage extends JDialog {
    private JPanel panel1;
    private JTable studentTable;
    private JTextField tfRegno;
    private JTextField tfFName;
    private JTextField tfClass;
    private JTextField tfMath;
    private JTextField tfEnglish;
    private JTextField tfSST;
    private JTextField tfScience;
    private JTextField tfArt;
    private JTextField tfAgric;
    private JPanel resultsPanel;
    private JScrollPane scrollPanel;
    private JTextField tfLName;
    private JButton saveButton;
    String[] columnNames = {"Reg Number","First Name","Last Name","Class","Math","Science","SSt","English","Art","Agricultre"};

    public addResultsPage(JFrame parent){
        super(parent);
        initialiseTable();
        createTable();
        setTitle("Register student");
        JPanel panel = new JPanel();
        setContentPane(panel1);
        //setMinimumSize(new Dimension(450, 500));
        pack();
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpdateResults();
            }
        });
        setVisible(true);
    }

    private void UpdateResults() {
        String fname = tfFName.getText();
        String lname = tfLName.getText();
        String regno = tfRegno.getText();
        int math = Integer.parseInt(tfMath.getText());
        String Class = tfClass.getText();
        int sst = Integer.parseInt(tfSST.getText());
        int science = Integer.parseInt(tfScience.getText());
        int english = Integer.parseInt(tfEnglish.getText());
        int art = Integer.parseInt(tfArt.getText());
        int agric = Integer.parseInt(tfAgric.getText());


        if (fname.isEmpty() || lname.isEmpty() || regno.isEmpty() || Class.isEmpty()){
            JOptionPane.showMessageDialog(this,
                    "A field is missing",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        student = addStudentToDatabase(regno, math,sst, science, english, art, agric);
        if (student != null){
            JOptionPane.showMessageDialog(this,
                    "Results have been added successfully",
                    "Success",
                    JOptionPane.ERROR_MESSAGE);
            createTable();
            //dispose();
        }else {
            JOptionPane.showMessageDialog(this,
                    "Failed to register student",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    public Student student;
    private Student addStudentToDatabase(String regno, int math, int sst, int science, int english, int art, int agric){
        Student student = null;
        final String DB_URL = "jdbc:mysql://localhost:3306/school";
        final String USERNAME = "root";
        final String PASSWORD = "re@g@n2000";

        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "UPDATE `school`.`students` SET `math` = ?, `Science` = ?, `SST` = ?, `English` = ?, `Art` = ?, `Agriculture` = ? WHERE (`Registration_no` = ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1,math);
            preparedStatement.setInt(2,science);
            preparedStatement.setInt(3,sst);
            preparedStatement.setInt(4,english);
            preparedStatement.setInt(5,art);
            preparedStatement.setInt(6,agric);
            preparedStatement.setString(7,regno);

            int addedRows = preparedStatement.executeUpdate();
            if(addedRows > 0){
                student = new Student();

            }
            stmt.close();
            conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return student;
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
                    columnData.add(rs.getString("Class"));
                    columnData.add(rs.getString("Math"));
                    columnData.add(rs.getString("Science"));
                    columnData.add(rs.getString("SSt"));
                    columnData.add(rs.getString("English"));
                    columnData.add(rs.getString("Art"));
                    columnData.add(rs.getString("Agriculture"));

                }

                RecordTable.addRow(columnData);

            }

        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }

    private void initialiseTable() {
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

    private void table1MouseClicked(MouseEvent evt) {
        DefaultTableModel RecordTable = (DefaultTableModel)studentTable.getModel();
        int SelectedRows = studentTable.getSelectedRow();

        tfRegno.setText(RecordTable.getValueAt(SelectedRows, 0).toString());
        tfFName.setText(RecordTable.getValueAt(SelectedRows, 1).toString());
        tfLName.setText(RecordTable.getValueAt(SelectedRows, 2).toString());
        tfClass.setText(RecordTable.getValueAt(SelectedRows, 3).toString());
        tfMath.setText(RecordTable.getValueAt(SelectedRows, 4).toString());
        tfScience.setText(RecordTable.getValueAt(SelectedRows, 5).toString());
        tfSST.setText(RecordTable.getValueAt(SelectedRows, 6).toString());
        tfEnglish.setText(RecordTable.getValueAt(SelectedRows, 7).toString());
        tfArt.setText(RecordTable.getValueAt(SelectedRows, 8).toString());
        tfAgric.setText(RecordTable.getValueAt(SelectedRows, 9).toString());
    }

    public static void main(String[] args) {

        addResultsPage myPage = new addResultsPage(null);
    }


}
