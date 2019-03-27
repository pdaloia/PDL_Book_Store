package crtl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Start
 */
@WebServlet({"/Start", "/DisplayBooksPage"})
public class Start extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String target;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Start() {
        super();
        // TODO Auto-generated constructor stub
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
		System.out.println(url);
		String URI = request.getRequestURI();
		System.out.println(URI);
		String queryString = request.getQueryString();
		System.out.println(queryString);
		
		//Main page
		if(URI.contains("Start")) {
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
		else if(URI.contains("DisplayBooksPage")) {
			target = "/DisplayBooksPage.jspx";
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
