package Ex1;
import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class Calculator_Server {
	public static void main(String[] args) throws Exception {
		System.out.println("Server Started");
		ServerSocket welcomeSocket = new ServerSocket(6789);//set the port for client
		while(true){
			System.out.println("ready for another user");
			Socket connectionSocket = welcomeSocket.accept();//accept the client connection
			System.out.println("Found User");
			new Calculate_Thread(connectionSocket).start();//start thread for multiUser connection 
		}
	}
}

class Calculate_Thread extends Thread{
	Socket connectionSocket;
	public Calculate_Thread(Socket socket){
		connectionSocket = socket;//referenced from main to identify connected client 
	}
	public void run() {
		System.out.println("thread started");
		String receive;//where the client request will store
		String answer = null;//where the server response will store
		
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