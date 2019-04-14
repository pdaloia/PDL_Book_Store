package rest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import DAO.AddressDAO;
import DAO.PODAO;
import DAO.POItemDAO;
import bean.AddressBean;
import bean.POBean;


@Path("ops")	
public class OrderProcessService {
	
	private PODAO podao; 
	private POItemDAO pidao;
	private AddressDAO adao;
	
	private DataSource ds;
	
	public OrderProcessService() {
		try {
			podao = new PODAO();
			pidao = new POItemDAO();
			adao = new AddressDAO();
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (ClassNotFoundException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public ItemListWrapper getItemListByOrderId(int id) throws SQLException {
		
		List<ItemWrapper> items = new ArrayList<ItemWrapper>();
		String query = "SELECT BOOK.title, BOOK.price as itemPrice, POItem.price as totalPrice, PO.day "
				+ "FROM BOOK, POItem, PO "
				+ "WHERE POItem.bid = BOOK.bid AND PO.id = POItem.id "
				+ "AND POItem.id=?";
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		p.setInt(1, id);
		
		ResultSet r = p.executeQuery();
		
		while (r.next()) {
			String title = r.getString("title");
			int itemPrice = r.getInt("itemPrice");
			int totalPrice = r.getInt("totalPrice");
			int quantity = totalPrice / itemPrice; 
			String day = r.getString("day");
			String formattedDate = day.substring(4) + "-" + day.substring(2,4) + "-" + day.substring(0, 2) ;
			ItemWrapper item = new ItemWrapper(title, quantity, itemPrice, formattedDate);
			items.add(item);
		}
		
		r.close();
		p.close();
		con.close();
		
		return new ItemListWrapper(items);
	}
	
	
	
	// http://localhost:8080/PDL_Book_Store/rest/ops/orders
	// to get all purchase orders 
	@GET
	@Path("/orders/")
	@Produces(MediaType.APPLICATION_XML)	
	public List<POWrapper> getOrdersByPartNumber() throws SQLException{
		List<POBean> orders = podao.retrieveAllPOs();
		List<POWrapper> poList = new ArrayList<POWrapper>();
		
		for(POBean order: orders) {
			int addressId = order.getAddress();
			int poId = order.getId();
			AddressBean ab = adao.retrieveAddressById(addressId);
			
			
			// Create Address Wrapper 
			String fullName = order.getFname() + " " + order.getLname();
			AddressWrapper address = new AddressWrapper(fullName, ab.getStreet(),ab.getProvince(),ab.getZip(),ab.getCountry());
			
			// Get List of Items by Purchase Order ID
			ItemListWrapper itemList = getItemListByOrderId(poId);
		
			
			// Create POWrapper purchase order with each XML elements
			String day = order.getDay();
			String formattedDate = day.substring(4) + "-" + day.substring(2,4) + "-" + day.substring(0, 2) ;
			POWrapper po = new POWrapper(formattedDate, address, address, itemList);
			poList.add(po);
			
		}
		return poList;
		
	}
	
	
}
