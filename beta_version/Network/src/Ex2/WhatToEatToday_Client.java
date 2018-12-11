package Ex2;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class WhatToEatToday_Client {
	public static void main(String[] args) throws Exception {
		//서버의 주소
		String hostName = "127.0.0.1";
		int hostPort = 6789;
		
		Scanner keyboard = new Scanner(System.in);//to receive a calculation formula
		int choose = 0;
		
		Socket clientSocket = null;
		DataOutputStream outToServer;
		BufferedReader inFromServer;
		
		while(true){
			System.out.println("방 만들기: 1\n"
					+ "방 참여: 2\n"
					+ "끝내기: 3\n"
					+ "->");
			choose = keyboard.nextInt();
			
			if(choose == 3)
				break;//버튼으로 옮겨야함
			
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
					//종료
				}
			}
			
			if(choose == 2){
				System.out.println("방 번호: ");
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