package Classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Users {
	
	static final String dbURL = "jdbc:mysql://localhost:3306/ouredit";
	
	public Users() throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		}
	
	public String Create_account(String name, String password, String email)  {  //create account
		Connection conn =null;
		String announce= null;
		try {
			conn = DriverManager.getConnection(dbURL, "root", "Goldelino19");

			Statement stmt = conn.createStatement();
			String query = "SELECT * " +			//checks if username already taken
					"FROM users " + "WHERE name = " +
					"'" + name + "'";
					System.out.println("query = " + query);
					
					ResultSet rs = stmt.executeQuery(query);
					
					StringBuffer sb = new StringBuffer();
					while (rs.next()) {
					sb.append(rs.getString(1));
					}
					
					if (!sb.isEmpty()) { 
						System.out.println("sorry username already taken");
						announce = "baduser";
						}
					
					else {	
						String query2 = "SELECT * " + //check if password already taken
								"FROM users " + "WHERE password = " +
								"'" + password + "'";
								System.out.println("query = " + query2);
								
								ResultSet rs2 = stmt.executeQuery(query2);
								
								StringBuffer sb2 = new StringBuffer();
								while (rs2.next()) {
								sb2.append(rs2.getString(1));
								}
								
						if (!sb2.isEmpty()) { 
							System.out.println("sorry password already taken");
							announce = "badpass";
						}
						
						else {
						String query3 = "INSERT INTO users (name, password, email) VALUES (?, ?, ?)";
						System.out.println("query = " + query3);
						System.out.println("Account created!");
						PreparedStatement preparedStmt = conn.prepareStatement(query3); 
						preparedStmt.setString (1, name);
						preparedStmt.setString (2, password);	
						preparedStmt.setString(3, email);
					      // execute the prepared statement
					      preparedStmt.execute();
					      
					      announce = "success";
						}
					}
		 conn.close();
					
		} catch (SQLException e) {
			
			e.printStackTrace();
			System.out.println("cant connect to database server");
		}
			
		return announce;
		
	}
	
	public boolean login(String username, String password) {  //login 
		Connection conn =null;
		boolean correct_login = false;
		
		try {
			conn = DriverManager.getConnection(dbURL, "root", "Goldelino19");

			Statement stmt = conn.createStatement();
			String query = "SELECT * " +			
					"FROM users " + "WHERE name = " +
					"'" + username + "'";
					System.out.println("query = " + query);
					
					ResultSet rs = stmt.executeQuery(query);
					
					StringBuffer sb = new StringBuffer();
					while (rs.next()) {
					sb.append(rs.getString(1));
					}
					
					if (sb.isEmpty()) { 
						System.out.println("NO user under that name");
						correct_login = false;
						
						}
					
					else {	
						String query2 = "SELECT * " + //check if password already taken
								"FROM users " + "WHERE password = " +
								"'" + password + "'";
								System.out.println("query = " + query2);
								
								ResultSet rs2 = stmt.executeQuery(query2);
								
								StringBuffer sb2 = new StringBuffer();
								while (rs2.next()) {
								sb2.append(rs2.getString(1));
								
								
								}
								
						if (sb2.isEmpty()) { 
							System.out.println("incorrect password please try again");
							correct_login = false;
							
						}
						
						else {
								correct_login = true; //access to editing window
								System.out.println("login successful!");
								
						}
					}
				
					
		 conn.close();
					
		} catch (SQLException e) {
			
			e.printStackTrace();
			System.out.println("cant connect to database server");
		}
		return correct_login;
		
		
	}
	
	public String[] Open_specific_docs(String username){  //get docs that user can access.
		Connection conn =null;
		String doc_names[] = null;
		
		try {
			conn = DriverManager.getConnection(dbURL, "root", "Goldelino19");

			Statement stmt = conn.createStatement();
			String query = "SELECT " + "documentname" +
					" FROM users_documents " + "WHERE username = " + "'" + username + "'";
					System.out.println("query = " + query);
					
					ResultSet rs = stmt.executeQuery(query);
					 doc_names = new String[30];
					int i =0;
					while (rs.next()) {
						
						doc_names[i]= rs.getString(1);
		
						i++;
					
					}

		} catch (SQLException e) {
						
						e.printStackTrace();
						System.out.println("cant connect to database server");
					}
		return doc_names;
		
	}
	public static void main(String[] args) throws ClassNotFoundException {
		// TODO Auto-generated method stub
	
	}

}
