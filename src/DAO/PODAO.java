package DAO;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
		String day = new SimpleDateFormat("MMddyyyy").format(new Date());
		Connection con = this.ds.getConnection();
		PreparedStatement p1 = con.prepareStatement(query);
		ResultSet r = p1.executeQuery();
		r.next();
		int poRow = r.getInt(1);	// get the number of rows in the table PO
		
		// 1. Third purchase order. Authorization failed 
		if((poRow)%3 == 0) { 
			msg = "Credit Card Authorization Failed.";
			System.out.println(msg);
			status = "DENIED";
			
			// update PO Table
			poRow++;
			p1.executeUpdate("insert into PO " + "VALUES (" + poRow + ", \'" + lname + "\', \'" + fname + "\', \'" + status + "\', " + address + ", \'" + day + "\')");
					
		}
		// 2. Authorization succeeded. Successfully place an order. 
		else {
			msg = "Order Successfully Completed.";
			System.out.println(msg);
			status = "ORDERED";
			
			
			// update PO Table
			poRow++;
			p1.executeUpdate("insert into PO " + "VALUES (" + poRow + ", \'" + lname + "\', \'" + fname + "\', \'" + status + "\', " + address + ", \'" + day + "\')");


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
	
	
	
	public List<POBean> retrieveAllPOs() throws SQLException{
		//String query = "select * from students where surname like '%" + namePrefix + "%' and credit_taken >= " + credit_taken;
		String query = "select * from po";
		List<POBean> rv = new ArrayList<POBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while (r.next()){
			
			int id = r.getInt("ID");
			String lname = r.getString("LNAME");
			String fname = r.getString("FNAME");
			String status = r.getString("STATUS");
			int address = r.getInt("ADDRESS");
			String day = new SimpleDateFormat("MMddyyyy").format(new Date());
			POBean current;
			current = new POBean(id, lname, fname, status, address, day);
			rv.add(current);
			
		}
		
		r.close();
		p.close();
		con.close();
		
		return rv;
	}

}
