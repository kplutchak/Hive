package objects;

public class Self {
	
	private static User user;
	/**
	 * @return the myuser
	 */
	public Self(){
		updateSelf();
	}
	
	public static User getUser() {
		return user;
	}

	/**
	 * @param myuser the myuser to set
	 */
	public static void setUser(User myuser) {
		Self.user = myuser;
	}
	
	public static void updateSelf(){
		//Check in saved preferences for an ID
		//	else generate an ID
		String temporaryID = "66";
		User updated = new User(temporaryID);
		Self.user = updated;
	}

}
