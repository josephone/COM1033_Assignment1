package jk00687_project_com1028;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;

public class LogOnPage {

	private JFrame frame;
	private JTextField usernameEnter;
	private JTextField passwordEnter;

	/**
	 * Launch the application.
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogOnPage window = new LogOnPage();
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
	public LogOnPage() {
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

		usernameEnter = new JTextField();
		usernameEnter.setBounds(109, 51, 86, 20);
		frame.getContentPane().add(usernameEnter);
		usernameEnter.setColumns(10);

		passwordEnter = new JTextField();
		passwordEnter.setBounds(109, 107, 86, 20);
		frame.getContentPane().add(passwordEnter);
		passwordEnter.setColumns(10);

		JLabel lblEnterUsername = new JLabel("Enter username:");
		lblEnterUsername.setBounds(10, 54, 89, 14);
		frame.getContentPane().add(lblEnterUsername);

		JLabel lblEnterPassword = new JLabel("Enter password:");
		lblEnterPassword.setBounds(10, 110, 89, 14);
		frame.getContentPane().add(lblEnterPassword);

		JButton btnLogIn = new JButton("Log in");
		btnLogIn.setBounds(86, 157, 89, 23);
		frame.getContentPane().add(btnLogIn);
		btnLogIn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String username = usernameEnter.getText();
				String password = passwordEnter.getText();

				try {
					if (checkExists(username, password)) {
						switch (checkRole(username)) {
						case "League developer":
							LeagueDeveloperPage.main();
							break;
						case "Manager":
							ManagerPage.main();
							break;
						case "Player":
							PlayerPage.main();
							break;
						
						default:
							throw new SQLException();
						}

					} else {
						JOptionPane.showMessageDialog(null, "Incorrect login details entered");
					}
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Incorrect login details entered");
					e.printStackTrace();
				}

			}

		});
	}

	public boolean checkExists(String username, String password) throws SQLException {
		Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/users?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
				"root", "password");
		Statement stmt = conn.createStatement();
		String SQL = "select * from users where username = '" + username + "' and password = '" + password + "';";

		ResultSet rset = stmt.executeQuery(SQL);

		if (rset.next()) {
			return true;
		} else {
			return false;
		}
	}

	public String checkRole(String username) throws SQLException {

		Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/users?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
				"root", "password");
		String SQL = "select role from users where username = '" + username + "';";
		Statement stmt = conn.createStatement();

		ResultSet result = stmt.executeQuery(SQL);
		result.beforeFirst();
		result.next();
		
		return result.getString(1).toString();
		
	}

}
