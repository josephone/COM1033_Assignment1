package jk00687_project_com1028;

import java.sql.*;

// INVALID CLASS, NOT NECESSARY BUT WILL DISCUSS THE USE OF THIS CLASS DURING EVALUATION AND 
// TESTING

public class Register {

	private String password = null;
	private String username = null;
	private Role role = null;
	private static final String passwordConstraint = "[a-zA-Z]+[0-9]+";

	public Register(String username, String password, Role role) throws IllegalArgumentException {
		super();

		if (password.length() < 5 || !password.matches(passwordConstraint)) {
			throw new IllegalArgumentException(
					"Password must be longer than 5 characters and must contain at least one number at the end of a string of characters");
		} else {
			this.password = password;
		}

		if (username != null) {
			this.username = username;
		} else {
			throw new IllegalArgumentException("A username must be chosen");
		}

		if (role != null) {
			this.role = role;
		} else {
			throw new IllegalArgumentException("A role must be chosen");
		}

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

	public void registerAccount(String username, String password, Role role)
			throws SQLException, ClassNotFoundException {

		if (checkExists(username) == true) {
			System.out.println("An account already exists with that username, please choose a different username");
			throw new SQLException();
		} else {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/users?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
					"root", "password");

					Statement stmt = conn.createStatement();) {

				String userInput = "insert into users values ('" + username + "', '" + password + "');";
				stmt.executeUpdate(userInput);

				if (role.PLAYER.equals(getRole())) {
					String playerInput = "insert into players(username, password, role) values ('" + username + "', '"
							+ password + "', '" + role + "');";
					System.out.println("The SQL statement is: " + playerInput + "\n");
					stmt.executeUpdate(playerInput);

				} else if (role.MANAGER.equals(getRole())) {
					String managerInput = "insert into managers(username, password, role) values ('" + username + "', '"
							+ password + "', '" + role + "');";
					System.out.println("The SQL statement is: " + managerInput + "\n");
					stmt.executeUpdate(managerInput);
				} else {
					String developerInput = "insert into leaguedevelopers(username, password, role) values ('"
							+ username + "', '" + password + "', '" + role + "');";
					System.out.println("The SQL statement is: " + developerInput + "\n");
					stmt.executeUpdate(developerInput);
				}

			}
		}

	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public Role getRole() {
		return role;
	}

}
