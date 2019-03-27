package crtl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.BookBean;
import model.model;

/**
 * Servlet implementation class Start
 */
@WebServlet({"/Start", "/DisplayBooksPage"})
public class Start extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String target;
	private model model;
	private static final String MODEL = "model";
	private static final String LIST_OF_BOOKS = "listOfBooks";
       
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
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		System.out.println("servlet started");
		
		//get values from URL
		String url = this.getServletContext().getContextPath();
		//System.out.println(url);
		String URI = request.getRequestURI();
		//System.out.println(URI);
		String queryString = request.getQueryString();
		//System.out.println(queryString);
		
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
			System.out.println("got here");
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
				System.out.println("got here");
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
		
		
		
		
		
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
