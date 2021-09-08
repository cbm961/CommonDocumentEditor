package Classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;


public class Document {
	
	static final String dbURL = "jdbc:mysql://localhost:3306/ouredit";
	
	public Document() throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		}
	public void newDoc(String name) { 
		Connection conn =null;
		try {
			conn = DriverManager.getConnection(dbURL, "root", "Goldelino19");


			String query = "INSERT INTO documents (name) VALUES (?)"; //inserts document in documents table
	
					System.out.println("query = " + query);

					PreparedStatement preparedStmt = conn.prepareStatement(query); 
					preparedStmt.setString (1, name);
		

				      // execute the prepared statement
				      preparedStmt.execute();
				   
				      conn.close();
					
		} catch (SQLException e) {
			
			e.printStackTrace();
			System.out.println("cant connect to database server");
		}
		
		
	}
	


	public void saveDoc(String name, String content) { 
		Connection conn =null;
		int number_users=0;
		try {
			conn = DriverManager.getConnection(dbURL, "root", "Goldelino19");


			String query = "INSERT INTO documents (name, content) VALUES (?, ?)" //if is new then save new row into database table, if it already exists then update contents.
			+ "ON DUPLICATE KEY UPDATE "
			+ "content = '" + content+ "'";
					System.out.println("query = " + query);

					PreparedStatement preparedStmt = conn.prepareStatement(query); 
					preparedStmt.setString (1, name);
					preparedStmt.setString (2, content);	
				      // execute the prepared statement
				      preparedStmt.execute();
				   
				      conn.close();
					
		} catch (SQLException e) {
			
			e.printStackTrace();
			System.out.println("cant connect to database server");
		}
		
		
	}
	

	public int DelIncrement(String docname, int numb_users) {	//should check if all users are ok with this doc being deleted 
		Connection conn =null;
		String delete_count= "1"; 
		int delete_C = 1;
		
		try {
			conn = DriverManager.getConnection(dbURL, "root", "Goldelino19");
			String query = "SELECT delete_count " +
					"FROM documents " + "WHERE name = " +
					"'" + docname + "'";
					System.out.println("query = " + query);
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery(query);
					StringBuffer sb = new StringBuffer();
					while (rs.next()) {
					sb.append(rs.getString(1));
	
					}
					delete_count = sb.toString();
		
					
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			delete_C = Integer.parseInt(delete_count);
			delete_C++;
			System.out.println(numb_users);
			if (delete_C == numb_users) {   //if number of users that wish to delete = number of users who own the doc then delete document from database 
				this.deleteDoc(docname);
				return numb_users;
			}
			
			String delete_count2 = String.valueOf(delete_C);
			conn = DriverManager.getConnection(dbURL, "root", "Goldelino19");
			String query = "UPDATE documents SET delete_count= "+ 
			delete_count2 + " WHERE name= " + " '" +docname +"'" ;
					System.out.println("query = " + query);

					PreparedStatement preparedStmt = conn.prepareStatement(query);
				      // execute the prepared statement
				      preparedStmt.execute();
				   
				      conn.close();
					
		} catch (SQLException e) {
			
			e.printStackTrace();
			System.out.println("cant connect to database server");
		}
		
		return -1;
	}
	
	public void deleteDoc(String name) {  //deletes document from database
	
		

		try {
			Connection conn =null;
			
			conn = DriverManager.getConnection(dbURL, "root", "Goldelino19");
	
			String query = "DELETE FROM documents WHERE name = " + " '" + name + "' ";   //deletes from documents table
					System.out.println("query = " + query);
					Statement stmt = conn.createStatement();
					 stmt.executeUpdate(query);
				   
				      conn.close();
					
		} catch (SQLException e) {
			
			e.printStackTrace();
			System.out.println("cant connect to database server");
		}
		try {
			Connection conn =null;
			
			conn = DriverManager.getConnection(dbURL, "root", "Goldelino19");
	
			String query = "DELETE FROM users_documents WHERE documentname = " + " '" + name + "' ";  //also deletes from users_documents table
					System.out.println("query = " + query);
					Statement stmt = conn.createStatement();
					 stmt.executeUpdate(query);
				   
				      conn.close();
					
		} catch (SQLException e) {
			
			e.printStackTrace();
			System.out.println("cant connect to database server");
		}
	}
	
	public String editDoc(String name) {   //gets text to take to client
		Connection conn =null;
		String content = null;
		try {
			conn = DriverManager.getConnection(dbURL, "root", "Goldelino19");
			Statement stmt = conn.createStatement();
			String query = "SELECT content " +
					"FROM documents " + "WHERE name = " +
					"'" + name + "'";
					System.out.println("query = " + query);
					
					ResultSet rs = stmt.executeQuery(query);
					
					StringBuffer sb = new StringBuffer();
					while (rs.next()) {
					sb.append(rs.getString(1));
	
					}
					if (sb.toString().isEmpty()) { 
						System.out.println("no document under that name");
						content = "";
					}
					else {
					content = sb.toString();
					
					}
					
		} catch (SQLException e) {
			
			
			e.printStackTrace();
		}
		return content; 
		
		
		
	}
	

	public ArrayList<String> getdocUsers(String docname) {   //put all users that own a specific doc in an array list 
		Connection conn =null;
		ArrayList<String> usernames = new ArrayList<String>();
		
		try {
			conn = DriverManager.getConnection(dbURL, "root", "Goldelino19");

			Statement stmt = conn.createStatement();
			String query = "SELECT " + "username" +
					" FROM users_documents " + "WHERE documentname = " + "'" + docname + "'";
					System.out.println("query = " + query);
					
					ResultSet rs = stmt.executeQuery(query);
					while (rs.next()) {
						
						usernames.add(rs.getString(1));
		
					
					}

		} catch (SQLException e) {
						
						e.printStackTrace();
						System.out.println("cant connect to database server");
					}
		return usernames;
		
	}	
		
		

	public static void main(String args[]) {
		Document new_doc = null;  
		try {
		   
			new_doc = new Document(); 
		}  
		  
		
		catch (ClassNotFoundException e) {
				 System.out.println("class document not found");
				 e.printStackTrace();
				 System.exit(1);
		}
		
		
		
	}

}




