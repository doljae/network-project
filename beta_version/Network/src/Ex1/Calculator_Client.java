package Ex1;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Calculator_Client {
	public static void main(String[] args) throws Exception {
		String fileName = "serverinfo.bat";//server info file name
		System.out.println("File Opening");
		Scanner inputStream = null;//where the read string will store
		try{
			inputStream = new Scanner(new File(fileName));//try to open file
		} catch(FileNotFoundException e){//file is not opened
			System.out.println("File Opening Error");
			System.exit(0);
		}
		System.out.println("File Opening Success");
		
		System.out.println("File Reading");
		String hostName = inputStream.nextLine();//read server IP
		int hostPort = inputStream.nextInt();//read server port
		inputStream.close();//finishing reading file
		System.out.println("File Reading Success");
		
		Scanner keyboard = new Scanner(System.in);//to receive a calculation formula
		String answer;//where the server response will store
		
		Socket clientSocket = new Socket(hostName, hostPort);//try socket connect to server
		
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());//to send to server
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));//to receive from server

		String[] operator = {"ADD", "SUB", "MULTI", "DIV"};//the operators for calculation
		System.out.println("Choose an operator");
		System.out.println(" 1: +");
		System.out.println(" 2: -");
		System.out.println(" 3: *");
		System.out.println(" 4: /");
		int operator_num = keyboard.nextInt() - 1;//read the choice from user
		
		System.out.println("Enter two value");
		float value_1 = keyboard.nextFloat();//read the operand 1 from user
		float value_2 = keyboard.nextFloat();//read the operand 2 from user
		
		outToServer.writeBytes("REQUEST" + " " +
								operator[operator_num] + " " +
								value_1 + " " +
								value_2 + '\n');//send protocol to server
			
		answer = inFromServer.readLine();//receive response from server
		
		ArrayList<String> list = new ArrayList<String>();//to decompose the response
		int point = answer.indexOf(" ");//fine the 'space'
		
		while(point != -1){//do until there is no 'space'
			list.add(answer.substring(0, point));//insert answer of 'first' to 'space' at list
			answer = answer.substring(point + 1);//delete stored string
			point = answer.indexOf(" ");//find new 'space'
		}
		list.add(answer);//insert remain(last) string
		
		if(list.get(0).equalsIgnoreCase("RESPONSE")){//the response protocol started with 'RESPONSE'
			if(list.size() == 2 || list.size() == 3){//the length of response have to between 2 to 3
				if(list.get(1).equalsIgnoreCase("0")){//receive success response
					System.out.println("Answer: " + list.get(2));//print answer
				}
				else if(list.get(1).equalsIgnoreCase("1")){//Error type 1
					System.out.println("Error: The server was unable to interpret the 'request'");
				}
				else if(list.get(1).equalsIgnoreCase("2")){//Error type 2
					System.out.println("Error: The length of the request is incorrect");
				}
				else if(list.get(1).equalsIgnoreCase("3")){//Error type 3
					System.out.println("Error: The operands format is incorrect");
				}
				else if(list.get(1).equalsIgnoreCase("4")){//Error type 4
					System.out.println("Error: The operator format is incorrect");
				}
				else if(list.get(1).equalsIgnoreCase("5")){//Error type 5
					System.out.println("Error: Divide by 0");
				}
			}
			else{//Error at response length
				System.out.println("Error: The length of the response is incorrect");
			}
		}
		else{//The message is 'not response' or 'corrupted'
			System.out.println("Error: The client was unable to interpret the 'response'");
		}
		
		System.out.println("Program close");
		clientSocket.close();//close the socket
		keyboard.close();//close input from user
	}
}