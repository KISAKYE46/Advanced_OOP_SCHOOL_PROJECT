package schoolManagement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

public class myResults extends JDialog{
    private JPanel panel1;
    private JTextField tfMath;
    private JTextField tfScience;
    private JTextField tfEnglish;
    private JTextField tfAgric;
    private JTextField tfSST;
    private JTextField tfArt;
    private JTable myResultTable;
    private JButton closeButton;
    private JScrollPane SPanel;


    String[] columnNames = {"Subject","Marks"};

    public myResults(JFrame parent) {
        super(parent);
        fetchResults();
        setTitle("Student Results");
        JPanel panel = new JPanel();
        setContentPane(panel1);
        pack();
        //setSize(new Dimension(800, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();

            }
        });
        setVisible(true);
    }

    private void fetchResults() {
        String id = LoginPage.loggedInUser;
        final String DB_URL = "jdbc:mysql://localhost:3306/school";
        final String USERNAME = "root";
        final String PASSWORD = "re@g@n2000";
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM students WHERE Registration_no=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,id);

            ResultSet rs = pst.executeQuery();
            //ResultSetMetaData StData = rs.getMetaData();
            tfMath.setText(rs.getString("math"));
            tfAgric.setText(rs.getString("Agriculture"));
            tfArt.setText(rs.getString("Art"));
            tfEnglish.setText(rs.getString("English"));
            tfSST.setText(rs.getString("SST"));
            tfScience.setText(rs.getString("Science"));


        }catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public static void main(String[] args) {
        myResults m = new myResults(null);
    }


}
