package objects;

public class Self {
	
	private Self me = new Self();
	private static User user;
	/**
	 * @return the myuser
	 */
	private Self(){
		updateSelf();
	}
	
	public static User getInstance() {
		return user;
	}

	/**
	 * @param myuser the myuser to set
	 */
	public static void setUser(User myuser) {
		Self.user = myuser;
	}
	
	private void updateSelf(){
		//Check in saved preferences for an ID
		//	else generate an ID
		String temporaryID = "66";
		User updated = new User(temporaryID);
		this.user = updated;
	}

}
