package project_multi;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class Server_Room_Thread extends Thread {
	Server_Room_Memory memory;

	public Server_Room_Thread(int maxPerson) {
		memory = new Server_Room_Memory(maxPerson);
	}

	public void run() {
		System.out.println("Thread start");
		
		try {
			sleep(1000);
		} catch (InterruptedException e1) {
		}
		
		try {
			DataOutputStream outToClient;
			outToClient = new DataOutputStream(memory.socketList.get(0).getOutputStream());
			outToClient.writeBytes("" + (new Random().nextInt(8999) + 1000) + '\n');
		} catch (IOException e) {
			e.printStackTrace();
		}

		// ���� - Round1
		while (memory.isFull() == false) {// ��� ����� �����Ҷ����� �ݺ�
			// wait
			if (memory.checkNew == true) {// ���ο� ����� �������� ���
				DataOutputStream outToClient;
				try {
					outToClient = new DataOutputStream(memory.socketList.get(0).getOutputStream());
					outToClient.writeBytes("" + memory.socketList.size() + '\n');
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				sleep(1000);
			} catch (InterruptedException e1) {
			}
			System.out.println("waiting");
		}

		for (int i = 0; i < 1; i++) {// �������κ��� ������ ���� ��ȣ�� ����
			DataOutputStream outToClient;
			BufferedReader inFromClient;
			try {
				outToClient = new DataOutputStream(memory.socketList.get(0).getOutputStream());
				outToClient.writeBytes("" + memory.socketList.size() + '\n');

				inFromClient = new BufferedReader(new InputStreamReader(memory.socketList.get(0).getInputStream()));
				String receive = inFromClient.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println("Sending response");
		for (int i = 1; i < memory.maxPerson; i++) {// ��������� Round1�� �Ѿ��� ����
			System.out.println(i);
			try {
				DataOutputStream outToClient = new DataOutputStream(memory.socketList.get(i).getOutputStream());
				outToClient.writeBytes("" + memory.maxPerson + '\n');
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Round1 - Round2
		memory.response = 0;

		for (int i = 0; i < memory.maxPerson; i++) {// ���� �����带 �����ڸ�ŭ ����
			System.out.println("make get thread" + i);
			new Server_Room_getCategory(memory, i).start();
		}

		while (memory.response < memory.socketList.size()) {// ��� ������ ���Ź��������� �ݺ�
			// wait
			try {
				sleep(1000);
			} catch (InterruptedException e1) {
			}
			System.out.println("waiting");
		}

		try {
			sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// �����ͺ��̽� �б�
		String fileName = "src\\database.txt";
		System.out.println("File Opening");
		Scanner inputStream = null;
		try {
			inputStream = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			System.out.println("File Opening Error");
			System.exit(0);
		}
		System.out.println("File Opening Success");

		System.out.println("File Reading");

		int restaurantNum = 44;
		memory.dataSet = new int[restaurantNum][10];// 1 id, 2~9 group, 10 valid

		String fileBuffer;
		for (int k = 0; k < restaurantNum; k++) {
			fileBuffer = inputStream.nextLine();
			ArrayList<String> tempList = new ArrayList<String>();
			int tempPoint = fileBuffer.indexOf(" ");

			while (tempPoint != -1) {
				tempList.add(fileBuffer.substring(0, tempPoint));
				fileBuffer = fileBuffer.substring(tempPoint + 1);
				tempPoint = fileBuffer.indexOf(" ");
			}
			tempList.add(fileBuffer);

			memory.dataSet[k][0] = Integer.parseInt(tempList.get(0));// id
			for (int i = 2; i < 10; i++) {
				memory.dataSet[k][i - 1] = Integer.parseInt(tempList.get(i));// ������
			}
			memory.dataSet[k][9] = 0;
		}
		inputStream.close();// finishing reading file
		System.out.println("File Reading Success");

		// �׷캰 ��ȣ Ȯ��
		for (int i = 0; i < 8; i++) {
			if (memory.category[i] == false) {
				for (int k = 0; k < restaurantNum; k++) {
					if (memory.dataSet[k][i + 1] == 1) {
						memory.dataSet[k][9] = 1;
					}
				}
			}
		}
		// ��ȿ�� ���� �߰�
		for (int i = 0; i < restaurantNum; i++) {
			if (memory.dataSet[i][9] == 1)
				memory.remainID.add(memory.dataSet[i][0]);
		}

		while (memory.remainID.size() > 2) {// ������ ������ �̴ϰ����� �ؾ��Ҹ�ŭ ���������� �ݺ�
			for (int i = 0; i < memory.socketList.size(); i++) {// ��� ����鿡�� ù��°��
																// �ι�° ������ ����
				System.out.println(i);
				try {
					DataOutputStream outToClient = new DataOutputStream(memory.socketList.get(i).getOutputStream());
					outToClient.writeBytes("" + memory.remainID.get(0) + " " + memory.remainID.get(1) + '\n');
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			memory.response = 0;
			memory.selectL = 0;
			memory.selectR = 0;

			for (int i = 0; i < memory.maxPerson; i++) {// ������ ����ŭ ���� ������ ����
				System.out.println("make get thread" + i);
				new Server_Room_getWinner(memory, i).start();
			}

			while (memory.response < memory.socketList.size()) {// ��� ����� ������ ����
																// �� ���� ���
				// wait
				try {
					sleep(1000);
				} catch (InterruptedException e1) {
				}
				System.out.println("waiting");
			}

			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (memory.selectL > memory.selectR)// ���� ������ �� �������
				memory.remainID.remove(1);// ������ ����
			else if (memory.selectL < memory.selectR)// �������� �� �������
				memory.remainID.remove(0);// ���� ����
			else// ������ ������ ���
				memory.remainID.remove(new Random().nextInt(2));// ������ �Ѱ��� ����
		}

		// ��� �����ڿ��� ������ ���� �����Ѵٴ� �ȳ� �ʿ�
		for (int i = 0; i < memory.socketList.size(); i++) {// ��� ����鿡�� ù��°�� �ι�°
															// ������ ����
			System.out.println(i);
			try {
				DataOutputStream outToClient = new DataOutputStream(memory.socketList.get(i).getOutputStream());
				outToClient.writeBytes("Ready" + '\n');
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		memory.response = 0;
		Server_Room_getFinner[] temp = new Server_Room_getFinner[memory.maxPerson];// �����带
																					// ����
																					// ���
		for (int i = 0; i < memory.maxPerson; i++) {// ������ ����ŭ ������ �̴ϰ��� ���� ������
													// ����
			temp[i] = new Server_Room_getFinner(memory, i);
		}
		for (int i = 0; i < memory.maxPerson; i++) {// ������ ����ŭ ������ �̴ϰ��� ���� ������
													// ����
			temp[i].start();
		}

		while (memory.finnal == null) {// ���� �¸��ڰ� ������������ ���
			// wait
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("waiting");
		}

		try {
			for (int i = 0; i < memory.socketList.size(); i++) {// ��� �������� ����ŭ
				if (memory.finnal == i) {// �ش� �����ڰ� �����ϰ��
					DataOutputStream outToClient = new DataOutputStream(memory.socketList.get(i).getOutputStream());
					outToClient.writeBytes("Winner " + memory.remainID.get(0) + " " + memory.remainID.get(1) + '\n');
				} else {// �ش� �����ڰ� �����ΰ��
					DataOutputStream outToClient = new DataOutputStream(memory.socketList.get(i).getOutputStream());
					outToClient.writeBytes("Loser" + '\n');
				}
				if (temp[i].isAlive())// ���� ���� �����尡 ����������
					temp[i].interrupt();// �ش� ������ ��������
			}
			sleep(1000);
		} catch (InterruptedException e1) {
		} catch (IOException e) {
			e.printStackTrace();
		}

		// ���ڷκ��� ������ ���� ����
		BufferedReader inFromClient;
		String finalFood = null;
		try {
			inFromClient = new BufferedReader(
					new InputStreamReader(memory.socketList.get(memory.finnal).getInputStream()));
			finalFood = inFromClient.readLine();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.out.println("the Final is " + finalFood);

		// ���� ������ ���ڸ� ������ ��� ����鿡�� ����
		for (int i = 0; i < memory.socketList.size(); i++) {
			System.out.println(i);
			if (memory.finnal != i) {
				try {
					DataOutputStream outToClient = new DataOutputStream(memory.socketList.get(i).getOutputStream());
					outToClient.writeBytes("" + finalFood + '\n');
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		for (int i = 0; i < memory.socketList.size(); i++) {// �������� ��� ���� ����
			try {
				memory.socketList.get(i).close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void sleep(int time) throws InterruptedException {
		Thread.sleep(time);
	}
}

class Server_Room_getCategory extends Thread {
	Server_Room_Memory memory;
	int clientNum;

	public Server_Room_getCategory(Server_Room_Memory memory, int clientNum) {
		this.memory = memory;
		this.clientNum = clientNum;
	}

	public void run() {
		try {
			BufferedReader inFromClient = new BufferedReader(
					new InputStreamReader(memory.socketList.get(clientNum).getInputStream()));

			String receive = inFromClient.readLine();

			ArrayList<String> list = new ArrayList<String>();
			int point = receive.indexOf(" ");

			while (point != -1) {
				list.add(receive.substring(0, point));
				receive = receive.substring(point + 1);
				point = receive.indexOf(" ");
			}
			list.add(receive);

			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i));
			}

			for (int i = 0; i < 8; i++)
				if (list.get(i).equalsIgnoreCase("true"))
					memory.category[i] = true;

			memory.response++;
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

class Server_Room_getWinner extends Thread {
	Server_Room_Memory memory;
	int clientNum;

	public Server_Room_getWinner(Server_Room_Memory memory, int clientNum) {
		this.memory = memory;
		this.clientNum = clientNum;
	}

	public void run() {
		try {
			BufferedReader inFromClient = new BufferedReader(
					new InputStreamReader(memory.socketList.get(clientNum).getInputStream()));

			String receive = inFromClient.readLine();

			if (receive.equalsIgnoreCase("L"))
				memory.selectL++;
			else if (receive.equalsIgnoreCase("R"))
				memory.selectR++;

			memory.response++;
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

class Server_Room_getFinner extends Thread {
	Server_Room_Memory memory;
	int clientNum;

	public Server_Room_getFinner(Server_Room_Memory memory, int clientNum) {
		this.memory = memory;
		this.clientNum = clientNum;
	}

	public void run() {
		try {
			System.out.println("made finnal reciver");
			BufferedReader inFromClient = new BufferedReader(
					new InputStreamReader(memory.socketList.get(clientNum).getInputStream()));

			String receive = inFromClient.readLine();
			System.out.println("I got Winner!");

			if (memory.response == 0) {
				memory.response++;
				memory.finnal = clientNum;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}