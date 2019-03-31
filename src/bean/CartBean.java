package bean;

public class CartBean {

	private String bid;
	private String title;
	private String quantity;
	private String price;
	private String userName;
	
	public CartBean(String userName, String bid, String title, String price, String quantity){
		this.bid = bid;
		this.userName = userName;
		this.title = title;
		this.quantity = quantity;
		this.price = price;
	}
	
	
	

	public String getUserName() {
		return userName;
	}
	

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getBid() {
		return bid;
	}
	

	public void setBid(String bid) {
		this.bid = bid;
	}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

}
