package jk00687_project_com1028;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JPasswordField;

/**
 * @author Joseph Kutler
 *
 */

public class LogOnPage {

	private JFrame frame;
	private JTextField usernameEnter = null;
	private int count = 0;
	private JPasswordField passwordField = null;

	/**
	 * Launch the application.
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			@Override
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
	 * 
	 * @throws SQLException
	 */
	public LogOnPage() throws SQLException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws SQLException
	 */
	private void initialize() throws SQLException {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		usernameEnter = new JTextField();
		usernameEnter.setBounds(126, 51, 86, 20);
		frame.getContentPane().add(usernameEnter);
		usernameEnter.setColumns(10);

		JLabel lblEnterUsername = new JLabel("Enter username:");
		lblEnterUsername.setBounds(10, 54, 106, 14);
		frame.getContentPane().add(lblEnterUsername);

		JLabel lblEnterPassword = new JLabel("Enter password:");
		lblEnterPassword.setBounds(10, 110, 106, 14);
		frame.getContentPane().add(lblEnterPassword);

		JButton btnLogIn = new JButton("Log in");
		btnLogIn.setBounds(86, 157, 89, 23);
		frame.getContentPane().add(btnLogIn);

		passwordField = new JPasswordField();
		passwordField.setBounds(126, 107, 86, 20);
		frame.getContentPane().add(passwordField);

		btnLogIn.addActionListener(new ActionListener() {

			/*
			 * Upon the 'Log in' button being pressed the following code will be executed.
			 * The values contained within the JTextBoxes will be all converted into Strings
			 * with the appropriate variable name so that they can be passed as a parameter
			 * into the 'checkExists' method. If the result of this method returns a value
			 * of true this means that they have entered correct login information and a
			 * switch-case scenario occurs. This calls the method 'checkRole' and depending
			 * on the String value returned, a different frame relating to a different class
			 * is called upon and the current frame is disposed of for efficiency reasons.
			 * If the 'checkExists' method returns a value of false, this means that the
			 * user has entered incorrect login information and must reattempt their login.
			 * If this occurs three times in a row, the program closes for security reasons.
			 *
			 */

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String username = usernameEnter.getText();
				String password = new String(passwordField.getPassword());

				try {
					if (checkExists(username, password)) {
						switch (checkRole(username)) {
						case "League developer":
							LeagueDeveloperPage.main();
							frame.dispose();
							break;
						case "Manager":
							ManagerPage.main();
							frame.dispose();
							break;
						case "Player":
							PlayerPage.main();
							frame.dispose();
							break;

						default:
							throw new SQLException();
						}

					} else {
						if (count >= 2) {
							JOptionPane.showMessageDialog(null,
									"Login details entered incorrectly too many times, please try again later");
							Thread.sleep(400);
							Runtime.getRuntime().exit(1);
						} else {
							count++;
							JOptionPane.showMessageDialog(null, "Incorrect login details entered");
						}

					}
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Incorrect login details entered");
					e.printStackTrace();
				} catch (InterruptedException e) {
					JOptionPane.showMessageDialog(null, "Error");
					e.printStackTrace();
				}

			}

		});
	}

	/**
	 * This method is used to check whether the information entered by the user into
	 * the username and password fields is a valid username and password combination
	 * in the 'users' table. I have implemented PreparedStatements here in order to
	 * prevent SQL injection from occurring. If the username-password combination is
	 * valid, a value of true is returned and if not then a value of false is
	 * returned
	 * 
	 * @param username This is the String equivalent value of the JTextField
	 *                 'usernameEnter'
	 * @param password This is the String equivalent value of the JTextField
	 *                 'passwordEnter'
	 * @return This method returns a boolean depending on whether the
	 *         username-password combination exists in the database or not. A value
	 *         of true is returned if this combination does exist and a value of
	 *         false is returned if this combination does not exist
	 * @throws SQLException An SQLException is thrown as the query being executed on
	 *                      the database may be syntactically incorrect in SQL, and
	 *                      so Java must defend against this
	 */

	public boolean checkExists(String username, String password) throws SQLException {

		try (Connection conn = DriverManager.getConnection("jdbc:sqlite:users");
				Statement stmt = conn.createStatement()) {
			String SQL = "select * from users where username = ? and password = ? ";
			PreparedStatement preparedStatement = conn.prepareStatement(SQL);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);

			ResultSet rset = preparedStatement.executeQuery();

			if (rset.next()) {
				conn.close();
				return true;
			} else {
				return false;
			}
		}

	}

	/**
	 * This method checks the role that is associated with the username that has
	 * been entered by the user and returns a String containing the values of the
	 * role. This will be either 'Player', 'Manager' or 'League developer'.
	 * 
	 * @param username This is the String equivalent value of the JTextField
	 *                 'usernameEnter'
	 * @returns a String of either 'Player', 'Manager' or 'League developer'
	 *          depending on what value the field in the table contains
	 * @throws SQLException An SQLException is thrown as the query being executed on
	 *                      the database may be syntactically incorrect in SQL, and
	 *                      so Java must defend against this
	 */
	public String checkRole(String username) throws SQLException {
		try (Connection conn = DriverManager.getConnection("jdbc:sqlite:users");
				Statement stmt = conn.createStatement()) {

			String SQL = "select role from users where username = '" + username + "';";

			ResultSet result = stmt.executeQuery(SQL);

			result.next();

			String resultRole = result.getString(1).toString();
			result.close();
			conn.close();
			return resultRole;
		}
	}
}
