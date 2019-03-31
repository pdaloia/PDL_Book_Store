package bean;

public class ReviewBean {
	
	private String bid;
	private String review;
	private String email;
	private int rating;
	
	public ReviewBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReviewBean(String bid, String review, String email, int rating) {
		super();
		this.bid = bid;
		this.review = review;
		this.email = email;
		this.rating = rating;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

}