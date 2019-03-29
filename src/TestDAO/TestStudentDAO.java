package TestDAO;

import java.sql.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class TestStudentDAO {
	
	public static void main(String[] args) throws SQLException {
		try {
			DataSource ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
			Connection con = ds.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM PO");
			while(rs.next()) {
				String bid= rs.getString("FNAME");
				System.out.println("\t" + bid + "\t ");
			}//end while loop
			con.close();
			rs.close();
			stmt.close();
		} catch (NamingException e) {
			e.printStackTrace();
		}

	}
	
}