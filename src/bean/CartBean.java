package bean;

public class CartBean {

    private String bid;
    private String title;
    private int quantity;
    private int price;
    private String userName;

    public CartBean(String userName, String bid, String title, int price, int quantity) {
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
