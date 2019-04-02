package DAO;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.AccountBean;
import bean.AddressBean;
import bean.ReviewBean;

public class AddressDAO {
	
	private DataSource ds;
	
	public AddressDAO() throws ClassNotFoundException{
		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public AddressBean retrieveAddressById(int id) throws SQLException{
		String query = "select * from Address where id='" + id + "'";
		Map<Integer, AddressBean> rv = new HashMap<Integer, AddressBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while (r.next()){			
			String street = r.getString("street");
			String province = r.getString("province");
			String country = r.getString("country");
			String zip = r.getString("zip");
			String phone = r.getString("phone");
			AddressBean current;
			current = new AddressBean(id, street, province, country, zip, phone);
			rv.put(id, current);
		}
		
		r.close();
		p.close();
		con.close();
		
		return rv.get(id);
	}
	
	public Map<Integer, AddressBean> retrieveAllAddresses() throws SQLException{
		//String query = "select * from students where surname like '%" + namePrefix + "%' and credit_taken >= " + credit_taken;
		String query = "select * from address";
		Map<Integer, AddressBean> rv = new HashMap<Integer, AddressBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while (r.next()){
			
			int id = r.getInt("ID");
			String street = r.getString("STREET");
			String province = r.getString("PROVINCE");
			String country = r.getString("COUNTRY");
			String zip = r.getString("ZIP");
			String phone = r.getString("PHONE");
			AddressBean current;
			current = new AddressBean(id, street, province, country, zip, phone);
			rv.put(id, current);
			
		}
		
		r.close();
		p.close();
		con.close();
		
		return rv;
	}

}