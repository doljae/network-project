package project_multi;

import java.awt.EventQueue;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Client extends JFrame {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client_Frame frame = new Client_Frame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

class Client_Thread_Timer extends Thread {
	Client_Frame frame;

	public Client_Thread_Timer(Client_Frame frame) {
		this.frame = frame;
	}

	public void run() {
		System.out.println("thread started");

		try {
			Thread.sleep(3500);
			frame.outToServer.writeBytes(" " + '\n');

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class Client_Thread_Creat extends Thread {
	Client_Frame frame;
	int maxPerson;

	public Client_Thread_Creat(Client_Frame frame, int maxPerson) {
		this.frame = frame;
		this.maxPerson = maxPerson;
	}

	public void run() {
		System.out.println("thread started");

		try {
			String answer = "1";

			while (Integer.parseInt(answer) < maxPerson) {
				answer = frame.inFromServer.readLine();
				System.out.println("recived message");
				frame.change(frame.contentPane, "Creation " + maxPerson + " " + answer);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("thread is done");
	}
}

class Client_Thread_Join extends Thread {
	Client_Frame frame;

	public Client_Thread_Join(Client_Frame frame) {
		this.frame = frame;
	}

	public void run() {
		System.out.println("thread started");

		try {
			String answer = frame.inFromServer.readLine();
			System.out.println("recived message");

			frame.change(frame.contentPane, "Round1");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class Client_Thread_Match extends Thread {
	Client_Frame frame;

	public Client_Thread_Match(Client_Frame frame) {
		this.frame = frame;
	}

	public void run() {
		System.out.println("thread started");

		try {
			String answer = frame.inFromServer.readLine();
			System.out.println("recived message");

			ArrayList<String> list = new ArrayList<String>();
			int point = answer.indexOf(" ");

			while (point != -1) {
				list.add(answer.substring(0, point));
				answer = answer.substring(point + 1);
				point = answer.indexOf(" ");
			}
			list.add(answer);

			if (list.size() > 1)
				frame.change(frame.contentPane, "Round2 " + list.get(0) + " " + list.get(1));
			else
				frame.change(frame.contentPane, "MiniGame");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class Client_Thread_MiniGame extends Thread {
	Client_Frame frame;

	public Client_Thread_MiniGame(Client_Frame frame) {
		this.frame = frame;
	}

	public void run() {
		System.out.println("thread started");

		try {
			String answer = frame.inFromServer.readLine();
			System.out.println("recived message");

			ArrayList<String> list = new ArrayList<String>();
			int point = answer.indexOf(" ");

			while (point != -1) {
				list.add(answer.substring(0, point));
				answer = answer.substring(point + 1);
				point = answer.indexOf(" ");
			}
			list.add(answer);

			if (list.get(0).equalsIgnoreCase("Winner"))
				frame.change(frame.contentPane, "LastChoice " + list.get(1) + " " + list.get(2));
			else if (list.get(0).equalsIgnoreCase("Loser"))
				frame.change(frame.contentPane, "WaitChoice");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class Client_Thread_Wait extends Thread {
	Client_Frame frame;

	public Client_Thread_Wait(Client_Frame frame) {
		this.frame = frame;
	}

	public void run() {
		System.out.println("thread started");

		try {
			String finalFood = frame.inFromServer.readLine();
			System.out.println("recived message");

			frame.change(frame.contentPane, "Result " + finalFood);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}