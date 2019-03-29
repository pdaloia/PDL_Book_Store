package TestDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.AccountDAO;

/**
 * Servlet implementation class TestAccountDAO
 */
@WebServlet("/TestAccountDAO")
public class TestAccountDAO extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestAccountDAO() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		AccountDAO accountDAO = null;
		Map<String, bean.AccountBean> results = new HashMap<String, bean.AccountBean>();
		
		try {
			accountDAO = new AccountDAO();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Retrieving all accounts -----------------");
		
		try {
			results = accountDAO.retrieveAllAccounts();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(results);
		
		System.out.println("Retrieving phild account -----------------");
		
		bean.AccountBean result = new bean.AccountBean();
		try {
			if(accountDAO.retrieveAccountByUsername("phild", "1234") != null) {
				result = accountDAO.retrieveAccountByUsername("phild", "1234");
				System.out.println(result.getUsername());
			}
			else {
				System.out.println("user not found");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("Retrieving non registered account -----------------");
		
		try {
			if(accountDAO.retrieveAccountByUsername("dsfafef", "fewegrwf") != null) {
				result = accountDAO.retrieveAccountByUsername("dsfafef", "fewegrwf");
				System.out.println(result.getUsername());
			}
			else {
				System.out.println("user not found");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
