package Classes;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Classes.ClientUpdater;
import Classes.Client;
import Classes.Users;
import java.awt.Font;
import java.awt.Image;
import javax.swing.SwingConstants;

public class mmyedits implements ActionListener {

	private final JFrame frame;
	private final JPanel panel;
	private final JButton loginButton;
	private final JLabel userLabel;
	private final JTextField userText;
	private final JLabel passwordLabel;
	private final JPasswordField passwordText;
	private final JButton createnew;
	private final Color green = Color.GREEN;
	private final Client client;
	public String username;
	public String password;
	

	public mmyedits(Client c) throws IOException {   //takes client as argument
		this.client = c;
	

		frame = new JFrame("Our Edit");   //name of frame
		frame.setBounds(100, 100, 531, 418);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new JPanel();
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		userLabel = new JLabel("Username: ");
		userLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		userLabel.setBounds(140,108,80,25);
		panel.add(userLabel);
		
		userText = new JTextField(20);
		userText.setBounds(244,108,165,25);
		panel.add(userText);
		
		 passwordLabel = new JLabel("Password:");
		 passwordLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
	     passwordLabel.setBounds(140,158,80,25);
	     panel.add(passwordLabel);

	     passwordText = new JPasswordField(20);
	     passwordText.setBounds(244,160,165,25);
	     panel.add(passwordText);

	     loginButton = new JButton("Login");
	     loginButton.setFont(new Font("Tahoma", Font.BOLD, 11));
	     loginButton.setBounds(240, 225, 80, 25);
	     loginButton.addActionListener(new loginListener());
	     panel.add(loginButton);
	     
	     createnew = new JButton ("Create New Account");
	     createnew.setFont(new Font("Tahoma", Font.BOLD, 11));
	     createnew.setBounds(240,267,150,25);
	     createnew.setBackground(Color.GRAY);
	     createnew.addActionListener(new createAccountListener());
	     panel.add(createnew);
	     
	     
	     JLabel background = new JLabel();
	     background.setBounds(0, 0, 515, 379);
	     panel.add(background);
	     background.setIcon(new ImageIcon("C:\\Users\\User\\Desktop\\350-project\\images\\background.jpeg"));
	     
	     

	}



	 protected class loginListener implements ActionListener  {
		public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		 username = userText.getText();     			//collects username and password from text fields
		 password =String.valueOf(passwordText.getPassword());
		 client.username = username;
		 client.password = password;
		frame.setVisible(false);
			try {
				client.user_login(username, password);   //calls login function 
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Window2 w2=null;  
			try {
				w2 = new Window2(client);   //calls Window2 which is the editing window
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//w2.initialize();
		}
			
		
	 }
	 protected class createAccountListener implements ActionListener  {  //create account opens the create account window named Window3
			public void actionPerformed(ActionEvent e) {
	 
			frame.setVisible(false);  
			Window3 w3= new Window3(client);

			
		}
	 }
	 
	 public static void main(String argv[]) throws Exception {
		 
		 Client client1 = new Client();   //instantiates new client
		 try {
				client1.initialize();  //calls initializes that instantiates an input and output stream
				 
			} catch (UnknownHostException e2) {
				e2.printStackTrace();
			} catch (IOException e2) {
				e2.printStackTrace();
			} catch (ClassNotFoundException e2) {
				e2.printStackTrace();
			}
		 
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						mmyedits login_window = new mmyedits(client1);  //starts this class
						login_window.frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		 
	 }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}


		