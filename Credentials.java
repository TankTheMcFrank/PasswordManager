public class Credentials implements Comparable<Credentials> {
	//Fields
	private String category;
	private String username;
	private String password;

	//Constructor
	public Credentials(String categoryIn, String usernameIn, String passwordIn) {
		category = categoryIn;
		username = usernameIn;
		password = passwordIn;
	}

	//Methods

	/**
	 * getCategory() returns the category field.
	 *
	 * @return category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * setCategory() sets the category field. 
	 *
	 * @param categoryIn string to use to set the category field
	 */
	public void setCategory(String categoryIn) {
		category = categoryIn;
	}

	/**
	 * getUsername() returns the username field.
	 *
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * setUsername() sets the username field. 
	 *
	 * @param usernameIn string to use to set the username field
	 */
	public void setUsername(String usernameIn) {
		username = usernameIn;
	}

	/**
	 * getPassword() returns the password field.
	 *
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * setPassword() sets the password field. 
	 *
	 * @param passwordIn string to use to set the password field
	 */
	public void setPassword(String passwordIn) {
		password = passwordIn;
	}

	/**
	 * 
	 */
	public int compareTo(Credentials other) {
		String thisCat = this.getCategory().toLowerCase();
		String otherCat = other.getCategory().toLowerCase();
		String thisUser = this.getUsername().toLowerCase();
		String otherUser = other.getUsername().toLowerCase();
		String thisPassword = this.getPassword().toLowerCase();
		String otherPassword = other.getPassword().toLowerCase();

		if (thisCat.compareTo(otherCat) > 0) {
			return 1;
		}
		else if (thisCat.compareTo(otherCat) < 0) {
			return -1;
		}

		else if (thisUser.compareTo(otherUser) > 0) {
			return 1;
		}
		else if (thisUser.compareTo(otherUser) < 0) {
			return -1;
		}

		else if (thisPassword.compareTo(otherPassword) > 0) {
			return 1;
		}
		else if (thisPassword.compareTo(otherPassword) < 0) {
			return -1;
		}

		else {
			return 0;
		}
	}
}