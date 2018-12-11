package project_multi;

import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class Server {
	public static void main(String[] args) throws Exception {
		System.out.println("Server Started");
		Server_Room_Thread room = null;

		ServerSocket welcomeSocket = new ServerSocket(6789);

		while (true) {
			System.out.println("ready for another user");
			if (room != null)
				room.memory.printState();
			else
				System.out.println("it is null");
			Socket connectionSocket = welcomeSocket.accept();
			System.out.println("Found User");

			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			// DataOutputStream outToClient = new
			// DataOutputStream(connectionSocket.getOutputStream());

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

			if (list.get(0).equalsIgnoreCase("Creation")) {
				System.out.println("make new room");
				room = new Server_Room_Thread(Integer.parseInt(list.get(1)));
				room.memory.addPerson(connectionSocket);
				room.start();
			} else if (list.get(0).equalsIgnoreCase("Participation")) {
				if (room.memory.isFull() == false) {
					room.memory.addPerson(connectionSocket);
				}
			}
		}
	}
}