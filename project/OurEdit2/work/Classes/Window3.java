package Classes;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Font;
//import Client.java;
//import Classes.Client;

public class Window3 implements ActionListener{

	private final JFrame frame;
	private final JButton Create;
	private final JLabel newuser;
	private final JTextField userText;
	private final JLabel passwordLabel;
	private final JPasswordField passwordText;
	private final JLabel email;
	private final JTextField emailText;
    String passwords[];
	private final Client client;
	
	/**
	 * Launch the application.
	 */
	public Window3(Client c) {
		this.client = c; 
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		
	
		panel.setLayout(null);
		
		newuser = new JLabel("New Username:");
		newuser.setFont(new Font("Tahoma", Font.BOLD, 11));
		newuser.setBounds(10,20,100,25);
		panel.add(newuser);
		
		userText = new JTextField(20);
		userText.setBounds(120,20,165,25);
		panel.add(userText);
		
		 passwordLabel = new JLabel("Password:");
		 passwordLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
	     passwordLabel.setBounds(10,50,80,25);
	     panel.add(passwordLabel);

	     passwordText = new JPasswordField(20);
	     passwordText.setBounds(120,50,165,25);
	     panel.add(passwordText);
	     
	    email = new JLabel("E-Mail:");
	    email.setFont(new Font("Tahoma", Font.BOLD, 11));
		email.setBounds(10,80,80,25);
		panel.add(email);
		
		emailText = new JTextField(20);
		emailText.setBounds(120,80,165,25);
		panel.add(emailText);

	     Create = new JButton("Create");
	     Create.setFont(new Font("Tahoma", Font.BOLD, 11));
	     Create.setBounds(120, 116, 80, 25);
	     Create.addActionListener(new newAccountListener());
	     panel.add(Create);
	     
	     
	     
	     JLabel background = new JLabel();
	     background.setBounds(0, 0, 515, 379);
	     panel.add(background);
	     background.setIcon(new ImageIcon("C:\\Users\\User\\Desktop\\350-project\\images\\background.jpeg"));
	     
	     
	     frame.setVisible(true);
	}
	
protected class newAccountListener implements ActionListener  {
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String username = userText.getText();
		String password =String.valueOf(passwordText.getPassword());
		String email = emailText.getText().toString();
	
			try {
				client.create_account(username, password, email);
				 client.username = username;
				 client.password = password;
				frame.setVisible(false);
				
				Window2 window = new Window2(client);
	
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void initialize() {
	EventQueue.invokeLater(new Runnable() {
		public void run() {
			try {
				Window3 window = new Window3(client);
				window.frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
	}
		
});
	
}
	
}

	
/*	public static String[] add_element(int n, String myarray[], String ele) 
    { 
         int i;
         String newArray[] = new String[n + 1]; 
        //copy original array into new array
        for (i = 0; i<n; i++) 
              newArray[i] = myarray[i]; 
 
        //add element to the new array
        newArray[n] = ele; 

        System.out.println(Arrays.toString(newArray));
        return newArray;
        */
     
