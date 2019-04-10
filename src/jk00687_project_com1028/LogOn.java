package jk00687_project_com1028;

public class LogOn {

	private Register account = null;

	public LogOn(Register account) {
		super();
		if (account == null) {
			throw new IllegalArgumentException("Account cannot be null");
		} else {
			this.account = account;
		}

	}

	private boolean checkUsername() {

		return false;

	}

	private boolean checkPassword() {

		return false;

	}

	private boolean isSuccessful() {

		if (checkPassword() == true && checkUsername() == true) {
			return true;
		} else {
			return false;
		}

	}

}
