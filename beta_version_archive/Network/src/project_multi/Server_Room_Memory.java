package project_multi;

import java.net.Socket;
import java.util.ArrayList;

class Server_Room_Memory {
	ArrayList<Socket> socketList = new ArrayList<Socket>();
	int maxPerson;
	boolean checkNew = false;

	boolean[] category = new boolean[8];

	int response;

	public int[][] dataSet;
	ArrayList<Integer> remainID = new ArrayList<Integer>();

	int selectL;
	int selectR;

	Integer finnal = null;

	public Server_Room_Memory(int maxPerson) {
		this.maxPerson = maxPerson;
	}

	public void addPerson(Socket newSocket) {
		socketList.add(newSocket);
		if (this.socketList.size() > 1)
			checkNew = true;
	}

	public void printState() {
		System.out.println("maxPerson :" + maxPerson);
		System.out.println("numPerson :" + socketList.size());
	}

	public boolean isFull() {
		if (socketList.size() < maxPerson)
			return false;
		else
			return true;
	}

	public void selecinitialize() {
		selectL = 0;
		selectR = 0;
	}

	public void selectedLeft() {
		selectL++;
	}

	public void selectedRight() {
		selectR++;
	}

	public boolean haveSelectDone() {
		if ((selectL + selectR) < maxPerson)
			return false;
		else
			return true;
	}

}