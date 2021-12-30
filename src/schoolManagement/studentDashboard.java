package schoolManagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class studentDashboard extends JDialog{
    private JPanel panel1;
    private JButton viewMyResultsButton;
    private JButton viewTimetableButton;
    private JButton accountInfoButton;
    private JPanel dashBoardPanel;

    public studentDashboard(JFrame parent) {
        super(parent);
        setTitle("Register student");
        JPanel panel = new JPanel();
        setContentPane(panel);
        //pack();
        setSize(new Dimension(800, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        viewMyResultsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                myResults rsts = new myResults(null);
                rsts.setVisible(true);

            }
        });
        viewTimetableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        accountInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        studentDashboard d = new studentDashboard(null);
    }
}
