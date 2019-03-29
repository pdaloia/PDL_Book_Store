package crtl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.AccountBean;
import bean.BookBean;
import bean.ReviewBean;
import model.model;

/**
 * Servlet implementation class Start
 */
@WebServlet({"/Start", "/DisplayBooksPage", "/BookDetails", "/Reviews", "/Login", "/Logout"})
public class Start extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String target;
	private model model;
	private static final String MODEL = "model";
	private static final String LIST_OF_BOOKS = "listOfBooks";
	private static final String LIST_OF_REVIEWS = "listOfReviews";
	private static final String LOGIN_ERROR_MESSAGE = "loginErrorMessage";
	
	//account details
	private AccountBean accountBean;
	private static final String ACCOUNT_BEAN = "accountBean";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Start() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    /**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		model = new model();
		getServletContext().setAttribute(MODEL, model);
		System.out.println("init");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("Servlet started");
		
		//get values from URL
		String url = this.getServletContext().getContextPath();
		String URI = request.getRequestURI();
		String queryString = request.getQueryString();
		
		//Main page
		if(URI.contains("Start") && request.getParameter("textSearchButton") == null) {
			target = "/BookStoreMainPage.jspx";
			request.getRequestDispatcher(target).forward(request, response);
		}
		/*
		 * Display a list/selection of books page
		 * Go to this page when a list of books needs to be shown to the client
		 * Such as:
		 * 1) Display books by category
		 * 2) Display books by 
	   	 */
		else if(URI.contains("DisplayBooksPage") || request.getParameter("textSearchButton") != null) {
			Map<String, BookBean> currentList = new HashMap<String, BookBean>();
			target = "/DisplayBooksPage.jspx";
			if(request.getParameter("category") != null) {
				System.out.println("Category searching!");
				if(request.getParameter("category").equals("science")) {
					try {
						currentList = model.retrieveBooksByCategory("Science");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else if(request.getParameter("category").equals("fiction")) {
					try {
						currentList = model.retrieveBooksByCategory("Fiction");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {
					try {
						currentList = model.retrieveBooksByCategory("Engineering");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			else if(request.getParameter("textSearchButton").equals("Search")) {
				try {
					currentList = model.retrieveBooksBySearch(request.getParameter("searchvalue"));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			request.setAttribute(LIST_OF_BOOKS, currentList);
			request.getRequestDispatcher(target).forward(request, response);
			
		}
		/*
		 * Display page for viewing a certain books details
		 */
		else if(URI.contains("BookDetails")) {
			if(request.getParameter("submitReview") != null) {
				if(request.getParameter("submitReview").equals("Submit")) {
					String bid = request.getParameter("bookid");
					String review = request.getParameter("reviewText");
					String email = request.getParameter("reviewEmail");
					int rating = Integer.parseInt(request.getParameter("reviewRating"));
					try {
						model.addReview(bid, review, email, rating);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			String bidToSearch;
			bidToSearch = request.getParameter("bookid");
			Map<String, BookBean> currentList = new HashMap<String, BookBean>();
			try {
				currentList = model.retrieveBooksByBID(bidToSearch);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute(LIST_OF_BOOKS, currentList.get(request.getParameter("bookid")));
			target = "/BookDetails.jspx";
			request.getRequestDispatcher(target).forward(request, response);
			
		}
		/*
		 * Seeing all reviews for a book
		 */
		else if(URI.contains("Reviews")) {
			String bidToSearch;
			bidToSearch = request.getParameter("bookid");
			
			Map<String, ReviewBean> currentList = new HashMap<String, ReviewBean>();
			try {
				currentList = model.retrieveReviewsByBID(bidToSearch);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute(LIST_OF_REVIEWS, currentList);
			target = "/BookReviews.jspx";
			request.getRequestDispatcher(target).forward(request, response);
		}
		/*
		 * login page
		 */
		else if(URI.contains("Login")) {
			
			if(request.getParameter("loginSubmit") != null) {
				if(request.getParameter("loginSubmit").equals("Submit")) {
					String loggedInUserName = request.getParameter("givenUserName");
					String loggedInPassword = request.getParameter("givenPassword");
					try {
						if(model.retrieveAccountByUsername(loggedInUserName, loggedInPassword) != null) {
							accountBean = model.retrieveAccountByUsername(loggedInUserName, loggedInPassword);
							request.getSession().setAttribute(LOGIN_ERROR_MESSAGE, null);
							response.sendRedirect("/PDL_Book_Store/Start");
						}
						else {
							request.getSession().setAttribute(LOGIN_ERROR_MESSAGE, "Not a valid login, try again.");
							System.out.println("user not found");
							target = "/LoginPage.jspx";
							request.getRequestDispatcher(target).forward(request, response);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					request.getSession().setAttribute(ACCOUNT_BEAN, accountBean);
					
				}
				
			}
			else {
				target = "/LoginPage.jspx";
				request.getRequestDispatcher(target).forward(request, response);
			}
			
		}
		/*
		 * logout page
		 */
		else if (URI.contains("Logout")) {
			
			request.getSession().setAttribute(ACCOUNT_BEAN, null);
			System.out.println(request.getSession().getAttribute(ACCOUNT_BEAN));
			
			target = "/LogoutPage.jspx";
			request.getRequestDispatcher(target).forward(request, response);
			
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
