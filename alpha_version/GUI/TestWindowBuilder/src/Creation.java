import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JFormattedTextField;
import javax.swing.JTextPane;
import java.awt.Font;
import javax.swing.JButton;

public class Creation extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Creation frame = new Creation();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Creation() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 10, 1000, 1001);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel roomNum = new JLabel("방번호는 이거야~");
		roomNum.setFont(new Font("굴림", Font.BOLD, 30));
		roomNum.setBounds(470, 494, 291, 108);
		contentPane.add(roomNum);
		
		JButton start = new JButton("");
		start.setIcon(new ImageIcon("src\\image\\start.png"));
		start.setBounds(393, 808, 199, 88);
		start.setBorderPainted(false); 
		start.setFocusPainted(false); 
		start.setContentAreaFilled(false);
		contentPane.add(start);
		
		JLabel lblm = new JLabel("1/4");
		lblm.setFont(new Font("돋움", Font.PLAIN, 83));
		lblm.setBounds(421, 626, 153, 108);
		contentPane.add(lblm);
		
		JLabel backGround = new JLabel("");
		backGround.setIcon(new ImageIcon("src\\image\\roomLeader.png"));
		backGround.setBounds(0, 0, 1000, 1001);
		contentPane.add(backGround);
	}
}
