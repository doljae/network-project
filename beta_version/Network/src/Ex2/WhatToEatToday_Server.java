package Ex2;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.io.*;

public class WhatToEatToday_Server {
	public static void main(String[] args) throws Exception {
		System.out.println("Server Started");
		HashMap<Integer, Memory> share = new HashMap<Integer, Memory>();
		
		ServerSocket welcomeSocket = new ServerSocket(6789);//set the port for client
		
		while(true){
			System.out.println("ready for another user");
			Socket connectionSocket = welcomeSocket.accept();//accept the client connection
			System.out.println("Found User");
			
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));//to receive from client
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());//to send to client
			String receive = inFromClient.readLine();
			
			if(receive.indexOf(" ") == -1){//띄어쓰기가 없단건 방 생성이라는 의미
				while(true){
					int n = new Random().nextInt(1000);
					if(!share.containsKey(n)){
						//서버 스레드 생성 후 연결
						Memory temp = new Memory(Integer.valueOf(receive));
						share.put(n, temp);
					}
				}
			}
			else{
				receive = receive.substring(2);
				//없을수도 있음 
			}
			new Client_Thread(connectionSocket, share, receive).start();//start thread for multiUser connection 
		}
	}
}

class Memory {
	int numPerson;
	ArrayList<String> remain_foods = new ArrayList<String>();
	public Memory(int num){
		numPerson = num;
	}
	public void run() {
	}
}

class Server_Thread extends Thread{
	HashMap<Integer, Memory> room_list;
	int room_num;
	public Server_Thread(HashMap<Integer, Memory> share, int num){
		room_list = share;
		room_num = num;
	}
	public void run() {
		room_list.get(room_num).pplNum++;
	}
}

class Client_Thread extends Thread{
	Socket connectionSocket;
	HashMap<Integer, Memory> room_list;
	int room_num;
	public Client_Thread(Socket socket, HashMap<Integer, Memory> share, String receive){
		connectionSocket = socket;//referenced from main to identify connected client
		room_list = share;
		room_num = Integer.getInteger(receive);
	}
	public void run() {
		System.out.println("Client thread started");
		/*
		String receive;//where the client request will store
		String answer = null;//where the server response will store
		*/
		//총 인원이 클릭 한 다음에 모듈러 후 결과 출력
		//사람마다 번호 할당 필요
		room_list.get(room_num).pplNum++;
		//while 해당 리스트 사이즈 > 1
		//어레이 리스트에서 첫번쨰
		//어레이 리스트에서 두번재 선택
		// 
		BufferedReader inFromClient;
		try {//to handle even if the IO connection with client is lost
			inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));//to receive from client
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());//to send to client
			receive = inFromClient.readLine();//receive the protocol from client
			
			ArrayList<String> list = new ArrayList<String>();//to decompose the request
			int point = receive.indexOf(" ");//fine the 'space'
			
			while(point != -1){//do until there is no 'space'
				list.add(receive.substring(0, point));//insert receive of 'first' to 'space' at list
				receive = receive.substring(point + 1);//delete stored string
				point = receive.indexOf(" ");//find new 'space'
			}
			list.add(receive);//insert remain(last) string
			
			if(list.get(0).equalsIgnoreCase("REQUEST")){//the request protocol started with 'REQUEST'
				if(list.size() == 4){//the length of request have to 4
					try{//to handle even if the operand can not translate
						float value_1 = Float.parseFloat(list.get(2));//store operand 1 from list
						float value_2 = Float.parseFloat(list.get(3));//store operand 2 from list
						
						if(list.get(1).equalsIgnoreCase("ADD")){//the operator is '+'
							answer = "0 " + (value_1 + value_2);
						}
						else if(list.get(1).equalsIgnoreCase("SUB")){//the operator is '-'
							answer = "0 " + (value_1 - value_2);
						}
						else if(list.get(1).equalsIgnoreCase("MULTI")){//the operator is '*'
							answer = "0 " + (value_1 * value_2);
						}
						else if(list.get(1).equalsIgnoreCase("DIV")){//the operator is '/'
							if(value_2 != 0){//the operand 2 must not 0
								answer = "0 " + (value_1 / value_2);
							}
							else{//Divide by 0
								answer = "5";
							}
						}
						else{//operator format is incorrect
							answer = "4";
						}
					} catch(Exception e){//operands format is incorrect
						answer = "3";
					}
				}
				else{//length of the request is incorrect
					answer = "2";
				}
			}
			else{//unable to interpret the 'request'
				answer = "1";
			}
			outToClient.writeBytes("RESPONSE " + answer + '\n');//sent response to client
		} catch (IOException e1) {//the IO connection with client is lost
			e1.printStackTrace();//print the Exception
		}
		try {
			connectionSocket.close();//close the socket
		} catch (IOException e) {//unable to close the socket(ex: it is already closed)
			e.printStackTrace();//print the Exception
		}
	}
}