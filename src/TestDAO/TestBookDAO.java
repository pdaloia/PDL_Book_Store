package TestDAO;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.BookDAO;

/**
 * Servlet implementation class TestBookDAO
 */
@WebServlet("/TestBookDAO")
public class TestBookDAO extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestBookDAO() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		BookDAO bookDAO = null;
		Map<String, bean.BookBean> results = new HashMap<String, bean.BookBean>();
		
		try {
			bookDAO = new BookDAO();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Retrieving all books -----------------");
		
		try {
			results = bookDAO.retrieveAllBooks();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(results);
		
		System.out.println("Retrieving books by category -----------------");
		
		try {
			results = bookDAO.retrieveBooksByCategory("Engineering");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(results);
		
		System.out.println("Retrieving books by title -----------------");
		
		try {
			results = bookDAO.retrieveBooksBySearch("Physics");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(results);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
