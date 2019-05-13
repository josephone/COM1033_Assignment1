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
		Connection conn = DriverManager.getConnection("jdbc:sqlite:users");
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
	 * @param username
	 * @param password
	 * @param role
	 * @throws SQLException
	 */

	public void registerAccount(String username, String password, Role role) throws SQLException {

		if (checkExists(username) == true) {
			throw new SQLException();
		} else {
			try (Connection conn = DriverManager.getConnection("jdbc:sqlite:users");

					Statement stmt = conn.createStatement();) {

				String userInput = "insert into users(username, password, role) values ('" + username + "', '"
						+ password + "', '" + role + "');";
				stmt.executeUpdate(userInput);

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
