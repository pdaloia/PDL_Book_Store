package bean;

public class AddressBean {
	
	private String id;
	private String street;
	private String province;
	private String city;
	private String country;
	private String zip;
	private String phone;
	
	public AddressBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AddressBean(String id, String street, String province, String city,  String country, String zip, String phone) {
		super();
		this.id = id;
		this.street = street;
		this.province = province;
		this.city = city; 
		this.country = country;
		this.zip = zip;
		this.phone = phone;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
