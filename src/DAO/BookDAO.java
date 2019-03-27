package DAO;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.BookBean;

public class BookDAO {
	
	private DataSource ds;
	
	public BookDAO() throws ClassNotFoundException{
		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public Map<String, BookBean> retrieveAllBooks() throws SQLException{
		String query = "select * from book";
		Map<String, BookBean> rv = new HashMap<String, BookBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while (r.next()){
			
			String bid = r.getString("BID");
			String title = r.getString("TITLE");
			String price = r.getString("PRICE");
			String author = "";
			String category = r.getString("CATEGORY");
			BookBean current;
			current = new BookBean(bid, title, price, author, category);
			rv.put(bid, current);
			
		}
		
		r.close();
		p.close();
		con.close();
		
		return rv;
	}
	
	public Map<String, BookBean> retrieveBooksByCategory(String categoryToSearch) throws SQLException{
		
		String query = "select * from book where category like '" + categoryToSearch + "'";
		Map<String, BookBean> rv = new HashMap<String, BookBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while (r.next()){
			
			String bid = r.getString("BID");
			String title = r.getString("TITLE");
			String price = r.getString("PRICE");
			String author = "";
			String category = r.getString("CATEGORY");
			BookBean current;
			current = new BookBean(bid, title, price, author, category);
			rv.put(bid, current);
			
		}
		
		r.close();
		p.close();
		con.close();
		
		return rv;
		
	}
	
public Map<String, BookBean> retrieveBooksBySearch(String searchString) throws SQLException{
		
		String query = "select * from book where title like '%" + searchString + "%'";
		Map<String, BookBean> rv = new HashMap<String, BookBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while (r.next()){
			
			String bid = r.getString("BID");
			String title = r.getString("TITLE");
			String price = r.getString("PRICE");
			String author = "";
			String category = r.getString("CATEGORY");
			BookBean current;
			current = new BookBean(bid, title, price, author, category);
			rv.put(bid, current);
			
		}
		
		r.close();
		p.close();
		con.close();
		
		return rv;
		
	}

}
