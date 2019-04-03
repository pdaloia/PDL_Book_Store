package model;

import java.util.HashMap;
import bean.AccountBean;
import bean.AddressBean;
import bean.BookBean;
import bean.CartBean;
import bean.ReviewBean;
import DAO.BookDAO;
import DAO.CartDAO;
import DAO.PODAO;
import DAO.ReviewDAO;
import DAO.AccountDAO;
import DAO.AddressDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.util.Map;

public class model {

	private AddressDAO addressDAO;
    private BookDAO bookDAO;
    private ReviewDAO reviewDAO;
    private AccountDAO accountDAO;
    private CartDAO cartDAO;
    private PODAO poDAO;

    public model() {

        try {
        	addressDAO = new AddressDAO();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            bookDAO = new BookDAO();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            reviewDAO = new ReviewDAO();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            accountDAO = new AccountDAO();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
        	poDAO = new PODAO();

        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            cartDAO = new CartDAO();

        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
      

    }
    
    /****************** Address Module from here *****************/
    
    public AddressBean retrieveAddressById(int id) throws Exception {
    	 return addressDAO.retrieveAddressById(id);
    }
    
    public int addNewAddress(String street, String province, String country, String zip, String phone) throws SQLException{
    	return addressDAO.addNewAddress(street, province, country, zip, phone);

    }
    

    
    /****************** Books Module from here *****************/

    public Map<String, BookBean> retrieveBooksByCategory(String categoryToSearch) throws Exception {

        Map<String, BookBean> rv = new HashMap<String, BookBean>();
        rv = bookDAO.retrieveBooksByCategory(categoryToSearch);
        return rv;

    }

    public Map<String, BookBean> retrieveBooksBySearch(String searchString) throws Exception {

        Map<String, BookBean> rv = new HashMap<String, BookBean>();
        rv = bookDAO.retrieveBooksBySearch(searchString);
        return rv;

    }

    public Map<String, BookBean> retrieveBooksByBID(String searchBID) throws Exception {

        Map<String, BookBean> rv = new HashMap<String, BookBean>();
        rv = bookDAO.retrieveBooksByBID(searchBID);
        return rv;

    }
    
    /****************** Review Module from here *****************/

    public Map<String, ReviewBean> retrieveReviewsByBID(String searchBID) throws Exception {

        Map<String, ReviewBean> rv = new HashMap<String, ReviewBean>();
        rv = reviewDAO.retrieveReviewsByBid(searchBID);
        return rv;

    }

    public void addReview(String bid, String review, String email, int rating) throws Exception {

        reviewDAO.addReview(bid, review, email, rating);

    }
    
    /****************** Account Module from here *****************/
    

    public boolean checkForUser(String givenUsername) throws Exception {

        return accountDAO.checkForUser(givenUsername);

    }

    public AccountBean retrieveAccountByUsername(String givenUsername, String givenPassword) throws Exception {

        return accountDAO.retrieveAccountByUsername(givenUsername, givenPassword);

    }

    public void addNewAccount(String username, String fname, String lname, String email, String password, Integer addressId) throws Exception{
		
		accountDAO.addNewAccount(username, fname, lname, email, password, addressId);
		
	}
    
 
    
    /****************** Cart Module from here *****************/
    
    public List<CartBean> retrieveUserCart(String username) {
            try{
              return  cartDAO.getCartByUser(username);
            }catch(SQLException e){
               e.printStackTrace();
            }
            return new ArrayList<>();
    }
    
    public void addProductToCart(String bid,String title, String username,int price, int quantity ){
        CartBean b = this.getCartProductByProductId(bid, username);
        if(null==b){
            cartDAO.addProductToCart(bid, title, username, price, quantity);
        }else{
            cartDAO.updateCartProduct(bid, username, price, quantity,true);
        }
        
        
    }
    
    public CartBean getCartProductByProductId(String bid,String username){
        return cartDAO.getCartProductByProductId(bid,username);
    }
    
    public void removeItemFromCart(String bid,String username){
         cartDAO.removeItem(bid,username);
    }
    
    public void updateCartProduct(String bid, String username,float price,int quantity){
       cartDAO.updateCartProduct(bid, username, price, quantity,false);
    }
    
    public int countCartItem(String username){
        
       return cartDAO.countCartItem(username);
    }
    
    
    
    /****************** Payment Module from here *****************/
    
    public String placeOrder(String lname, String fname, int address, List<CartBean> books) throws Exception
    {
    	String msg = poDAO.placeOrder(lname, fname, address, books);
    	return msg;
    }
    
    
}