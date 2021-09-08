package Classes;

import java.io.*;
import java.net.*;

//import javax.swing.text.BadLocationException;




public class Client{
	public static DataOutputStream toServer;
	public BufferedReader fromServer;
	public static String ip;
	public Integer port;
	public Socket clientSocket;
	public ClientUpdater updater;
	public String username;
	public String password;
	


	public Client() {

	}


	/**
	 * 
	 * @param docname : name of document
	 * @param username : name of user 
	 * @param password : password of user 
	 * @param content : content of document 
	 * @throws IOException
	 */
	
	public void newDocOnServer(String docname) throws IOException {  //send to server new document creation request
		System.out.println("newdoc to server ");
		toServer.write(("new"+docname+"\n").getBytes());
		toServer.flush();
		
	}
	
	public void create_account(String username, String password, String email) throws IOException {  //send to server new account craetion request
		
		toServer.write(("createacc"+"+"+username+"+"+password+"+"+email+"\n").getBytes());
		toServer.flush();

		
	}
	
	public void user_login(String username, String password) throws IOException { //send to server that a user is trying to log in 
		String str;
		toServer.write(("login"+"+"+username+"+"+password+"\n").getBytes()); //adding + to split on server side
		toServer.flush();
		System.out.println("flushed");
		
	}
	
	public void suggest_delete(String docname, String username) throws IOException { //sending to server that a user wants to delete doc
		System.out.println("sending deletion request...");
		toServer.write(("sugdel" +"+"+docname+"+"+username+'\n').getBytes());
		toServer.flush();
		System.out.println("sugdel" +"+"+docname+"+"+username+'\n');
	}
	
	public static void delete_choice (int yes, String docname) throws IOException{ //sending to server whether a user accepted delete request or not
	;//must have window to input boolean, window pops up upon function call
		if (yes==1) {
			toServer.write(("delchoice"+docname+'\n').getBytes());
			toServer.flush();	
		}
		else { 
		}
		
	}
	
	public void deleteFileOnServer(String docname) throws IOException {  //not used
		toServer.write(("delete" + docname).getBytes());
		toServer.flush();

	}
	
	public void saveFileOnServer(String docname, String content) throws IOException { //sending to server to save doc
		toServer.write(("save" +"+"+docname+"+"+content+'\n').getBytes());
		toServer.flush();
	}

	public void GetFileFromServer(String docname) throws IOException { //requesting file from server
		toServer.write(("get"+docname+"\n").getBytes());
		toServer.flush();
		
		
	}
	
		public void initialize() throws UnknownHostException, IOException,  //method to start up client on GUI 
		ClassNotFoundException {
			clientSocket = new Socket("localhost", 1111); //create client socket to establish connection with server
			toServer = new DataOutputStream(clientSocket.getOutputStream()); //attaching output stream to socket
			fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		    updater = new ClientUpdater(clientSocket) ;
				 
			  new Thread(updater).start();
			
			
	}
			

	
}
	
