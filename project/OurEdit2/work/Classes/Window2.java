package Classes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.DropMode;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import Classes.mmyedits.createAccountListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;
import javax.swing.SwingConstants;

public class Window2 implements ActionListener {

	private static JFrame frame = new JFrame();
	private final JMenuBar menubar;
	private final JMenu file;
	private final JMenuItem New;
	private final JMenuItem Open;
	private final JMenuItem Save;
	private final JMenu delete;
	private final JMenu help;
	public  static JTextArea textArea ;
	private static Client client;
	private JComboBox<String> doc_Selector;
	private String content;
	public static String docname;
	/*
	 * private final Users user; private final Document doc;
	 */
	
	private final JPanel panel;
	private JList<String> document_names;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;


	/**
	 * Launch the application.
	 */
	public Window2(Client client) throws IOException {
		this.client = client;


		
		frame = new JFrame("Our Edit");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\User\\Desktop\\laptop-editing-header.jpg")); 
		frame.setBounds(100, 100, 563, 580);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().add(panel);

		
		menubar = new JMenuBar();  //menu for functionalities
		frame.setJMenuBar(menubar);
		
		file = new JMenu("File");			//buttons for menu
		file.setFont(new Font("Arial", Font.PLAIN, 12));
		menubar.add(file);
		New = new JMenuItem("New Document");
		New.addActionListener(new newdoclistener()); //listener for every botton
		
		Open = new JMenuItem("Open Document");
		Open.addActionListener(new opendoclistener());
		
		Save = new JMenuItem("Save Document");
		Save.addActionListener(new savedoclistener());

		
		file.add(New); file.add(Open); file.add(Save);
		
		delete = new JMenu("Delete");	
		delete.setFont(new Font("Arial", Font.PLAIN, 12));
		menubar.add(delete);
		JMenuItem delete_req = new JMenuItem("Request delete");
		delete.add(delete_req);
		delete_req.addActionListener(new deletedoclistener());
		
		 
		help = new JMenu("Help");
		help.setFont(new Font("Arial", Font.PLAIN, 12));
		menubar.add(help);
		JMenuItem how = new JMenuItem("learn more");
		help.add(how);
		
	

		panel.setVisible(true);
		frame.setVisible(true);
		
		

		panel.setLayout(null);
		
		textArea = new JTextArea();
		textArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
			}
		});
		textArea.setBounds(10,101,476,419);
		panel.add(textArea);
		textArea.setVisible(true);
		
		lblNewLabel = new JLabel("Welcome, " + client.username);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBackground(new Color(255, 222, 173));
		lblNewLabel.setBounds(10, 11, 233, 28);
		panel.add(lblNewLabel);
		
		
		lblNewLabel_1 = new JLabel("Currently editing: <none> ");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setBounds(266, 76, 220, 14);
		panel.add(lblNewLabel_1);
		
		JLabel background = new JLabel();
		background.setLabelFor(frame);
		background.setHorizontalAlignment(SwingConstants.CENTER);
		background.setBounds(0, 0, 547, 520);
		panel.add(background);
		background.setIcon(new ImageIcon("C:\\Users\\User\\Desktop\\350-project\\images\\background.jpeg"));   //background of app
}


	  class newdoclistener implements ActionListener  {  //new button listener
		public void actionPerformed(ActionEvent e) {
			JTextField field1 = new JTextField();
	
			Object[] fields = {"Enter name of new document : ", field1};
			
			int input = JOptionPane.showConfirmDialog(frame, fields, "New Document", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);  //option pane to input name of new doc
			
			if(input == JOptionPane.OK_OPTION)
			{
				textArea.setText("");  //removes previous content of textArea
				docname = field1.getText(); //changes docname to current document
				System.out.println(docname);
				 lblNewLabel_1.setText("Currently editing: "+ docname);  //changes status of this Label 
				 try {
					client.newDocOnServer(docname);  //inserts new doc into database table
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			}
			
			
		}


	 }

		 protected class savedoclistener implements ActionListener  {  //save document listener 
			public void actionPerformed(ActionEvent e) {
				System.out.println("got into save listener");
				System.out.println(docname);
				content = textArea.getText();   //get content from textArea and puts it in client.save method 
				
				  try { client.saveFileOnServer(docname, content); System.out.println("saving"); } catch (IOException e1) {
				  // TODO Auto-generated catch block e1.printStackTrace(); }
				  }
		}
	 }

		 protected class opendoclistener implements ActionListener  {  //open document listener
			public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
				
				try {
					client.user_login(client.username, client.password);
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				String[] doc_names = client.updater.doc_names;  //get docnames of specific user

				
			    final JComboBox<ArrayList> doc_selector = new JComboBox(doc_names);
				
				Object[] fields = {"Select document that you would like to edit : ", doc_selector};
				
				int input = JOptionPane.showConfirmDialog(frame, fields, "New Document", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				
				if(input == JOptionPane.OK_OPTION)
				{
					 docname =  doc_selector.getSelectedItem().toString(); //get docname from selected item in the jcombobox
					 
					 lblNewLabel_1.setText("Currently editing: " + docname);
					 System.out.println(docname);
						try {
							textArea.setText("");			//remove previous content of textArea
							client.GetFileFromServer(docname); //
							
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						};
		
				
				}
				
				
			}
				
			
		 }
		 
		 
		 
				

		 protected class deletedoclistener implements ActionListener  {   //delete document listener 
			public void actionPerformed(ActionEvent e) {
				Object[] fields = {"Do you wish to delete this document? "};
				
				 int a= JOptionPane.showConfirmDialog(frame, fields, "delete", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				 if(a ==JOptionPane.OK_OPTION){  
					 try {
						client.suggest_delete(docname, client.username);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				      
				 }  
				
				
					
				}
			
			}
		 
	
				public static void suggest_delete_listener(String docname) {  //pops up upon a call from client. once concerned clients receive a delete notification this window pops up asking them for their confirmation
					Object[] fields = {"A user wants to delete this document. Do you wish to delete this document? "};
					
					 int a= JOptionPane.showConfirmDialog(frame, fields, "delete", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
					 if(a ==JOptionPane.OK_OPTION){  		
					 try {System.out.println(1);
								Client.delete_choice(1, docname);  //sends choice to server
								
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
					 	}
					     
					 }  
					
				public static void completely_deleted(String docname) {  //notifies all users concerned with doc that doc has been deleted from database 
					Object[] fields = {"The document: " + docname+ " was deleted from the database"};
					
					 int a= JOptionPane.showConfirmDialog(frame, fields, "delete confirmation", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		
					     
					 }  
					
				public static void completely_saved(String docname) {  //notifies all users concerned with doc that doc has been deleted from database 
					Object[] fields = {"The document "+docname+ " was saved on the database"};
					
					 int a= JOptionPane.showConfirmDialog(frame, fields, "save confirmation", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		
					     
					 } 	
				public static void completely_new(String docname) {  //notifies all users concerned with doc that doc has been deleted from database 
					Object[] fields = {"The document "+docname+ " was created on the database"};
					
					 int a= JOptionPane.showConfirmDialog(frame, fields, "save confirmation", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		
					     
					 } 
				
		//	public void call_suggest_delete( 	


		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		public void initialize() {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						Window2 window = new Window2(client);
						window.frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				
			}
				
		});
}
}
	
