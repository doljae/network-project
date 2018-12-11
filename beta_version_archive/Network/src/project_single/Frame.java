package project_single;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Frame extends JFrame {
	public JPanel contentPane;
	private final JLabel backGround = new JLabel("");
	private JTextField roomNum;
	public int[][] dataSet;

	public String hostName = "127.0.0.1";
	public int hostPort = 6789;

	public Socket clientSocket;
	public DataOutputStream outToServer;
	public BufferedReader inFromServer;

	public Frame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 10, 1020, 1020); // 창 사이즈 고정 1020,1020
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		change(contentPane, "Round1");
	}

	public void change(JPanel contentPane, String command) {
		ArrayList<String> list = new ArrayList<String>();// to decompose the
															// response
		int point = command.indexOf(" ");// fine the 'space'

		while (point != -1) {// do until there is no 'space'
			list.add(command.substring(0, point));// insert answer of 'first' to
													// 'space' at list
			command = command.substring(point + 1);// delete stored string
			point = command.indexOf(" ");// find new 'space'
		}
		list.add(command);// insert remain(last) string

		// 확인용
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		// 확인용

		if (list.get(0).equalsIgnoreCase("First")) {
			contentPane.removeAll();
			First(contentPane);
			revalidate();
			repaint();
		} else if (list.get(0).equalsIgnoreCase("Creation")) {
			contentPane.removeAll();
			Creation(contentPane, Integer.parseInt(list.get(1)));
			revalidate();
			repaint();
			// new Client_Thread(this, outToServer, inFromServer,
			// Integer.parseInt(list.get(1))).start();
		} else if (list.get(0).equalsIgnoreCase("Wait")) {
			contentPane.removeAll();
			Wait(contentPane);
			revalidate();
			repaint();
		} else if (list.get(0).equalsIgnoreCase("Round1")) {
			contentPane.removeAll();
			Round1(contentPane);
			revalidate();
			repaint();
		} else if (list.get(0).equalsIgnoreCase("Round2")) {
			contentPane.removeAll();
			if (list.size() > 1) {
				String fileName = "src\\database.txt";
				System.out.println("File Opening");
				Scanner inputStream = null;// where the read string will store
				try {
					inputStream = new Scanner(new File(fileName));// try to open
																	// file
				} catch (FileNotFoundException e) {// file is not opened
					System.out.println("File Opening Error");
					System.exit(0);
				}
				System.out.println("File Opening Success");

				System.out.println("File Reading");

				int restaurantNum = 44;
				int countData = 0;
				dataSet = new int[restaurantNum][10];// 1 id, 2~9 group, 10
														// valid
				String fileBuffer;
				for (int k = 0; k < restaurantNum; k++) {
					fileBuffer = inputStream.nextLine();
					ArrayList<String> tempList = new ArrayList<String>();
					int tempPoint = fileBuffer.indexOf(" ");

					while (tempPoint != -1) {
						tempList.add(fileBuffer.substring(0, tempPoint));
						// 'space' at list
						fileBuffer = fileBuffer.substring(tempPoint + 1);
						tempPoint = fileBuffer.indexOf(" ");
					}
					tempList.add(fileBuffer);

					dataSet[countData][0] = Integer.parseInt(tempList.get(0));// id
					for (int i = 2; i < 10; i++) {
						dataSet[countData][i - 1] = Integer.parseInt(tempList.get(i));// 나머지
					}
					dataSet[countData][9] = 0;
					countData++;
				}

				// 그룹별 유호 확인
				for (int i = 0; i < 8; i++) {
					if (list.get(i + 1).equalsIgnoreCase("false")) {
						for (int k = 0; k < restaurantNum; k++) {
							if (dataSet[k][i + 1] == 1) {
								dataSet[k][9] = 1;
							}
						}
					}
				}

				inputStream.close();// finishing reading file
				System.out.println("File Reading Success");
			}
			Round2(contentPane);
			revalidate();
			repaint();
		} else if (list.get(0).equalsIgnoreCase("Result")) {
			contentPane.removeAll();
			Result(contentPane, Integer.parseInt(list.get(1)));
			revalidate();
			repaint();
		} else if (list.get(0).equalsIgnoreCase("LastChoice")) {
			contentPane.removeAll();
			LastChoice(contentPane);
			revalidate();
			repaint();
		} else if (list.get(0).equalsIgnoreCase("MiniGame")) {
			contentPane.removeAll();
			MiniGame(contentPane);
			revalidate();
			repaint();
		} else if (list.get(0).equalsIgnoreCase("WaitChoice")) {
			contentPane.removeAll();
			WaitChoice(contentPane);
			revalidate();
			repaint();
		}
	}

	public void First(JPanel contentPane) {

		JComboBox numPerson = new JComboBox();
		numPerson.setForeground(Color.DARK_GRAY);
		numPerson.setBackground(Color.WHITE);
		numPerson.setFont(new Font("a대한늬우스M", Font.BOLD, 40));
		numPerson.setMaximumRowCount(4);
		numPerson.setModel(new DefaultComboBoxModel(
				new String[] { "1\uC778\uC6A9", "2\uC778\uC6A9", "3\uC778\uC6A9", "4\uC778\uC6A9" }));
		numPerson.setBounds(554, 434, 165, 68);
		contentPane.add(numPerson);

		JButton creation = new JButton("");
		creation.setIcon(new ImageIcon("src\\image\\making.png"));
		creation.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				change(contentPane, "Creation " + (numPerson.getSelectedIndex() + 1));
			}
		});
		creation.setBounds(216, 408, 266, 125);
		creation.setBorderPainted(false);
		creation.setFocusPainted(false);
		creation.setContentAreaFilled(false);
		contentPane.add(creation);

		JButton participation = new JButton("");
		participation.setIcon(new ImageIcon("src\\image\\finding.png"));
		participation.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				change(contentPane, "Wait " + (numPerson.getSelectedIndex() + 2));
			}
		});
		participation.setBounds(216, 586, 266, 125);
		participation.setBorderPainted(false);
		participation.setFocusPainted(false);
		participation.setContentAreaFilled(false);
		contentPane.add(participation);

		roomNum = new JTextField();
		roomNum.setForeground(Color.DARK_GRAY);
		roomNum.setFont(new Font("a대한늬우스M", Font.PLAIN, 40));
		roomNum.setText("1234");
		roomNum.setBounds(554, 618, 165, 68);
		contentPane.add(roomNum);
		roomNum.setColumns(10);

		JLabel qMark = new JLabel("");
		qMark.setIcon(new ImageIcon("src\\image\\question.gif"));
		qMark.setBounds(702, 61, 66, 94);
		contentPane.add(qMark);

		JLabel backGround = new JLabel("");
		backGround.setIcon(new ImageIcon("src\\image\\main.png"));
		backGround.setBounds(0, 0, 1000, 1001);
		contentPane.add(backGround);
	}

	public void Creation(JPanel contentPane, int maxPerson) {
		JLabel roomNum = new JLabel("방번호는 이거야~");
		roomNum.setHorizontalAlignment(SwingConstants.CENTER);
		roomNum.setFont(new Font("굴림", Font.BOLD, 30));
		roomNum.setBounds(470, 448, 291, 108);
		contentPane.add(roomNum);

		JButton start = new JButton("");
		start.setIcon(new ImageIcon("src\\image\\start.png"));
		start.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				change(contentPane, "Round1 ");
			}
		});
		start.setBounds(400, 849, 199, 88);
		start.setBorderPainted(false);
		start.setFocusPainted(false);
		start.setContentAreaFilled(false);
		contentPane.add(start);

		JLabel current = new JLabel("1");
		current.setHorizontalAlignment(SwingConstants.CENTER);
		current.setFont(new Font("돋움", Font.PLAIN, 80));
		current.setBounds(386, 616, 102, 108);
		contentPane.add(current);

		JLabel maximum = new JLabel(Integer.toString(maxPerson));
		maximum.setHorizontalAlignment(SwingConstants.CENTER);
		maximum.setFont(new Font("돋움", Font.PLAIN, 80));
		maximum.setBounds(517, 631, 102, 108);
		contentPane.add(maximum);

		JLabel backGround = new JLabel("");
		backGround.setIcon(new ImageIcon("src\\image\\roomLeader.png"));
		backGround.setBounds(0, 0, 1000, 1001);
		contentPane.add(backGround);
	}

	public void Wait(JPanel contentPane) {
		JLabel loadingImg = new JLabel("");
		loadingImg.setHorizontalAlignment(SwingConstants.CENTER);
		loadingImg.setIcon(new ImageIcon("src\\image\\loading.gif"));
		loadingImg.setBounds(293, 491, 375, 333);
		contentPane.add(loadingImg);

		JLabel backGround = new JLabel("");
		backGround.setIcon(new ImageIcon("src\\image\\wait.png"));
		backGround.setBounds(0, 0, 1000, 1001);
		contentPane.add(backGround);
	}

	public void Round1(JPanel contentPane) {
		JPanel panel = new JPanel();
		panel.setBounds(0, 322, 1000, 350);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(2, 4, 0, 0));

		JToggleButton kr = new JToggleButton("");
		kr.setSelectedIcon(new ImageIcon("src\\image\\no.png"));
		kr.setIcon(new ImageIcon("src\\image\\korea.png"));
		kr.setBorderPainted(false);
		kr.setFocusPainted(false);
		kr.setContentAreaFilled(false);
		panel.add(kr);

		JToggleButton cn = new JToggleButton("");
		cn.setSelectedIcon(new ImageIcon("src\\image\\no.png"));
		cn.setIcon(new ImageIcon("src\\image\\china.png"));
		cn.setBorderPainted(false);
		cn.setFocusPainted(false);
		cn.setContentAreaFilled(false);
		panel.add(cn);

		JToggleButton jp = new JToggleButton("");
		jp.setSelectedIcon(new ImageIcon("src\\image\\no.png"));
		jp.setIcon(new ImageIcon("src\\image\\japan.png"));
		jp.setBorderPainted(false);
		jp.setFocusPainted(false);
		jp.setContentAreaFilled(false);
		panel.add(jp);

		JToggleButton it = new JToggleButton("");
		it.setSelectedIcon(new ImageIcon("src\\image\\no.png"));
		it.setIcon(new ImageIcon("src\\image\\italy.png"));
		it.setBorderPainted(false);
		it.setFocusPainted(false);
		it.setContentAreaFilled(false);
		panel.add(it);

		JToggleButton sn = new JToggleButton("");
		sn.setSelectedIcon(new ImageIcon("src\\image\\no.png"));
		sn.setIcon(new ImageIcon("src\\image\\snack.png"));
		sn.setBorderPainted(false);
		sn.setFocusPainted(false);
		sn.setContentAreaFilled(false);
		panel.add(sn);

		JToggleButton gb = new JToggleButton("");
		gb.setSelectedIcon(new ImageIcon("src\\image\\no.png"));
		gb.setIcon(new ImageIcon("src\\image\\global.png"));
		gb.setBorderPainted(false);
		gb.setFocusPainted(false);
		gb.setContentAreaFilled(false);
		panel.add(gb);

		JToggleButton ch = new JToggleButton("");
		ch.setSelectedIcon(new ImageIcon("src\\image\\no.png"));
		ch.setIcon(new ImageIcon("src\\image\\chicken.png"));
		ch.setBorderPainted(false);
		ch.setFocusPainted(false);
		ch.setContentAreaFilled(false);
		panel.add(ch);

		JToggleButton ff = new JToggleButton("");
		ff.setSelectedIcon(new ImageIcon("src\\image\\no.png"));
		ff.setIcon(new ImageIcon("src\\image\\fastfood.png"));
		ff.setBorderPainted(false);
		ff.setFocusPainted(false);
		ff.setContentAreaFilled(false);
		panel.add(ff);
		backGround.setHorizontalAlignment(SwingConstants.CENTER);

		JButton btn_complete = new JButton(""); // 완료 버튼
		btn_complete.setIcon(new ImageIcon("src\\image\\btn_r1.png"));
		btn_complete.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String combine = "" + kr.isSelected() + " " + cn.isSelected() + " " + jp.isSelected() + " "
						+ it.isSelected() + " " + sn.isSelected() + " " + gb.isSelected() + " " + ch.isSelected() + " "
						+ ff.isSelected();
				change(contentPane, "Round2 " + combine);
			}
		});
		btn_complete.setBounds(400, 849, 194, 95);
		btn_complete.setBorderPainted(false);
		btn_complete.setFocusPainted(false);
		btn_complete.setContentAreaFilled(false);
		contentPane.add(btn_complete);

		backGround.setIcon(new ImageIcon("src\\image\\round1.png"));
		backGround.setBounds(0, -8, 1000, 1001);
		contentPane.add(backGround);
	}

	public void Round2(JPanel contentPane) {
		System.out.println("Round2 안에서");
		for (int i = 0; i < 44; i++) {
			for (int k = 0; k < 10; k++) {
				System.out.print(dataSet[i][k] + " ");
			}
			System.out.println("");
		}

		int point1 = 0;
		int point2 = 0;
		for (int i = 0; i < 44; i++) {
			if (dataSet[i][9] == 1) {
				point1 = i + 1;
				break;
			}
		}
		for (int i = point1; i < 44; i++) {
			if (dataSet[i][9] == 1) {
				point2 = i + 1;
				break;
			}
			if (i == 43)
				point2 = -1;
		}
		if (point2 == -1) {
			change(contentPane, "Result " + point1);
		}

		// ????
		int point1_1 = point1;
		int point2_1 = point2;

		JButton r1 = new JButton(""); // 왼쪽 버튼
		r1.setVerticalAlignment(SwingConstants.TOP);
		r1.setIcon(new ImageIcon("src\\image\\rest\\rest" + point1 + ".png"));
		System.out.println("src\\image\\rest\\rest" + point1 + ".png");
		r1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.out.println(point1_1);
				dataSet[point2_1 - 1][9] = 0;
				change(contentPane, "Round2");
			}
		});
		r1.setBounds(0, 304, 449, 493);
		r1.setBorderPainted(false);
		r1.setFocusPainted(false);
		r1.setContentAreaFilled(false);
		contentPane.add(r1);

		JButton r2 = new JButton(""); // 오른 쪽 버튼
		r2.setIcon(new ImageIcon("src\\image\\rest\\rest" + point2 + ".png"));
		System.out.println("src\\image\\rest\\rest" + point2 + ".png");
		r2.setVerticalAlignment(SwingConstants.TOP);
		r2.setFocusPainted(false);
		r2.setContentAreaFilled(false);
		r2.setBorderPainted(false);
		r2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				dataSet[point1_1 - 1][9] = 0;
				change(contentPane, "Round2");
			}
		});
		r2.setBounds(551, 304, 449, 493);
		contentPane.add(r2);

		JLabel timer = new JLabel("");
		timer.setIcon(new ImageIcon("src\\image\\timer.gif"));
		timer.setBounds(458, 799, 100, 105);
		contentPane.add(timer);

		JLabel backGround = new JLabel("");
		backGround.setHorizontalAlignment(SwingConstants.CENTER);
		backGround.setIcon(new ImageIcon("src\\image\\round2.png"));
		backGround.setBounds(0, -8, 1000, 1001);
		contentPane.add(backGround);
	}

	public void Result(JPanel contentPane, int theResult) {
		JButton exit = new JButton("");
		exit.setSelectedIcon(new ImageIcon("src\\image\\btn_end_click.png"));
		exit.setIcon(new ImageIcon("src\\image\\btn_end.png"));
		exit.setBounds(423, 849, 160, 93);
		exit.setBorderPainted(false);
		exit.setFocusPainted(false);
		exit.setContentAreaFilled(false);
		contentPane.add(exit);

		JLabel result = new JLabel("");
		result.setIcon(new ImageIcon("src\\image\\rest\\rest" + theResult + ".png"));
		result.setHorizontalAlignment(SwingConstants.CENTER);
		result.setBounds(276, 235, 449, 559);
		contentPane.add(result);

		JLabel backGround = new JLabel("");
		backGround.setIcon(new ImageIcon("src\\image\\result.png"));
		backGround.setBounds(0, 0, 1000, 1001);
		contentPane.add(backGround);
	}

	// 여기부터 작업 필요
	public void LastChoice(JPanel contentPane) {
		JButton r1 = new JButton(""); // 왼쪽 버튼
		r1.setVerticalAlignment(SwingConstants.TOP);
		r1.setIcon(new ImageIcon("src\\rest\\rest_35.png"));
		r1.setBounds(0, 304, 449, 493);
		r1.setBorderPainted(false);
		r1.setFocusPainted(false);
		r1.setContentAreaFilled(false);
		contentPane.add(r1);

		JButton r2 = new JButton(""); // 오른 쪽 버튼
		r2.setIcon(new ImageIcon("src\\image\\rest_34.png"));
		r2.setVerticalAlignment(SwingConstants.TOP);
		r2.setFocusPainted(false);
		r2.setContentAreaFilled(false);
		r2.setBorderPainted(false);
		r2.setBounds(551, 304, 449, 493);
		contentPane.add(r2);

		JLabel timer = new JLabel("3!"); // 타이머
		timer.setFont(new Font("맑은 고딕", Font.BOLD, 84));
		timer.setBounds(458, 799, 76, 105);
		contentPane.add(timer);

		JLabel backGround = new JLabel("");
		backGround.setHorizontalAlignment(SwingConstants.CENTER);
		backGround.setIcon(new ImageIcon("src\\image\\finalRound.png"));
		backGround.setBounds(0, -8, 1000, 1001);
		contentPane.add(backGround);
	}

	public void MiniGame(JPanel contentPane) {
		String answer = "SOFTWARE";
		String temp = answer;

		HashMap<Integer, String> sequence = new HashMap<Integer, String>();
		for (int i = 0; i < answer.length(); i++) {
			int num = new Random().nextInt(temp.length());
			sequence.put(i, "" + temp.charAt(num));
			temp = temp.substring(0, num) + temp.substring(num + 1, temp.length());
		}
		
		for (int i = 0; i < answer.length(); i++) {
			System.out.println(sequence.get(i));
		}

		JLabel output = new JLabel("");
		output.setForeground(new Color(227, 152, 153));
		output.setFont(new Font("a대한늬우스M", Font.PLAIN, 62));
		output.setBounds(340, 380, 320, 73);
		contentPane.add(output);

		JLabel errorMssg = new JLabel(""); // 버튼을 순서대로 입력하지 않았을 때 뜨는 에러메세지 이미지를 보임
		errorMssg.setHorizontalAlignment(SwingConstants.CENTER);
		errorMssg.setBounds(257, 726, 482, 61);
		errorMssg.setVisible(false); // 아직은 안보이게 설정
		contentPane.add(errorMssg);
		errorMssg.setIcon(new ImageIcon("src\\image\\error.png"));

		JPanel choice = new JPanel(); // 버튼 표를 묶는 패널(그리드 레이아웃)
		choice.setBackground(new Color(242, 233, 227));
		choice.setBounds(0, 508, 1000, 159);
		contentPane.add(choice);
		choice.setLayout(new GridLayout(1, 0, 0, 0));

		/* btn1 부터 btn8은 각각의 칸을 의미함 */
		JButton btn1 = new JButton("");
		btn1.setIcon(new ImageIcon("src\\image\\mini\\" + sequence.get(0) + ".png"));
		btn1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				output.setText(output.getText() + sequence.get(0));

				if (output.getText().length() == 8) {
					if (output.getText().equalsIgnoreCase(answer))
						change(contentPane, "LastChoice");
					else {
						output.setText("");
						errorMssg.setVisible(true);
					}
				}
			}
		});
		btn1.setBorderPainted(false); // 버튼의 이미지만 보이기위함
		btn1.setFocusPainted(false);
		btn1.setContentAreaFilled(false);
		choice.add(btn1);

		JButton btn2 = new JButton("");
		btn2.setIcon(new ImageIcon("src\\image\\mini\\" + sequence.get(1) + ".png"));
		btn2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				output.setText(output.getText() + sequence.get(1));

				if (output.getText().length() == 8) {
					if (output.getText().equalsIgnoreCase(answer))
						change(contentPane, "LastChoice");
					else {
						output.setText("");
						errorMssg.setVisible(true);
					}
				}
			}
		});
		btn2.setBorderPainted(false);
		btn2.setFocusPainted(false);
		btn2.setContentAreaFilled(false);
		choice.add(btn2);

		JButton btn3 = new JButton("");
		btn3.setIcon(new ImageIcon("src\\image\\mini\\" + sequence.get(2) + ".png"));
		btn3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				output.setText(output.getText() + sequence.get(2));

				if (output.getText().length() == 8) {
					if (output.getText().equalsIgnoreCase(answer))
						change(contentPane, "LastChoice");
					else {
						output.setText("");
						errorMssg.setVisible(true);
					}
				}
			}
		});
		btn3.setBorderPainted(false);
		btn3.setFocusPainted(false);
		btn3.setContentAreaFilled(false);
		choice.add(btn3);

		JButton btn4 = new JButton("");
		btn4.setIcon(new ImageIcon("src\\image\\mini\\" + sequence.get(3) + ".png"));
		btn4.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				output.setText(output.getText() + sequence.get(3));

				if (output.getText().length() == 8) {
					if (output.getText().equalsIgnoreCase(answer))
						change(contentPane, "LastChoice");
					else {
						output.setText("");
						errorMssg.setVisible(true);
					}
				}
			}
		});
		btn4.setBorderPainted(false);
		btn4.setFocusPainted(false);
		btn4.setContentAreaFilled(false);
		choice.add(btn4);

		JButton btn5 = new JButton("");
		btn5.setIcon(new ImageIcon("src\\image\\mini\\" + sequence.get(4) + ".png"));
		btn5.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				output.setText(output.getText() + sequence.get(4));

				if (output.getText().length() == 8) {
					if (output.getText().equalsIgnoreCase(answer))
						change(contentPane, "LastChoice");
					else {
						output.setText("");
						errorMssg.setVisible(true);
					}
				}
			}
		});
		btn5.setBorderPainted(false);
		btn5.setFocusPainted(false);
		btn5.setContentAreaFilled(false);
		choice.add(btn5);

		JButton btn6 = new JButton("");
		btn6.setIcon(new ImageIcon("src\\image\\mini\\" + sequence.get(5) + ".png"));
		btn6.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				output.setText(output.getText() + sequence.get(5));

				if (output.getText().length() == 8) {
					if (output.getText().equalsIgnoreCase(answer))
						change(contentPane, "LastChoice");
					else {
						output.setText("");
						errorMssg.setVisible(true);
					}
				}
			}
		});
		btn6.setBorderPainted(false);
		btn6.setFocusPainted(false);
		btn6.setContentAreaFilled(false);
		choice.add(btn6);

		JButton btn7 = new JButton("");
		btn7.setIcon(new ImageIcon("src\\image\\mini\\" + sequence.get(6) + ".png"));
		btn7.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				output.setText(output.getText() + sequence.get(6));

				if (output.getText().length() == 8) {
					if (output.getText().equalsIgnoreCase(answer))
						change(contentPane, "LastChoice");
					else {
						output.setText("");
						errorMssg.setVisible(true);
					}
				}
			}
		});
		btn7.setBorderPainted(false);
		btn7.setFocusPainted(false);
		btn7.setContentAreaFilled(false);
		choice.add(btn7);

		JButton btn8 = new JButton("");
		btn8.setIcon(new ImageIcon("src\\image\\mini\\" + sequence.get(7) + ".png"));
		btn8.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				output.setText(output.getText() + sequence.get(7));

				if (output.getText().length() == 8) {
					if (output.getText().equalsIgnoreCase(answer))
						change(contentPane, "LastChoice");
					else {
						output.setText("");
						errorMssg.setVisible(true);
					}
				}
			}
		});
		btn8.setBorderPainted(false);
		btn8.setFocusPainted(false);
		btn8.setContentAreaFilled(false);
		choice.add(btn8);

		JButton reset = new JButton(""); // 리셋 버튼
		reset.setVerticalAlignment(SwingConstants.TOP);
		reset.setIcon(new ImageIcon("src\\image\\reset.png"));
		reset.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				output.setText("");
			}
		});
		reset.setBounds(423, 849, 152, 85);
		reset.setBorderPainted(false);
		reset.setFocusPainted(false);
		reset.setContentAreaFilled(false);
		contentPane.add(reset);

		JLabel bakcGround = new JLabel(""); // backGround 이미지
		bakcGround.setIcon(new ImageIcon("src\\image\\minigame.png"));
		bakcGround.setBounds(0, -8, 1000, 1001);
		contentPane.add(bakcGround);
	}

	public void WaitChoice(JPanel contentPane) {
		JLabel loading = new JLabel("");
		loading.setIcon(new ImageIcon("src\\image\\loading.gif"));
		loading.setBounds(362, 551, 273, 229);
		contentPane.add(loading);

		JLabel backGround = new JLabel("");
		backGround.setIcon(new ImageIcon("src\\image\\wait2.png"));
		backGround.setBounds(0, -8, 1000, 1001);
		contentPane.add(backGround);
	}
}
