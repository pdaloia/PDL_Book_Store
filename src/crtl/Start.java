package crtl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.AccountBean;
import bean.AddressBean;
import bean.BookBean;
import bean.CartBean;
import bean.ReviewBean;
import java.util.List;
import model.model;

/**
 * Servlet implementation class Start
 */
@WebServlet({ "/Start", "/DisplayBooksPage", "/BookDetails", "/Reviews", "/Login", "/Logout", "/Register",
		"/ShoppingCart", "/Payment" })
public class Start extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String target;
	private model model;
	private static final String MODEL = "model";
	private static final String LIST_OF_BOOKS = "listOfBooks";
	private static final String LIST_OF_REVIEWS = "listOfReviews";
	private static final String LOGIN_ERROR_MESSAGE = "loginErrorMessage";
	private static final String LIST_OF_Cart = "listOfCart";
	private static final String REGISTER_ERROR_MESSAGE = "registerErrorMessage";
	private Map<String, BookBean> myCart = new HashMap<String, BookBean>();

	private int item_list = 0;
	private double subtotal_list = 0.0;

	// account details
	private AccountBean currentUser;
	private AddressBean currentAddress;
	private static final String ACCOUNT_BEAN = "accountBean";
	private static final String ADDRESS_BEAN = "addressBean";
	private static final String SESSION_USER_NAME = "sessionUsername";
	private static final String PAYMENT_RESULT_MESSAGE = "paymentResultMessage";
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// System.out.println("Servlet started");

		// get values from URL
		String url = this.getServletContext().getContextPath();
		String URI = request.getRequestURI();
		String queryString = request.getQueryString();
		Object u = request.getSession().getAttribute(SESSION_USER_NAME);
		if (null == u) {
			u = request.getSession().getId();
		}

		final String username = String.valueOf(u);
		String countLabel = "";
		int cartCount = model.countCartItem(username);
		if (cartCount > 0) {
			if (cartCount > 99) {
				countLabel = "99+";
			} else {
				countLabel = cartCount + "";
			}
		}
		request.setAttribute("cartCount", countLabel);

		// Main page
		if (URI.contains("Start") && request.getParameter("textSearchButton") == null) {
			target = "/BookStoreMainPage.jspx";
			request.getRequestDispatcher(target).forward(request, response);
		}
		/*
		 * Display a list/selection of books page Go to this page when a list of books
		 * needs to be shown to the client Such as: 1) Display books by category 2)
		 * Display books by
		 */
		else if (URI.contains("DisplayBooksPage") || request.getParameter("textSearchButton") != null) {
			Map<String, BookBean> currentList = new HashMap<String, BookBean>();
			target = "/DisplayBooksPage.jspx";
			if (request.getParameter("category") != null) {
				System.out.println("Category searching!");
				if (request.getParameter("category").equals("science")) {
					try {
						currentList = model.retrieveBooksByCategory("Science");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (request.getParameter("category").equals("fiction")) {
					try {
						currentList = model.retrieveBooksByCategory("Fiction");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					try {
						currentList = model.retrieveBooksByCategory("Engineering");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else if (request.getParameter("textSearchButton").equals("Search")) {
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
		else if (URI.contains("BookDetails")) {
			// to check if the button is clicked
			if (request.getParameter("addToCart") != null) {

				String bidToSearch;
				bidToSearch = request.getParameter("bookid");
				Map<String, BookBean> currentList = new HashMap<String, BookBean>();
				BookBean b = new BookBean();

				try {
					currentList = model.retrieveBooksByBID(bidToSearch);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				b = currentList.get(bidToSearch);
				// subtotal_list += Double.parseDouble((b.getPrice()));
				myCart = currentList;
				request.setAttribute(LIST_OF_BOOKS, currentList.get(request.getParameter("bookid")));
			}
			
			if (request.getParameter("submitReview") != null) {

				String bid = request.getParameter("bookid");
				String review = request.getParameter("reviewText");
				String email = currentUser.getEmail();
				int rating = Integer.parseInt(request.getParameter("reviewRating"));
				try {
					model.addReview(bid, review, email, rating);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
		 * 
		 */

		else if (URI.contains("Reviews")) {
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
		 * Registration page
		 */
		else if (URI.contains("Register")) {
			String enteredUserName = request.getParameter("enteredUserName");
			String enteredFirstName = request.getParameter("enteredFirstName");
			String enteredLastName = request.getParameter("enteredLastName");
			String enteredEmail = request.getParameter("enteredEmail");
			String enteredPassword = request.getParameter("enteredPassword");
			if (request.getParameter("registerSubmit") != null) {
				// check the validity with javascript. 
				if (enteredUserName.equals("") || enteredFirstName.equals("") || enteredLastName.equals("")
						|| enteredEmail.equals("") || enteredPassword.equals("")) {
					System.out.println("field left empty");
					request.setAttribute(REGISTER_ERROR_MESSAGE, "Please enter all fields");
					target = "/RegistrationPage.jspx";
					request.getRequestDispatcher(target).forward(request, response);
				}
				// all account fields entered
				else {
					try {
						// username is new
						if (model.checkForUser(enteredUserName) == false) {
							model.addNewAccount(enteredUserName, enteredFirstName, enteredLastName, enteredEmail,
									enteredPassword, null);
							request.getSession().setAttribute(REGISTER_ERROR_MESSAGE, null);
							request.getSession().setAttribute(ACCOUNT_BEAN,
									model.retrieveAccountByUsername(enteredUserName, enteredPassword));
							response.sendRedirect("/PDL_Book_Store/Start");
						} else {
							request.getSession().setAttribute(REGISTER_ERROR_MESSAGE, "That user name is taken");
							System.out.println("user already exists");
							target = "/RegistrationPage.jspx";
							request.getRequestDispatcher(target).forward(request, response);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

			} else {
				target = "/RegistrationPage.jspx";
				request.getRequestDispatcher(target).forward(request, response);
			}
		}
		/*
		 * login page
		 */
		else if (URI.contains("Login")) {
			if (request.getParameter("loginSubmit") != null) {
				if (request.getParameter("loginSubmit").equals("Submit")) {
					String loggedInUserName = request.getParameter("givenUserName");
					String loggedInPassword = request.getParameter("givenPassword");
					try {
						if (model.retrieveAccountByUsername(loggedInUserName, loggedInPassword) != null) {
							currentUser = model.retrieveAccountByUsername(loggedInUserName, loggedInPassword);
							request.getSession().setAttribute(LOGIN_ERROR_MESSAGE, null);
							request.getSession().setAttribute(SESSION_USER_NAME, loggedInUserName);
							request.getSession().setAttribute(ACCOUNT_BEAN, currentUser);
							if (request.getParameter("state") != null) {
								response.sendRedirect("/PDL_Book_Store/Payment");
							}
							else response.sendRedirect("/PDL_Book_Store/Start");

						} else {
							request.getSession().setAttribute(LOGIN_ERROR_MESSAGE, "Not a valid login, try again.");
							System.out.println("user not found");
							target = "/LoginPage.jspx";
							request.getRequestDispatcher(target).forward(request, response);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

			} else {
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
		/*
		 * Shopping Cart
		 */

		else if (URI.contains("ShoppingCart")) {

			if (request.getMethod().equals("POST")) {
				if (request.getParameter("action") != null) {
					// modification request
					// use currentUser (accountBean object) instead of request.getParameter("username")
					String action = request.getParameter("action");
					if (action.equals("remove")) {
						model.removeItemFromCart(request.getParameter("bid").trim(),
								request.getParameter("username").trim());
						response.getOutputStream().write("{\"error\":false}".getBytes());
						return;
					} else if (action.equals("update")) {

						String[] values = request.getParameterValues("item");
						for (String s : values) {

							String tmp[] = s.split(",");
							float unitPrice = new Float(tmp[3].trim());
							int totalQuan = new Integer(tmp[0].trim());
							String user = tmp[2].trim();
							String bid = tmp[1].trim();

							model.updateCartProduct(bid, user, (unitPrice * totalQuan), totalQuan);

						}
						response.getOutputStream().write("{\"error\":false}".getBytes());
						return;

					}

				} else {
					model.addProductToCart(request.getParameter("bid"), request.getParameter("title"), username,
							new Integer(request.getParameter("price").trim()), 1);
				}
			}

			List<CartBean> cartList = model.retrieveUserCart(username);
			request.setAttribute("userCart", cartList);
			request.setAttribute("cartLen", cartList.size());
			int totalPrice = 0;
			for (CartBean bean : cartList) {
				totalPrice += bean.getPrice();
			}
			request.setAttribute("totalPrice", totalPrice);
			target = "/ShoppingCart.jspx";
			request.getRequestDispatcher(target).forward(request, response);
		}
		
		/*
		 * Payment
		 * 
		 * Todo 2: check the form on jspx page 
		 */
		
		else if (URI.contains("Payment")) {
			// 1. check if user has logged in
			target = "/Payment.jspx";
			if(currentUser != null) {
				// a. check if address has been saved into this account
				Integer id = currentUser.getAddressId();
				if(id != null) {
					try {
						currentAddress = model.retrieveAddressById(id);
						request.getSession().setAttribute(ADDRESS_BEAN, currentAddress);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				// b. check if order has been placed. 
				if(request.getParameter("confirmOrder1") != null) {
					String cardLastName = request.getParameter("cardLastName");
					String cardFirstName = request.getParameter("cardFirstName");
					// b1. check if current user has existing address. 
					if(id != null) {
						// b-1a. check if the user has requested the different address. 
						// 	  check the validity with javascript. 
						if(request.getParameter("differentStreet")!=null) {
							// Get different address
							String differentStreet = request.getParameter("differentStreet");
							String differentProvince = request.getParameter("differentProvince");
							String differentCountry = request.getParameter("differentCountry");
							String differentZip = request.getParameter("differentZip");
							String differentPhone = request.getParameter("differentPhone");
							
							// Place an order and delete books from cart
							try {
								// add different address to the table. 
								int addressId = 0;
								addressId = model.addNewAddress(differentStreet, differentProvince, differentCountry, differentZip, differentPhone);
								List<CartBean> cartList = model.retrieveUserCart(username);
								String resultMsg = model.placeOrder(cardLastName, cardFirstName, addressId, cartList);
								request.getSession().setAttribute(PAYMENT_RESULT_MESSAGE, resultMsg);
								
								// delete books from cart
								for (CartBean book: cartList) {
									model.removeItemFromCart(book.getBid(), username);
								}
								
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							target = "/OrderResult.jspx";

							
							// Initialize the form variables 
							request.getSession().setAttribute("differentStreet", null);
							request.getSession().setAttribute("differentProvince", null);
							request.getSession().setAttribute("differentCountry", null);
							request.getSession().setAttribute("differentZip", null);
							request.getSession().setAttribute("differentPhone", null);
						}
						// b-1b. order with existing address  
						else {
							try {
								int addressId = currentUser.getAddressId();
								List<CartBean> cartList = model.retrieveUserCart(username);
								String resultMsg = model.placeOrder(cardLastName, cardFirstName, addressId, cartList);
								request.getSession().setAttribute(PAYMENT_RESULT_MESSAGE, resultMsg);
								
								// delete books from cart
								for (CartBean book: cartList) {
									model.removeItemFromCart(book.getBid(), username);
								}
								
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							target = "/OrderResult.jspx";
						}	
						
					}
					// b-2. current user has no existing address.
					else {
						// Get new address
						String newStreet = request.getParameter("newStreet");
						String newProvince = request.getParameter("newProvince");
						String newCountry = request.getParameter("newCountry");
						String newZip = request.getParameter("newZip");
						String newPhone = request.getParameter("newPhone");
						// Place an order 
						try {
							// Add new address and save it into the current user
							int addressId = 0;
							addressId = model.addNewAddress(newStreet, newProvince, newCountry, newZip, newPhone);
							currentUser.setAddressId(addressId);
							
							// Add cart items and place an order
							List<CartBean> cartList = model.retrieveUserCart(username);
							String resultMsg = model.placeOrder(cardLastName, cardFirstName, addressId, cartList);
							request.getSession().setAttribute(PAYMENT_RESULT_MESSAGE, resultMsg);
							
							// delete books from cart
							for (CartBean book: cartList) {
								model.removeItemFromCart(book.getBid(), username);
							}
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						target = "/OrderResult.jspx";
						
						// initialize form variables
						request.getSession().setAttribute("newStreet", null);
						request.getSession().setAttribute("newProvince", null);
						request.getSession().setAttribute("newCountry", null);
						request.getSession().setAttribute("newZip", null);
						request.getSession().setAttribute("newPhone", null);
					}	
					
				}
			}
			
			// 2. New Customer placing the first order
			// 	  Save account and address information
			else if(request.getParameter("confirmOrder2")!=null) {
				// Get address information
				int addressId = 0;
				String street = request.getParameter("street");
				String province = request.getParameter("province");
				String country = request.getParameter("country");
				String zip = request.getParameter("zip");
				String phone = request.getParameter("phone");
				
				// Get account information 
				String newCstmrAccount = request.getParameter("newCstmrAccount");
				String cardLastName = request.getParameter("cardLastName");
				String cardFirstName = request.getParameter("cardFirstName");
				String newCstmrEmail = request.getParameter("newCstmrEmail");
				String newCstmrPassword = request.getParameter("newCstmrPassword");
				
	
				
				// Add address information
				// Check the validity with javascript. 
				if (street.equals("") || province.equals("") || country.equals("")
						|| zip.equals("") || phone.equals("")) {
					System.out.println("field left empty (Address Information)");
					request.setAttribute(REGISTER_ERROR_MESSAGE, "Please enter all fields for Address Information");
				}
				else {
					try {
						addressId = model.addNewAddress(street, province, country, zip, phone);
						System.out.println("Address has been succesfully added");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				// Add account information
				if (newCstmrAccount.equals("") || newCstmrEmail.equals("") || newCstmrPassword.equals("")
						|| cardFirstName.equals("") || cardLastName.equals("")) {
					System.out.println("field left empty (Account Information)");
					request.setAttribute(REGISTER_ERROR_MESSAGE, "Please enter all fields for Account Information");
				}
				else {		
					try {
						if (model.checkForUser(newCstmrAccount) == false) {
							model.addNewAccount(newCstmrAccount, cardFirstName, cardLastName, newCstmrEmail, newCstmrPassword, addressId);
							request.getSession().setAttribute(REGISTER_ERROR_MESSAGE, null);
							currentUser = model.retrieveAccountByUsername(newCstmrAccount, newCstmrPassword);
							request.getSession().setAttribute(ACCOUNT_BEAN, currentUser);
						} else {
							request.getSession().setAttribute(REGISTER_ERROR_MESSAGE, "That user name is taken");
							System.out.println("user already exists");
							
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
				
				// Place an order 
				try {
					List<CartBean> cartList = model.retrieveUserCart(username);
					String resultMsg = model.placeOrder(cardLastName, cardFirstName, addressId, cartList);
					request.getSession().setAttribute(PAYMENT_RESULT_MESSAGE, resultMsg);
					
					// delete books from cart
					for (CartBean book: cartList) {
						model.removeItemFromCart(book.getBid(), username);
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				target = "/OrderResult.jspx";
				
			}
			System.out.println("now forwarding to ..." + target);
			request.getRequestDispatcher(target).forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
