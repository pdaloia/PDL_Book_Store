package DAO;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.ReviewBean;

public class ReviewDAO {
	
	private DataSource ds;
	
	public ReviewDAO() throws ClassNotFoundException{
		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public Map<String, ReviewBean> retrieveAllReviews() throws SQLException{
		String query = "select * from reviews";
		Map<String, ReviewBean> rv = new HashMap<String, ReviewBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while (r.next()){
			
			String bid = r.getString("BID");
			String review = r.getString("REVIEW");
			String email = r.getString("EMAIL");
			int rating = Integer.parseInt(r.getString("RATING"));
			ReviewBean current;
			current = new ReviewBean(bid, review, email, rating);
			rv.put(bid + email, current);
			
		}
		
		r.close();
		p.close();
		con.close();
		
		return rv;
	}
	
	public Map<String, ReviewBean> retrieveReviewsByBid(String bidToSearch) throws SQLException{
		String query = "select * from reviews where bid like '" + bidToSearch + "'";
		Map<String, ReviewBean> rv = new HashMap<String, ReviewBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while (r.next()){
			
			String bid = r.getString("BID");
			String review = r.getString("REVIEW");
			String email = r.getString("EMAIL");
			int rating = Integer.parseInt(r.getString("RATING"));
			ReviewBean current;
			current = new ReviewBean(bid, review, email, rating);
			rv.put(bid + email, current);
			
		}
		
		r.close();
		p.close();
		con.close();
		
		return rv;
	}
	
	public void addReview(String bid, String review, String email, int rating) throws SQLException{
		
		// Insert a new user into the Users table
		String query = "INSERT INTO Reviews (bid, review, email, rating) VALUES ('" + bid + "', '" + review + "', '" + email + "', " + rating + ")";
		Connection con = this.ds.getConnection();
		Statement st = con.createStatement();
		
		st.executeUpdate(query);
		st.close();
		
		con.close();
		
	}
	
}
