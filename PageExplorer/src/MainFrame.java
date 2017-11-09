
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import Implementations.*;

public class MainFrame {

	private JFrame frame;
	private JTextField inputField;
	private static Navigator _navigator;
	
	/**
	 * Launch the application.
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		
		_navigator = new Navigator(new MySqlDataBaseConnector(), new UrlWebConnector());
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 710, 501);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		inputField = new JTextField();
		inputField.setBounds(31, 39, 116, 22);
		frame.getContentPane().add(inputField);
		inputField.setColumns(10);
			
		JList list = new JList();
		list.setBounds(68, 109, 1, 1);
		frame.getContentPane().add(list);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(31, 98, 311, 326);
		
		frame.getContentPane().add(textPane);

		JButton btnNavigate = new JButton("Navigate");
		btnNavigate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) 
			{
				String x;
				try 
				{
					x = _navigator.Naviagte(inputField.getText());
					textPane.setText(x);
				} catch (Exception e) 
				{
					textPane.setText("An error occurred");
				}
				
				
			}
		});
		btnNavigate.setBounds(167, 38, 97, 25);
		frame.getContentPane().add(btnNavigate);
		
	}
}
