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

public class RegisterPage {

	private JFrame frame;
	private JTextField usernameChoice;
	private JTextField passwordChoice;
	private JTextField roleChoice;
	private boolean inputChoice = false;

	/**
	 * Launch the application.
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
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

			@Override
			public void actionPerformed(ActionEvent arg0) {

				final String passwordConstraint = "[a-zA-Z]+[0-9]+";

				String role = roleChoice.getText();
				String username = usernameChoice.getText();
				String password = passwordChoice.getText();

				if (role == null || username == null || password == null) {
					inputChoice = false;
				} else if (password.length() < 5 || !password.matches(passwordConstraint)) {
					inputChoice = false;
				} else {
					inputChoice = true;
				}

				try {
					if (inputChoice = true) {
						registerAccount(username, password, role);
						JOptionPane.showMessageDialog(null, "Registration successful");
					} else {
						JOptionPane.showMessageDialog(null, "Username, password or role not entered correctly");
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		});
	}

	public boolean checkExists(String username) throws SQLException {
		Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/users?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
				"root", "password");
		Statement stmt = conn.createStatement();
		String SQL = "select * from users where username = '" + username + "';";

		ResultSet rset = stmt.executeQuery(SQL);

		if (rset.next()) {
			return true;
		} else {
			return false;
		}
	}

	public void registerAccount(String username, String password, String role)
			throws SQLException, ClassNotFoundException {

		if (checkExists(username) == true) {
			JOptionPane.showMessageDialog(null,
					"An account already exists with that username, please choose a different username");
			throw new SQLException();
		} else {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/users?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
					"root", "password");

					Statement stmt = conn.createStatement();) {

				if (role.equals("Player")) {
					String playerInput = "insert into players(username, password, role) values ('" + username + "', '"
							+ password + "', '" + role + "');";
					System.out.println("The SQL statement is: " + playerInput + "\n");
					stmt.executeUpdate(playerInput);
					String userInput = "insert into users values ('" + username + "', '" + password + "');";
					stmt.executeUpdate(userInput);

				} else if (role.equals("Manager")) {
					String managerInput = "insert into managers(username, password, role) values ('" + username + "', '"
							+ password + "', '" + role + "');";
					System.out.println("The SQL statement is: " + managerInput + "\n");
					stmt.executeUpdate(managerInput);
					String userInput = "insert into users values ('" + username + "', '" + password + "');";
					stmt.executeUpdate(userInput);

				} else if (role.equals("League developer")) {
					String developerInput = "insert into leaguedevelopers(username, password, role) values ('"
							+ username + "', '" + password + "', '" + role + "');";
					System.out.println("The SQL statement is: " + developerInput + "\n");
					stmt.executeUpdate(developerInput);
					String userInput = "insert into users values ('" + username + "', '" + password + "');";
					stmt.executeUpdate(userInput);

				} else {
					JOptionPane.showMessageDialog(null, "Invalid role choice");
					throw new SQLException();
				}

			}
		}

	}
}
