package objects;

public class User {
	
	private String uID;
	
	public User (String uID){
		setuID(uID);
	}

	/**
	 * @return the uID
	 */
	public String getuID() {
		return uID;
	}

	/**
	 * @param uID the uID to set
	 */
	public void setuID(String uID) {
		this.uID = uID;
	}
	
	

}
