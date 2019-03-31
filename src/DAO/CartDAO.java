package DAO;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import bean.CartBean;

public class CartDAO {
	
	DataSource ds;

	public CartDAO() throws ClassNotFoundException {
		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public Map<String, CartBean> retrieveAllCarts() throws SQLException{
		String query = "select * from Cart";
		Map<String, CartBean> rc = new HashMap<String, CartBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while (r.next()){
			String userName = r.getString("USERNAME");
			String bid = r.getString("BID");
			String title = r.getString("TITLE");
			String price = r.getString("PRICE");
			String quantity = r.getString("QUANTITY");
			CartBean current;
			current = new CartBean(userName, bid, title, price,quantity);
			rc.put(bid, current);
			
		}
		
		r.close();
		p.close();
		con.close();
		
		return rc;
	}
	
}
