package DAO;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

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
