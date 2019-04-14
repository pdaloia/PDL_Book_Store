package rest;

import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public class ItemWrapper {
	private String bookName;
	private int quantity;
	private int price;
	private String shipDate; 
	
	public ItemWrapper() {
		
	}
	
	public ItemWrapper(String bookName, int quantity, int price, String shipDate) {
		super();
		this.bookName = bookName;
		this.quantity = quantity;
		this.price = price;
		this.shipDate = shipDate;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getShipDate() {
		return shipDate;
	}

	public void setShipDate(String shipDate) {
		this.shipDate = shipDate;
	}
	
}
