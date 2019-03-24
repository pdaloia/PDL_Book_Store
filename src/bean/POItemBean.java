package bean;

public class POItemBean {
	
	private String id;
	private String bid;
	private String price;
	
	public POItemBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public POItemBean(String id, String bid, String price) {
		super();
		this.id = id;
		this.bid = bid;
		this.price = price;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

}
