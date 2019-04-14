package rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import bean.AddressBean;


@XmlRootElement(name="purchaseOrder")
@XmlAccessorType(XmlAccessType.FIELD)
public class POWrapper {

	@XmlAttribute(name="orderDate")
	private String date; 
	
	@XmlElement(name="shipTo")
	private AddressWrapper shipAddress;
	
	@XmlElement(name="billTo")
	private AddressWrapper billAddress;
	
	private ItemListWrapper items;
	
	
	public POWrapper() {
	}
	
	public POWrapper(String date, AddressWrapper shipAddress, AddressWrapper billAddress, ItemListWrapper items) {
		super();
		this.date = date;
		this.shipAddress = shipAddress;
		this.billAddress = billAddress;
		this.items = items;
	}
	
	
	
}
