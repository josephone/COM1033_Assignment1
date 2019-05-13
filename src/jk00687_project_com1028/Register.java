package jk00687_project_com1028;

import java.sql.*;

/**
 * @author Joseph Kutler
 *
 */

public class Register {

	private String password = null;
	private String username = null;
	private Role role = null;

	/*
	 * The constant 'passwordConstraint' defines a regular expression which declares
	 * that the string must be one or more upper or lower case letter followed by
	 * one or more number
	 */

	private static final String passwordConstraint = "[a-zA-Z]+[0-9]+";

	/**
	 * Constructor for the Register class
	 * 
	 * @param username This parameter cannot be equal to null and defines the
	 *                 username that will be inputted into the database
	 * @param password This parameter must be greater than 5 characters and it must
	 *                 match the 'passwordConstraint' regular expression. This
	 *                 defines the password that will accompany the username when
	 *                 being inputted into the database
	 * @param role     This parameter cannot be null and defines the role that will
	 *                 be associated with the username
	 * @throws IllegalArgumentException An IllegalArgumentException has been thrown
	 *                                  here as it could be possible that the user
	 *                                  does not enter any information when required
	 */

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

	/**
	 * This method allows a user to register a new account into the users database
	 * 
	 * @param username
	 * @param password
	 * @param role
	 * @throws SQLException
	 */
	
	public void registerAccount(String username, String password, Role role) throws SQLException {

		if (checkExists(username) == true) {
			System.out.println("An account already exists with that username, please choose a different username");
			throw new SQLException();
		} else {
			try (Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/users?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
					"root", "password");

					Statement stmt = conn.createStatement();) {

				String userInput = "insert into users values ('" + username + "', '" + password + "');";
				stmt.executeUpdate(userInput);

				if (Role.PLAYER.equals(getRole())) {
					String playerInput = "insert into players(username, password, role) values ('" + username + "', '"
							+ password + "', '" + role + "');";
					System.out.println("The SQL statement is: " + playerInput + "\n");
					stmt.executeUpdate(playerInput);

				} else if (Role.MANAGER.equals(getRole())) {
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

	/**
	 * @returns the role enumeration that has been chosen
	 */

	public Role getRole() {
		return role;
	}

}
