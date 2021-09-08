package Classes;


import java.io.BufferedReader;

//import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.net.ServerSocket;
import Classes.Document;
import Classes.Users;

public class OurEditServer{
	
	private int port = 1111;
	private ServerSocket serverSocket;
	private ArrayList<ServerThread> clients = new ArrayList<>(); //keep track of connected clients
	private String client_name;
	

	static final String dbURL = "jdbc:mysql://localhost:3306/ouredit";
	
	public OurEditServer() throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		}
	
	  public void acceptConnections() {
	    	
			try {
			
					serverSocket = new ServerSocket(port);  //create welcome socket
			 
			}
			
			catch (IOException e) {  //catch creation of socket failure
			  
					System.err.println("ServerSocket instantiation failure"); 
					
					e.printStackTrace();
					
					System.exit(0);
			
			}
	   
	   //Entering the infinite loop
	   
			while (true) {
									   
							try { 	//wait for a TCP handshake initialization (arrival of a "SYN" packet)
							 
							        	
									Socket newConnection = serverSocket.accept(); //listens for socket from client
									
									System.out.println("accepted connection"); //prints it if try block succeeds
									
									client_name = "client" + clients.size()+1;
									System.out.println(client_name);
									ServerThread st = new ServerThread(newConnection, clients, client_name); //Now, pass the connection socket created above to a thread and run it in it
									     
									clients.add(st);
									
									new Thread(st).start();  //Then, start the thread, and go back to waiting for another TCP connection
							}
	        
	        
							catch (IOException ioe) { 
								
									System.err.println("server accept failed"); 
							}
	    }
}
	  
	  
public static void main(String args[]) {
 
		OurEditServer server = null;  //OurEdit 
		try {
		   
				server = new OurEditServer(); //Instantiate an object of this class. This will load the JDBC database driver
		}  
		  
		
		catch (ClassNotFoundException e) {
				 System.out.println("unable to load JDBC driver");
				 e.printStackTrace();
				 System.exit(1);
		}
		server.acceptConnections();   //call this function, which will start it all...
		 
}

//Internal class
class ServerThread implements Runnable {
		private Socket socket;
		private DataOutputStream toClient;
		private BufferedReader fromClient;
		private ArrayList<ServerThread> clients;
		private String client_name;
		
		public ServerThread(Socket socket, ArrayList<ServerThread> clients, String client_name) {
				this.clients = clients;
				this.socket = socket;   //Inside the constructor: store the passed object in the data member
				this.client_name = client_name;
		}
			 
	  //This is where you place the code you want to run in a thread
		
	  //Every instance of a ServerThread will handle one client (TCP connection)
		
		
		public void run() {
			
			try {
			
				toClient = new DataOutputStream(socket.getOutputStream());
				fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			       
			}
			
			catch (IOException e) {
				
			return;
			}
     
        
			boolean conversationActive = true; //boolean to keep server running till a signal from user

			while (conversationActive) {
				
					String str;
					String content;
					String docname;
					Users user;
					String username;
					String password;
					String email;
					Document doc;
					String stringout;
					int numb_users = 0;
					          
					try {
						str = fromClient.readLine(); //store input stream in "str" string
						System.out.println("str got input from client");
						
						doc = new Document();
						user = new Users();
						
					if (str == null) {}
					
					
					else if (str.substring(0, 3).equals("new")) { //save document / document changes to database
						
						System.out.println("serverThread: client new doc on server");
						docname = str.substring(3, str.length());
						doc.newDoc(docname);
						
						for (ServerThread aClient : clients ) {  //assume all users of app are online for simplicity 
					
							String out = "new"+ docname;
			
								aClient.toClient.write((out+"\n").getBytes());
								aClient.toClient.flush();
								System.out.println("might have sent it");
							}
							
						}
					
					
						

				
					else if (str.substring(0, 3).equals("get")) {  //get text from database
						System.out.println("serverThread: client get doc from server");
						docname = str.substring(3,str.length()); 
						content = doc.editDoc(docname);
						System.out.println(content);
						toClient.write(("get"+content+"\n").getBytes());
						toClient.flush();
						System.out.println(content);
						}
					
					else if (str.substring(0, 4).equals("save")) { //save document / document changes to database
			
						String [] sub_obj = str.split("\\+"); //split string to retrieve docname and content
						System.out.println("serverThread: client save doc on server");
						docname = sub_obj[1];
						content  = sub_obj[2];
						doc.saveDoc(docname, content);
						toClient.write(("save"+docname+"\n").getBytes());
						toClient.flush();
					
						
						
						
					}
					else if (str.substring(0, 5).equals("login")) {		//login function
						String[] docs;
						
						String [] sub_obj = str.split("\\+"); //split string to retrieve username and password
						System.out.println("serverThread: client login");
						username = sub_obj[1];
						password  = sub_obj[2];
						boolean correct_login = user.login(username, password);
						
						if (correct_login == true) {
							System.out.println("serverThread: correct login");
							client_name = username; //change default name "client#" to username of user upon successful login
							int i = 0;
							docs = user.Open_specific_docs(username); //get name of docs of user from database
							
							////assemble docnames together to transport through socket
							stringout= docs[i];
							i++;
							while ((i <docs.length) && (docs[i] != null)) { 
								 stringout+=("+"+docs[i]);
								i++;
							}
							
						}
						else{  //if user.login false send back to client# that login failed
							stringout = "failed";
						}
						toClient.write(("login"+stringout+"\n").getBytes());
						toClient.flush();
						System.out.println("serverThread: flushed out docnames");
						
					}	
					
					else if (str.substring(0, 6).equals("delete")) {  //delete text from database
							System.out.println("serverThread: client delete doc on server");
							docname = str.substring(6,str.length());
							doc.deleteDoc(docname);
							toClient.write(("deleted"+docname+"\n").getBytes());
							toClient.flush();
					
							
							
							
							
					
					}
						else if (str.substring(0, 6).equals("sugdel")) {     //one user sends delete notification to other users
						String [] sub_obj = str.split("\\+"); 			 //split string to retrieve username and password
						System.out.println("serverThread: client suggest delete");
						docname = sub_obj[1];															
						username= sub_obj[2];
						System.out.println("serverThread: client suggests deletion");
						ArrayList<String> specific_clients = doc.getdocUsers(docname);		//get name of users that have privilege over this document so server can send deletion notification
						if (specific_clients.size() == 1) {
							doc.deleteDoc(docname);
				
							
						}
						else {
							outToUsers(docname, username, specific_clients);  //sends for every user concerned a string telling it that someone wants to delete 
							System.out.println("serverThread: trying to send to specific clients docname");
						}
					
					}
					
					
					else if (str.substring(0, 9).equals("createacc")) {   //create account function
						
						String [] sub_obj = str.split("\\+"); //split string to retrieve username and password
						System.out.println("serverThread: client create acc");
						username = sub_obj[1];
						password  = sub_obj[2];
						email = sub_obj[3];
						client_name = username;  //change client name to inserted username
						
						String announce = user.Create_account(username, password, email);
						if (announce.equals("success")) {
							stringout = "Account creation successful!";
						}
						else if (announce.equals("baduser")) {
							stringout = "username already taken, please try again";
						}
						else {
							stringout = "Incorrect password, please try again";
						}
						toClient.write(("createacc"+stringout+"\n").getBytes());
						toClient.flush();
					}
					
					
					else if (str.substring(0, 9).equals("delchoice")) { 
						docname = str.substring(9, str.length());
						ArrayList<String> specific_clients = doc.getdocUsers(docname);	 //get list of users with this doc
						numb_users = specific_clients.size();					//get number of users with this doc
						int users=doc.DelIncrement(docname, numb_users);					//pass document name and number of users to the function that//increments deletions from users until all of them agree
							if (users==numb_users) {
								outToUsers2(docname, specific_clients);
								System.out.println("serverThread: trying to send to specific clients that doc was deleted");
							}	
							
					}
					
					 
					
  
					}
					catch (IOException | ClassNotFoundException ioe) {
						  
					conversationActive= false; //I/O exception ends server
					
					}
	}
	     
    try {
    	
      System.out.println("closing socket"); //code ends -> so does server by closing socket

      socket.close(); 
	}
	        
	catch (IOException e) {
		
	}
	        
}
		
		
		
		private void outToUsers(String docname, String sender, ArrayList<String> specific_clients) throws IOException {

			for (ServerThread aClient : clients ) {  //assume all users of app are online for simplicity   //joseph kinan clara marie ...
				
				
				String out = "sugdel"+ docname;
				if (specific_clients.contains(aClient.client_name) & !aClient.client_name.equals(sender) ) { //dont send to self, //looks for users who own this document AND are ONLINE
					aClient.toClient.write((out+"\n").getBytes());
					aClient.toClient.flush();
					System.out.println("might have sent it");
				}
				
			}
		
			
		}
		
		
		private void outToUsers2(String docname, ArrayList<String> specific_clients) throws IOException {

			for (ServerThread aClient : clients ) {  //assume all users of app are online for simplicity 
				
				
				String out = "deleted"+ docname;
				if (specific_clients.contains(aClient.client_name)) { //send to all doc owners that doc was deleted
					aClient.toClient.write((out+"\n").getBytes());
					aClient.toClient.flush();
					System.out.println("might have sent it");
				}
				
			}
		
			
		}
		
	 }
	      

}

	 


