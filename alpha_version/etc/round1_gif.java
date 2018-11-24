import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTree;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TestWB extends JFrame {

   private JPanel contentPane;
   private JTextField roomNum;

   
     Launch the application.
    
   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               TestWB frame = new TestWB();
               frame.setVisible(true);
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      });
   }

   
     Create the frame.
    
   public TestWB() {
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBounds(450, 10, 1020, 1020);
      contentPane = new JPanel();
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      setContentPane(contentPane);
      contentPane.setLayout(null);
      
      JButton creation = new JButton();
      creation.setIcon(new ImageIcon(CUsersuCD5CuC778uC120eclipse-workspaceTestWindowBuildersrcimagemaking.png));
      creation.setBounds(216, 408, 266, 125);
      creation.setBorderPainted(false); 
      creation.setFocusPainted(false); 
      creation.setContentAreaFilled(false);
      contentPane.add(creation);
      
      JButton participation = new JButton();
      participation.setIcon(new ImageIcon(CUsersuCD5CuC778uC120eclipse-workspaceTestWindowBuildersrcimagefinding.png));
      participation.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
         }
      });
      participation.setBounds(216, 586, 266, 125);
      participation.setBorderPainted(false); 
      participation.setFocusPainted(false); 
      participation.setContentAreaFilled(false);
      contentPane.add(participation);
      
      JComboBox numPerson = new JComboBox();
      numPerson.setForeground(Color.DARK_GRAY);
      numPerson.setBackground(Color.WHITE);
      numPerson.setFont(new Font(a´ëÇÑ´Ì¿ì½ºM, Font.BOLD, 40));
      numPerson.setMaximumRowCount(3);
      numPerson.setModel(new DefaultComboBoxModel(new String[] {2uC778uC6A9, 3uC778uC6A9, 4uC778uC6A9}));
      numPerson.setBounds(554, 434, 165, 68);
      contentPane.add(numPerson);
      
      roomNum = new JTextField();
      roomNum.setForeground(Color.DARK_GRAY);
      roomNum.setFont(new Font(a´ëÇÑ´Ì¿ì½ºM, Font.PLAIN, 40));
      roomNum.setText(1234);
      roomNum.setBounds(554, 618, 165, 68);
      contentPane.add(roomNum);
      roomNum.setColumns(10);
      
      JLabel qMark = new JLabel();
      qMark.setIcon(new ImageIcon(CUsersuCD5CuC778uC120eclipse-workspaceTestWindowBuildersrcimagequestion.gif));
      qMark.setBounds(702, 61, 66, 94);
      contentPane.add(qMark);
      
      JLabel backGround = new JLabel();
      backGround.setIcon(new ImageIcon(CUsersuCD5CuC778uC120eclipse-workspaceTestWindowBuildersrcimagemain.png));
      backGround.setBounds(0, 0, 1000, 1001);
      contentPane.add(backGround);
   }
}