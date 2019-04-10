package jk00687_project_com1028;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

public class RegisterTest {
	
	// INVALID TEST CLASS, NOT BEING USED AS THE REGISTER CLASS IS OUTDATED BUT WILL DISCUSS
	// IN EVALUATION AND TESTING DOC

	@Test
	public void TestUserRegisterCreation() {
		Register newUser = new Register("cyril123", "jom56", Role.PLAYER);
		Register newUser2 = new Register("ilikechairs", "joseph12", Role.MANAGER);
		Register newUser3 = new Register("cyril123", "jklyo9", Role.LEAGUE_DEVELOPER);
	}

	@Test(expected = IllegalArgumentException.class)
	public void TestIncorrectRole() {
		Register newUser = new Register("cyril123", "jom56", null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void TestIncorrectUsername() {
		Register newUser = new Register(null, "joseph12", Role.PLAYER);
	}

	@Test(expected = IllegalArgumentException.class)
	public void TestShortPassword() {
		Register newUser = new Register("cyril123", "jo12", Role.MANAGER);
	}

	@Test(expected = IllegalArgumentException.class)
	public void TestInvalidPassword() {
		Register newUser = new Register("cyril123", "j2opl3", Role.MANAGER);
	}

	@Test(expected = SQLException.class)
	public void AccountRegisterFailTest() throws ClassNotFoundException, SQLException {
		Register newUser = new Register("pillowmonster", "rwandacountry2", Role.PLAYER);
		Register newUser2 = new Register("pillowmonster", "rwanda7", Role.MANAGER);
		newUser.registerAccount("pillowmonster", "rwandacountry2", Role.PLAYER);
		newUser2.registerAccount("pillowmonster", "rwanda7", Role.MANAGER);
	}

}
