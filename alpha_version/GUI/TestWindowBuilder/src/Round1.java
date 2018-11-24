import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.JSeparator;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;

public class Round1 extends JFrame {

	private JPanel contentPane;
	private final JLabel backGround = new JLabel("");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Round1 frame = new Round1();
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
	public Round1() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 0, 1000, 1001);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btn_complete = new JButton("");
		btn_complete.setIcon(new ImageIcon("src\\image\\btn_r1.png"));
		btn_complete.setBounds(394, 825, 194, 95);
		btn_complete.setBorderPainted(false); 
		btn_complete.setFocusPainted(false); 
		btn_complete.setContentAreaFilled(false);
		contentPane.add(btn_complete);
		
		JToggleButton kr = new JToggleButton("");
		kr.setSelectedIcon(new ImageIcon("src\\image\\no.png"));
		kr.setIcon(new ImageIcon("src\\image\\korea.png"));
		kr.setBounds(0, 363, 246, 147);
		contentPane.add(kr);
		
		JToggleButton cn = new JToggleButton("");
		cn.setSelectedIcon(new ImageIcon("src\\image\\no.png"));
		cn.setIcon(new ImageIcon("src\\image\\china.png"));
		cn.setBounds(245, 363, 246, 147);
		contentPane.add(cn);
		
		JToggleButton jp = new JToggleButton("");
		jp.setSelectedIcon(new ImageIcon("src\\image\\no.png"));
		jp.setIcon(new ImageIcon("src\\image\\japan.png"));
		jp.setBounds(490, 363, 246, 147);
		contentPane.add(jp);
		
		JToggleButton it = new JToggleButton("");
		it.setSelectedIcon(new ImageIcon("src\\image\\no.png"));
		it.setIcon(new ImageIcon("src\\image\\italy.png"));
		it.setBounds(736, 363, 246, 147);
		contentPane.add(it);
		
		JToggleButton sn = new JToggleButton("");
		sn.setSelectedIcon(new ImageIcon("src\\image\\no.png"));
		sn.setIcon(new ImageIcon("src\\image\\snack.png"));
		sn.setBounds(0, 509, 246, 147);
		contentPane.add(sn);
		
		JToggleButton gb = new JToggleButton("");
		gb.setSelectedIcon(new ImageIcon("src\\image\\no.png"));
		gb.setIcon(new ImageIcon("src\\image\\global.png"));
		gb.setBounds(245, 509, 246, 147);
		contentPane.add(gb);
		
		JToggleButton cp = new JToggleButton("");
		cp.setSelectedIcon(new ImageIcon("src\\image\\no.png"));
		cp.setIcon(new ImageIcon("src\\image\\chicken.png"));
		cp.setBounds(490, 509, 246, 147);
		contentPane.add(cp);
		
		JToggleButton ff = new JToggleButton("");
		ff.setSelectedIcon(new ImageIcon("src\\image\\no.png"));
		ff.setIcon(new ImageIcon("src\\image\\fastfood.png"));
		ff.setBounds(736, 509, 246, 147);
		ff.setBorder(BorderFactory.createEmptyBorder());
		contentPane.add(ff);
		backGround.setHorizontalAlignment(SwingConstants.CENTER);
		
		backGround.setIcon(new ImageIcon("src\\image\\round1.png"));
		backGround.setBounds(0, -8, 1000, 1001);
		contentPane.add(backGround);
	}
}
