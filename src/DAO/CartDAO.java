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
import java.util.ArrayList;
import java.util.List;

public class CartDAO {

	DataSource ds;

	public CartDAO() throws ClassNotFoundException {
		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public List<CartBean> getCartByUser(String username) throws SQLException {
		List<CartBean> cartProductList = new ArrayList<>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement("SELECT * from cart where TRIM(username) like ?");
		p.setString(1, username.trim());
		ResultSet r = p.executeQuery();
		while (r.next()) {
			CartBean bean = new CartBean(r.getString("username"), r.getString("bid"), r.getString("title"),
					r.getInt("price"), r.getInt("quantity"));
			cartProductList.add(bean);

		}

		r.close();
		p.close();
		con.close();
		return cartProductList;

	}

	public CartBean addProductToCart(String bid, String title, String username, int price, int quantity) {

		try {
			CartBean b = new CartBean(username, bid, title, price, quantity);
			Connection con = this.ds.getConnection();
			PreparedStatement stmt = con
					.prepareStatement("INSERT INTO cart (username,bid,title,price,quantity) values(?,?,?,?,?)");
			stmt.setString(1, username);
			stmt.setString(2, bid);
			stmt.setString(3, title);
			stmt.setFloat(4, price);
			stmt.setInt(5, quantity);
			stmt.executeUpdate();
			stmt.close();
			con.close();
			return b;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void updateCartProduct(String bid, String username, float price, int quantity, boolean increament) {

		try {
			Connection con = this.ds.getConnection();

			if (increament) {

				CartBean bean = getCartProductByProductId(bid, username);

				PreparedStatement stmt = con
						.prepareStatement("UPDATE cart set price=?, quantity=? where bid=? and username=? ");
				stmt.setFloat(1, bean.getPrice() + price);
				stmt.setInt(2, quantity + bean.getQuantity());
				stmt.setString(3, bid);
				stmt.setString(4, username);
				stmt.executeUpdate();
				stmt.close();

			} else {
				PreparedStatement stmt = con
						.prepareStatement("UPDATE cart set price=?, quantity=? where bid=? and username=? ");
				stmt.setFloat(1, price);
				stmt.setInt(2, quantity);
				stmt.setString(3, bid);
				stmt.setString(4, username);
				stmt.executeUpdate();
				stmt.close();

			}

			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public CartBean getCartProductByProductId(String bid, String username) {
		try {

			Connection con = this.ds.getConnection();
			PreparedStatement p = con
					.prepareStatement("SELECT * from Cart where TRIM(username) like ? and TRIM(bid) like ?");
			p.setString(1, username.trim());
			p.setString(2, bid.trim());
			ResultSet r = p.executeQuery();
			CartBean bean = null;
			while (r.next()) {
				bean = new CartBean(r.getString("username"), r.getString("bid"), r.getString("title"),
						r.getInt("price"), r.getInt("quantity"));

			}

			r.close();
			p.close();
			con.close();
			return bean;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public void removeItem(String bid, String username) {

		try {
			Connection con = this.ds.getConnection();
			PreparedStatement stmt = con.prepareStatement("DELETE from cart where bid=? and username=? ");
			stmt.setString(1, bid);
			stmt.setString(2, username);
			stmt.executeUpdate();
			stmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public int countCartItem(String username) {

		try {
			Connection con = this.ds.getConnection();
			PreparedStatement stmt = con.prepareStatement("SELECT SUM(quantity) as cnt from cart where  username=? ");
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			int count = rs.getInt("cnt");
			stmt.close();
			con.close();
			return count;
		} catch (Exception e) {
			e.printStackTrace();

		}
		return 0;
	}

}
