package jk00687_project_com1028;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

/**
 * @author Joseph Kutler
 *
 */

public class RegisterTest {

	/**
	 * This test method is used to test if the Register class is able to
	 * successfully create objects of type Register when given correct parameters.
	 * 
	 */

	@Test
	public void TestUserRegisterCreation() {
		Register newUser = new Register("cyril123", "jom56", Role.PLAYER);
		Register newUser2 = new Register("ilikechairs", "joseph12", Role.MANAGER);
		Register newUser3 = new Register("cyril123", "jklyo9", Role.LEAGUE_DEVELOPER);
	}

	/**
	 * This test method tests whether an IllegalArgumentException is thrown when a
	 * null value for role is inputted when the other values are all correct
	 */
	@Test(expected = IllegalArgumentException.class)
	public void TestIncorrectRole() {
		Register newUser = new Register("cyril123", "jom56", null);
	}

	/**
	 * This test method tests whether an IllegalArgumentException is thrown when a
	 * null value for username is inputted when the other values are all correct
	 */
	@Test(expected = IllegalArgumentException.class)
	public void TestIncorrectUsername() {
		Register newUser = new Register(null, "joseph12", Role.PLAYER);
	}

	/**
	 * This test method tests whether an IllegalArgumentException is thrown when an
	 * invalid password is entered. The provided password of 'jo12' is invalid as it
	 * is smaller than 5 characters long
	 */
	@Test(expected = IllegalArgumentException.class)
	public void TestShortPassword() {
		Register newUser = new Register("cyril123", "jo12", Role.MANAGER);
	}

	/**
	 * This test method tests whether an IllegalArgumentException is thrown when an
	 * invalid password is entered. The provided password of 'j2opl3' is invalid as
	 * it is a true mix of letters and numbers rather than letters followed by
	 * numbers
	 * 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void TestInvalidPassword() {
		Register newUser = new Register("cyril123", "j2opl3", Role.MANAGER);
	}

	/**
	 * This test method tests whether an SQLException is thrown if an account
	 * attempts to get made in which the username already exists in the database
	 * 
	 * @throws SQLException An SQLException is thrown as the query being executed on
	 *                      the database may be syntactically incorrect in SQL, and
	 *                      so Java must defend against this
	 */
	@Test(expected = SQLException.class)
	public void AccountRegisterFailTest() throws SQLException {
		Register newUser = new Register("pillowmonster", "rwandacountry2", Role.PLAYER);
		Register newUser2 = new Register("pillowmonster", "rwanda7", Role.MANAGER);
		newUser.registerAccount("pillowmonster", "rwandacountry2", Role.PLAYER);
		newUser2.registerAccount("pillowmonster", "rwanda7", Role.MANAGER);
	}

}
