import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;

public class Round2 extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Round2 frame = new Round2();
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
	public Round2() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 10, 1000, 1001);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton r1 = new JButton("1");
		r1.setBounds(25, 340, 380, 377);
		contentPane.add(r1);
		
		JButton r2 = new JButton("2");
		r2.setBounds(576, 340, 380, 377);
		contentPane.add(r2);
		
		JLabel timer = new JLabel("3!");
		timer.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 84));
		timer.setBounds(458, 799, 76, 105);
		contentPane.add(timer);
		
		JLabel backGround = new JLabel("");
		backGround.setIcon(new ImageIcon("src\\image\\round2.png"));
		backGround.setBounds(0, -8, 1000, 1001);
		contentPane.add(backGround);
	}
}
