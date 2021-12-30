package schoolManagement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;

public class StudentInformation extends JDialog {
    private JTabbedPane tabbedPane1;
    private JTable table1;
    private JPanel infoPanel;
    private JButton addStudentButton;
    private JButton closeButton;
    private JTextField textField1;
    private JButton searchButton;
    private JComboBox sortCombo;
    private JScrollPane jScrollPanel1;
    private JButton addResultsButton;
    String[] columnNames = {"Reg_Number","First Name","Last Name","Gender","Age","Class","Contact","Math","Science","SSt","English","Art","Agricultre"};

    public StudentInformation(JFrame parent) {
        super(parent);
        initialiseTable();
        createTable();
        setTitle("Register student");
        JPanel panel = new JPanel();
        setContentPane(infoPanel);
        //setMinimumSize(new Dimension(450, 500));
        //pack();
        setSize(new Dimension(800, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        addStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        sortCombo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
            }
        });

        addResultsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                addResultsPage results = new addResultsPage(null);
                results.setVisible(true);

            }
        });
        setVisible(true);
    }

    private void initialiseTable() {
        Object[][] data = {};
        table1.setModel(new DefaultTableModel( data,columnNames) {
            Class[] types = new Class [] {
                    java.lang.Object.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        table1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table1MouseClicked(evt);
            }
        });
        jScrollPanel1.setViewportView(table1);
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

            DefaultTableModel RecordTable = (DefaultTableModel)table1.getModel();
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

    private void table1MouseClicked(MouseEvent evt) {

    }

    public static void main(String[] args) {
        StudentInformation myForm = new StudentInformation(null);
    }
}
