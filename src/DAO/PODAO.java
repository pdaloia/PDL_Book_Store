package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.CartBean;
import bean.POBean;

public class PODAO {
	
	private DataSource ds;
	
	public PODAO() throws ClassNotFoundException{
		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public String placeOrder(String lname, String fname, int address, List<CartBean> books) throws SQLException{
		String msg = "";
		String status = "";
		String query = "select count(*) from po";
		Connection con = this.ds.getConnection();
		PreparedStatement p1 = con.prepareStatement(query);
		ResultSet r = p1.executeQuery();
		r.next();
		int poRow = r.getInt(1);	// get the number of rows in the table PO
		
		// 1. Third purchase order. Authorization failed 
		if((poRow++)%3 == 0) { 
			msg = "Credit Card Authorization Failed.";
			System.out.println(msg);
			status = "DENIED";
			
			// update PO Table
			p1.executeUpdate("insert into po " + "VALUES (" + poRow + ", \'" + lname + "\', \'" + fname + "\', \'" + status + "\', \'" + address + "\')");
		}
		// 2. Authorization succeeded. Successfully place an order. 
		else {
			msg = "Order Successfully Completed.";
			System.out.println(msg);
			status = "ORDERED";
			
			
			// update PO Table
			p1.executeUpdate("insert into po " + "VALUES (" + poRow + ", \'" + lname + "\', \'" + fname + "\', \'" + status + "\', \'" + address + "\')");

			// Add each book in the Cart to POITEM Table.
			for (CartBean element: books) {
				// Add
				p1.executeUpdate("insert into POItem " + "VALUES (" + poRow + ", \'" + element.getBid() + "\', " + element.getPrice() + ")");
				
			}
		}
		
		r.close();
		p1.close();
		con.close();
		
		return msg; 
	}
	
	
	
	public Map<String, POBean> retrieveAllPOs() throws SQLException{
		//String query = "select * from students where surname like '%" + namePrefix + "%' and credit_taken >= " + credit_taken;
		String query = "select * from po";
		Map<String, POBean> rv = new HashMap<String, POBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while (r.next()){
			
			String id = r.getString("ID");
			String lname = r.getString("LNAME");
			String fname = r.getString("FNAME");
			String status = r.getString("STATUS");
			String address = r.getString("ADDRESS");
			POBean current;
			current = new POBean(id, lname, fname, status, address);
			rv.put(id, current);
			
		}
		
		r.close();
		p.close();
		con.close();
		
		return rv;
	}

}