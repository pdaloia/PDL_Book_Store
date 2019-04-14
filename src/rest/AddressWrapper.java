package rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class AddressWrapper {
	
	private String name;
	private String street;
	private String province; 
	private String zip; 
	private String country;
	
	public AddressWrapper(){
		
	}

	public AddressWrapper(String name, String street, String province, String zip, String country) {
		super();
		this.name = name;
		this.street = street;
		this.province = province;
		this.zip = zip;
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	} 
	
	
	
	
	
}
