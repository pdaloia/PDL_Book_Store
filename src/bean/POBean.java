package bean;

public class POBean {
	
	private String id;
	private String lname;
	private String fname;
	private String status;
	private String address;
	
	public POBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public POBean(String id, String lname, String fname, String status, String address) {
		super();
		this.id = id;
		this.lname = lname;
		this.fname = fname;
		this.status = status;
		this.address = address;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
