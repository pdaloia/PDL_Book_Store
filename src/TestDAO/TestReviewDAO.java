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

import DAO.ReviewDAO;

/**
 * Servlet implementation class TestReviewDAO
 */
@WebServlet("/TestReviewDAO")
public class TestReviewDAO extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestReviewDAO() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ReviewDAO reviewDAO = null;
		Map<String, bean.ReviewBean> results = new HashMap<String, bean.ReviewBean>();
		
		try {
			reviewDAO = new ReviewDAO();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Retrieving all reviews -----------------");
		
		try {
			results = reviewDAO.retrieveAllReviews();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(results);
		
		System.out.println("Retrieving reviews by bid -----------------");
		
		try {
			results = reviewDAO.retrieveReviewsByBid("b001");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(results);
		
		System.out.println("adding review");
		try {
			reviewDAO.addReview("b001", "2", "3", 4);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
