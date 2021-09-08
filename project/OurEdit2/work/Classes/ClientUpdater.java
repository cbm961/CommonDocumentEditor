package Classes;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientUpdater implements Runnable { //waits for input from server  //listens for updates  
	public Socket socket;
	public DataOutputStream toClient;
	public BufferedReader fromServer;
	public String docname;
	public String account_creation;
	public String save_notif ;
	public String delete_notif;
	public String content;
	public boolean delete_choice;
	public String delete_docname;
	public String[] doc_names;
	public String deleted_notif;
	public String deleted_docname;

	
	public ClientUpdater(Socket socket) throws IOException  {
		this.socket = socket;
		fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	@Override
	public void run() { //handles inputs from the server
		
		try {
				while (true) {
				String str;
				System.out.println("got into client updater");	
				str = fromServer.readLine();
				System.out.println("updater: "+str);
				
				 if (str.substring(0,3).equals("new")) {   //answer from server to save doc 
					System.out.println("went into save block in broadcaster");
					docname = str.substring(3, str.length());
					Window2.completely_new(docname); //popup window informing user that doc was saved
				}
				
				 else if (str.substring(0,3).equals("get")) {    //answer from server to get doc 
					content = (str.substring(3, str.length()));
					System.out.println("went into get block in broadcaster");
					System.out.println(content);
					Window2.textArea.setText(content); //set text in GUI
					
					
				}
				
				 else if (str.substring(0,4).equals("save")) {   //answer from server to save doc 
					System.out.println("went into save block in broadcaster");
					docname = str.substring(4, str.length());
					Window2.completely_saved(docname); //popup window informing user that doc was saved
				}
	
				else if (str.substring(0,5).equals("login")) {    //answer from server to login 
					 
					System.out.println("went into login block in broadcaster");
					if (!str.equals("failed")) {
						 doc_names = str.substring(5, str.length()).split("\\+"); //split string to retrieve docname and content
					}
					else { System.out.println("failed login"); }
				}
				
			   else if (str.substring(0,6).equals("sugdel")) {    //answer to client from suggest delete
				   
						System.out.println("do you wish to delete doc: " + str.substring(6, str.length()));		
						
						delete_docname = str.substring(6, str.length());
						Window2.suggest_delete_listener(delete_docname);
					
					
				}
			   else if (str.substring(0,7).equals("deleted")) {    //answer from server to account creation 
					 
					System.out.println("went into deleted block in broadcaster");
					deleted_docname = str.substring(7, str.length());
					
					Window2.completely_deleted(deleted_docname);
					
				}
				 
				else if (str.substring(0,9).equals("createacc")) {    //answer from server to account creation 
					 
					System.out.println("went into createacc block in broadcaster");
					account_creation = str;
					
				}
				

				
					
			
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		finally { try {
			fromServer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} }
		
		
		
	
	}

	
}
