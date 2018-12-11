package Ex2;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class WhatToEatToday_Client {
	public static void main(String[] args) throws Exception {
		//������ �ּ�
		String hostName = "127.0.0.1";
		int hostPort = 6789;
		
		Scanner keyboard = new Scanner(System.in);//to receive a calculation formula
		int choose = 0;
		
		Socket clientSocket = null;
		DataOutputStream outToServer;
		BufferedReader inFromServer;
		
		while(true){
			System.out.println("�� �����: 1\n"
					+ "�� ����: 2\n"
					+ "������: 3\n"
					+ "->");
			choose = keyboard.nextInt();
			
			if(choose == 3)
				break;//��ư���� �Űܾ���
			
			if(choose == 1){
				clientSocket = new Socket(hostName, hostPort);
				outToServer = new DataOutputStream(clientSocket.getOutputStream());//to send to server
				inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));//to receive from server
				
				outToServer.writeBytes("1");
				String answer = inFromServer.readLine();
				if(answer.equalsIgnoreCase("ok"))
					while(answer.equalsIgnoreCase("full")){
						answer = inFromServer.readLine();
						System.out.println(answer);
					}
				else{
					System.out.println("room creat fail");
					//����
				}
			}
			
			if(choose == 2){
				System.out.println("�� ��ȣ: ");
				int room_num = keyboard.nextInt();
				
				clientSocket = new Socket(hostName, hostPort);
				outToServer = new DataOutputStream(clientSocket.getOutputStream());//to send to server
				inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				
				outToServer.writeBytes("2 " + room_num);
			}
		}

		System.out.println("Program close");
		clientSocket.close();//close the socket
		keyboard.close();//close input from user
	}
}