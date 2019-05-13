package jk00687_project_com1028;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JButton;

/**
 * @author Joseph Kutler
 *
 */
public class HomePage {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
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

		JButton btnRegisterNewAccount = new JButton("Register account");
		btnRegisterNewAccount.setBounds(33, 145, 145, 71);
		frame.getContentPane().add(btnRegisterNewAccount);
		btnRegisterNewAccount.addActionListener(new ActionListener() {

			/*
			 * Upon this button being clicked, the user is taken to the frame associated
			 * with the 'RegisterPage' class and the current frame is disposed of for
			 * efficiency reasons
			 */

			@Override
			public void actionPerformed(ActionEvent arg0) {
				RegisterPage.main();
				frame.dispose();

			}
		});

		JButton btnLogIntoAccount = new JButton("Log into account");
		btnLogIntoAccount.setBounds(247, 145, 151, 71);
		frame.getContentPane().add(btnLogIntoAccount);

		JButton fanButton = new JButton("Proceed as fan");
		fanButton.addActionListener(new ActionListener() {
			
			/*
			 * Upon this button being clicked, the user is taken to the frame associated
			 * with the 'FanPage' class and the current frame is disposed of for
			 * efficiency reasons
			 */
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				FanPage.main();
				frame.dispose();
			}
		});

		fanButton.setBounds(118, 11, 170, 98);
		frame.getContentPane().add(fanButton);
		btnLogIntoAccount.addActionListener(new ActionListener() {

			/*
			 * Upon this button being clicked, the user is taken to the frame associated
			 * with the 'LogOnPage' class and the current frame is disposed of for
			 * efficiency reasons
			 */
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				LogOnPage.main();
				frame.dispose();

			}

		});
	}

}
