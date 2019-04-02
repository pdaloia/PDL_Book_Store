package bean;

public class AccountBean {
	
	private String username;
	private String fname;
	private String lname;
	private String email;
	private String password;
	private Integer addressId;
	
	public AccountBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AccountBean(String username, String fname, String lname, String email, String password, Integer addressId) {
		super();
		this.username = username;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.password = password;
		this.addressId = addressId;
	
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Integer getAddressId() {
		return addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

}