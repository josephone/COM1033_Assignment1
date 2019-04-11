package jk00687_project_com1028;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JButton;

public class HomePage {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomePage window = new HomePage();
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
	public HomePage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnRegisterNewAccount = new JButton("Register new account");
		btnRegisterNewAccount.setBounds(31, 88, 145, 71);
		frame.getContentPane().add(btnRegisterNewAccount);
		btnRegisterNewAccount.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				RegisterPage.main();
				
			}
			
			
		});
		
		JButton btnLogIntoAccount = new JButton("Log into account");
		btnLogIntoAccount.setBounds(249, 88, 151, 71);
		frame.getContentPane().add(btnLogIntoAccount);
		btnLogIntoAccount.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				LogOnPage.main();
				
			}
			
			
		});
	}

}