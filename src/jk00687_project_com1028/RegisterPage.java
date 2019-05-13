package jk00687_project_com1028;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;

/**
 * @author Joseph Kutler
 *
 */

public class RegisterPage {

	private JFrame frame;
	private JTextField usernameChoice = null;
	private JTextField passwordChoice = null;
	private JTextField roleChoice = null;
	private boolean inputChoice = false;

	/**
	 * Launch the application.
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					RegisterPage window = new RegisterPage();
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
	public RegisterPage() {
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

		usernameChoice = new JTextField();
		usernameChoice.setBounds(115, 57, 86, 20);
		frame.getContentPane().add(usernameChoice);
		usernameChoice.setColumns(10);

		passwordChoice = new JTextField();
		passwordChoice.setBounds(115, 106, 86, 20);
		frame.getContentPane().add(passwordChoice);
		passwordChoice.setColumns(10);

		JLabel lblEnterUsername = new JLabel("Enter username:");
		lblEnterUsername.setBounds(10, 60, 101, 14);
		frame.getContentPane().add(lblEnterUsername);

		JLabel lblEnterPassword = new JLabel("Enter password:");
		lblEnterPassword.setBounds(10, 109, 94, 14);
		frame.getContentPane().add(lblEnterPassword);

		roleChoice = new JTextField();
		roleChoice.setBounds(115, 147, 86, 20);
		frame.getContentPane().add(roleChoice);
		roleChoice.setColumns(10);

		JLabel lblEnterRole = new JLabel("Enter role:");
		lblEnterRole.setBounds(10, 150, 101, 14);
		frame.getContentPane().add(lblEnterRole);

		JButton btnRegister = new JButton("Register");
		btnRegister.setBounds(69, 198, 89, 23);
		frame.getContentPane().add(btnRegister);
		btnRegister.addActionListener(new ActionListener() {

			/*
			 * Upon the 'Register' button being pressed, the values contained in the various
			 * JTextAreas will be converted into Strings that use an appropriate variable
			 * name for ease of identification. The 'registerAccount' method is then called
			 * which has the username, password and the role passed into it in order for the
			 * full functionalities to be completed. Once the registration has been
			 * successful, the user receives the appropriate success message and the frame
			 * immediately changes to the frame associated with the 'LogOnPage' class.
			 * 
			 */

			@Override
			public void actionPerformed(ActionEvent arg0) {

				String role = roleChoice.getText();
				String username = usernameChoice.getText();
				String password = passwordChoice.getText();

				try {
					registerAccount(username, password, role);
					JOptionPane.showMessageDialog(null, "Registration successful");
					LogOnPage.main();
					frame.dispose();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Registration unsuccessful");
					e.printStackTrace();
				}
			}

		});
	}

	/**
	 * This method takes in a parameter of 'username' as it must be able to access
	 * the name of the team that is being searched for. A connection is then created
	 * between the SQLite database 'users' and the Java program and a statement is
	 * initialised. The method then checks the database to see whether there is an
	 * entry in the database that has the same value as the username that is being
	 * searched for. If there is then the boolean value of true is returned, if
	 * there is not then the boolean value of false is returned.
	 * 
	 * @param username This is the String equivalent value of the JTextField
	 *                 'username'
	 * @return This method returns a boolean depending on whether the username
	 *         exists in the database or not. A value of true is returned if the
	 *         username does exist and a value of false is returned if the username
	 *         does not exist
	 * @throws SQLException An SQLException is thrown as the query being executed on
	 *                      the database may be syntactically incorrect in SQL, and
	 *                      so Java must defend against this
	 */

	public boolean checkExists(String username) throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:sqlite:users");
		Statement stmt = conn.createStatement();
		String SQL = "select * from users where username = '" + username + "';";

		ResultSet rset = stmt.executeQuery(SQL);

		if (rset.next()) {
			conn.close();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method allows a user to register a new account into the users database.
	 * The method first checks to see if the username already exists in the database
	 * as the user must register an account that has a unique username and there
	 * must not be duplicate rows. If the username already exists then the
	 * appropriate error message is displayed to the user and an SQLException is
	 * thrown. If there is no record of the user inputted username in the database
	 * then a connection is made to the SQLite database and a statement is
	 * initialised. An INSERT INTO SQL statement is then created which takes the
	 * three parameters provided by the user and inputs these values into the
	 * columns of the 'users' table
	 * 
	 * @param username This is the String equivalent value of the JTextField
	 *                 'username'
	 * @param password This is the String equivalent value of the JTextField
	 *                 'password'
	 * @param role     This is the String equivalent value of the JTextField 'role'
	 * @throws SQLException An SQLException is thrown as the query being executed on
	 *                      the database may be syntactically incorrect in SQL, and
	 *                      so Java must defend against this
	 */

	public void registerAccount(String username, String password, String role) throws SQLException {
		final String passwordConstraint = "[a-zA-Z]+[0-9]+";
		if (checkExists(username)) {
			JOptionPane.showMessageDialog(null,
					"An account already exists with that username, please choose a different username");
			throw new SQLException();
		} else {

			if (Arrays.stream((new String[] { role, username, password })).filter(x -> x.length() == 0).count() > 0) {
				JOptionPane.showMessageDialog(null, "Username, password or role entered incorrectly");
				throw new SQLException();
			} else if (password.length() < 5 || !password.matches(passwordConstraint)) {
				JOptionPane.showMessageDialog(null, "Password does not meet criteria");
				throw new SQLException();
			}
			try (Connection conn = DriverManager.getConnection("jdbc:sqlite:users");

					Statement stmt = conn.createStatement();) {
				if (role.equals("Player") || role.equals("Manager") || role.equals("League developer")) {

					String userInput = "insert into users values ('" + username + "', '" + password + "', '" + role
							+ "');";
					stmt.executeUpdate(userInput);

				} else {
					JOptionPane.showMessageDialog(null, "Invalid role choice");
					throw new SQLException();
				}
				conn.close();
			}

		}

	}
}
