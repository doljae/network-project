package project_single;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.swing.JFrame;

public class Test extends JFrame {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame frame = new Frame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

class Client_Thread extends Thread {
	Frame frame;
	DataOutputStream outToServer;
	BufferedReader inFromServer;
	int maxPerson;
	int numPerson;
	public int step = 0;

	public Client_Thread(Frame frame, DataOutputStream outToServer, BufferedReader inFromServer, int maxPerson) {
		this.frame = frame;
		this.outToServer = outToServer;
		this.inFromServer = inFromServer;
		this.maxPerson = maxPerson;
	}

	public void run() {
		System.out.println("thread started");
		numPerson = -1;
		frame.change(frame.contentPane, "Round1");
		while (numPerson != maxPerson) {
			// �߰��� ������ üũ �ʿ�
			/*
			 * if(�߰��� ������) break;
			 */
			try {
				numPerson = Integer.parseInt(inFromServer.readLine());
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (numPerson == maxPerson) {
			frame.change(frame.contentPane, "Round1");
		} else {// �߰��� �������
			frame.change(frame.contentPane, "First");
			// ������ ����
		}
		while (step < 1) {

		}
	}
}