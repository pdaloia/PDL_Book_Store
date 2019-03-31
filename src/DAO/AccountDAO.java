package DAO;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.AccountBean;

public class AccountDAO {
	
	private DataSource ds;
	
	public AccountDAO() throws ClassNotFoundException{
		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public Map<String, AccountBean> retrieveAllAccounts() throws SQLException{
		String query = "select * from accounts";
		Map<String, AccountBean> rv = new HashMap<String, AccountBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while (r.next()){
			
			String username = r.getString("USERNAME");
			String fname = r.getString("FNAME");
			String lname = r.getString("LNAME");
			String email = r.getString("EMAIL");
			String password = r.getString("PASSWORD");
			AccountBean current;
			current = new AccountBean(username, fname, lname, email, password);
			rv.put(username, current);
			
		}
		
		r.close();
		p.close();
		con.close();
		
		return rv;
	}
	
	public boolean checkForUser(String givenUsername) throws SQLException{
		
		boolean exists;
		
		String query = "select * from accounts where username='" + givenUsername + "'";

		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		int size = 0;
		
		while(r.next()) {
			size++;
		}
		
		if(size > 0) {
			exists = true;
		}
		else {
			exists = false;
		}
		
		r.close();
		p.close();
		con.close();
		
		return exists;
		
	}
	
	public AccountBean retrieveAccountByUsername(String givenUsername, String givenPassword) throws SQLException{
		String query = "select * from accounts where username='" + givenUsername + "' and password='" + givenPassword + "'";
		Map<String, AccountBean> rv = new HashMap<String, AccountBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while (r.next()){
			
			String username = r.getString("USERNAME");
			String fname = r.getString("FNAME");
			String lname = r.getString("LNAME");
			String email = r.getString("EMAIL");
			String password = r.getString("PASSWORD");
			AccountBean current;
			current = new AccountBean(username, fname, lname, email, password);
			rv.put(username, current);
			
		}
		
		r.close();
		p.close();
		con.close();
		
		return rv.get(givenUsername);
	}
	
	public void addNewAccount(String username, String fname, String lname, String email, String password) throws SQLException{
		
		String query = "INSERT INTO accounts (username, fname, lname, email, password) VALUES ('" + username + "', '" + fname + "', '" + lname + "', '" + email + "', '" + password + "')";
		Connection con = this.ds.getConnection();
		Statement st = con.createStatement();
		
		st.executeUpdate(query);
		st.close();
		
		con.close();
		
	}


}