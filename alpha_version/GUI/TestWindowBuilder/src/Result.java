import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Result extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Result frame = new Result();
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
	public Result() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 10, 1000, 1001);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton exit = new JButton("");
		exit.setSelectedIcon(new ImageIcon("src\\image\\btn_end_click.png"));
		exit.setIcon(new ImageIcon("src\\image\\btn_end.png"));
		exit.setBounds(423, 849, 160, 93);
		exit.setBorderPainted(false); 
		exit.setFocusPainted(false); 
		exit.setContentAreaFilled(false);
		contentPane.add(exit);
		
		JLabel result = new JLabel("최종메뉴");
		result.setBounds(276, 304, 457, 386);
		contentPane.add(result);
		
		JLabel backGround = new JLabel("");
		backGround.setIcon(new ImageIcon("src\\image\\result.png"));
		backGround.setBounds(0, 0, 1000, 1001);
		contentPane.add(backGround);
	}
}
