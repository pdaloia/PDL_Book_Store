package model;

import java.util.HashMap;
import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import bean.VisitEventBean;
import bean.POBean;
import bean.AccountBean;
import bean.AddressBean;
import bean.BookBean;
import bean.POItemBean;
import bean.ReviewBean;
import DAO.AddressDAO;
import DAO.BookDAO;
import DAO.PODAO;
import DAO.POItemDAO;
import DAO.VisitEventDAO;
import DAO.ReviewDAO;
import DAO.AccountDAO;

import java.util.Map;


public class model {

private BookDAO bookDAO;
private ReviewDAO reviewDAO;
private AccountDAO accountDAO;

	public model(){
		
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
		
	}
	
	public Map<String, BookBean> retrieveBooksByCategory(String categoryToSearch) throws Exception{
		
		Map<String, BookBean> rv = new HashMap<String, BookBean>();
		rv = bookDAO.retrieveBooksByCategory(categoryToSearch);
		return rv;
		
	}
	
	public Map<String, BookBean> retrieveBooksBySearch(String searchString) throws Exception{
		
		Map<String, BookBean> rv = new HashMap<String, BookBean>();
		rv = bookDAO.retrieveBooksBySearch(searchString);
		return rv;
		
	}
	
	public Map<String, BookBean> retrieveBooksByBID(String searchBID) throws Exception{
		
		Map<String, BookBean> rv = new HashMap<String, BookBean>();
		rv = bookDAO.retrieveBooksByBID(searchBID);
		return rv;
		
	}
	
	public Map<String, ReviewBean> retrieveReviewsByBID(String searchBID) throws Exception{
		
		Map<String, ReviewBean> rv = new HashMap<String, ReviewBean>();
		rv = reviewDAO.retrieveReviewsByBid(searchBID);
		return rv;
		
	}
	
	public void addReview(String bid, String review, String email, int rating) throws Exception{
		
		reviewDAO.addReview(bid, review, email, rating);
		
	}
	
	public boolean checkForUser(String givenUsername) throws Exception{
		
		return accountDAO.checkForUser(givenUsername);
		
	}
	
	public AccountBean retrieveAccountByUsername(String givenUsername, String givenPassword) throws Exception{
		
		return accountDAO.retrieveAccountByUsername(givenUsername, givenPassword);
		
	}
	
	public void addNewAccount(String username, String fname, String lname, String email, String password) throws Exception{
		
		accountDAO.addNewAccount(username, fname, lname, email, password);
		
	}

}