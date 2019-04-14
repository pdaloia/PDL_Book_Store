package crtl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

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
import bean.VisitEventBean;

import java.util.List;
import model.model;

/**
 * Servlet implementation class Start
 */
@WebServlet({ "/Start", "/DisplayBooksPage", "/BookDetails", "/Reviews", "/Login", "/Logout", "/Register",
		"/ShoppingCart", "/Payment", "/Admin"})
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
	private static final String REPORT_LIST = "reportList";
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
					// Add VisitEvent
					model.createVisitEvent(bidToSearch, "CART");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				b = currentList.get(bidToSearch);
				// subtotal_list += Double.parseDouble((b.getPrice()));
				myCart = currentList;
				request.setAttribute(LIST_OF_BOOKS, currentList.get(request.getParameter("bookid")));
	//			target = "/ShoppingCart.jspx";
	//			request.getRequestDispatcher(target).forward(request, response);
			}
			
			if (request.getParameter("submitReview") != null) {
	//			if (request.getParameter("submitReview").equals("Submit")) {
					String bid = request.getParameter("bookid");
					String review = request.getParameter("reviewText");
					//String email = request.getParameter("reviewEmail");
					String email = currentUser.getEmail();
					int rating = Integer.parseInt(request.getParameter("reviewRating"));
					try {
						model.addReview(bid, review, email, rating);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		//		}
			}
			String bidToSearch;
			bidToSearch = request.getParameter("bookid");
			Map<String, BookBean> currentList = new HashMap<String, BookBean>();
			try {
				currentList = model.retrieveBooksByBID(bidToSearch);
				// Add VisitEvent
				model.createVisitEvent(bidToSearch, "VIEW");
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
							currentUser = model.retrieveAccountByUsername(enteredUserName, enteredPassword);
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
			currentUser = null;
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
							new Float(request.getParameter("price").trim()), 1);
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
		 */
		
		else if (URI.contains("Payment")) {
			// 1. check if user has logged in
			target = "/Payment.jspx";
			if(currentUser != null) {
				// a. check if address has been saved into this account
				int id = currentUser.getAddressId();
				if(id != 0) {
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
					// b1. check if current user has existing address. 
					if(id != 0) {
						// b-1a. check if the user has requested the different address. 
						// 	  check the validity with javascript. 
						
						// Get different address attributes
						String differentStreet = request.getParameter("differentStreet");
						String differentProvince = request.getParameter("differentProvince");
						String differentCountry = request.getParameter("differentCountry");
						String differentZip = request.getParameter("differentZip");
						String differentPhone = request.getParameter("differentPhone");
						if (differentStreet != null && differentProvince != null && differentCountry != null
								&& differentZip != null && differentPhone != null) {
							if (!differentStreet.equals("") && !differentProvince.equals("")
									&& !differentCountry.equals("") && !differentZip.equals("")
									&& !differentPhone.equals("")) {

								// Place an order and delete books from cart
								try {
									// add different address to the table and place an order.
									int addressId = model.addNewAddress(differentStreet, differentProvince,
											differentCountry, differentZip, differentPhone);
									List<CartBean> cartList = model.retrieveUserCart(username);
									String resultMsg = model.placeOrder(currentUser.getLname(), currentUser.getFname(), addressId,
											cartList);
									request.getSession().setAttribute(PAYMENT_RESULT_MESSAGE, resultMsg);

									// delete books from cart and add VisitEvent if order is processed.
									if (resultMsg.contains("Successfully")) {
										for (CartBean book : cartList) {
											model.removeItemFromCart(book.getBid(), username);
											model.createVisitEvent(book.getBid(), "PURCHASE");
										}
									}

								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								target = "/OrderResult.jspx";

							}
							// b-1b. order with existing address  
							else {
								try {
									List<CartBean> cartList = model.retrieveUserCart(username);
									String resultMsg = model.placeOrder(currentUser.getLname(), currentUser.getFname(), id, cartList);
									request.getSession().setAttribute(PAYMENT_RESULT_MESSAGE, resultMsg);
									
									// delete books from cart and add VisitEvent if order is processed.
									if (resultMsg.contains("Successfully")) {
										for (CartBean book: cartList) {
											model.removeItemFromCart(book.getBid(), username);
											model.createVisitEvent(book.getBid(), "PURCHASE");
										}
									}
									
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								target = "/OrderResult.jspx";
							}	
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
							int addressId = model.addNewAddress(newStreet, newProvince, newCountry, newZip, newPhone);
							currentUser.setAddressId(addressId);
							model.updateAddressId(currentUser.getUsername(), addressId);
							
							// Add cart items and place an order
							List<CartBean> cartList = model.retrieveUserCart(username);
							String resultMsg = model.placeOrder(currentUser.getLname(), currentUser.getFname(), addressId, cartList);
							request.getSession().setAttribute(PAYMENT_RESULT_MESSAGE, resultMsg);
							
							// delete books from cart and add VisitEvent if order is processed.
							if (resultMsg.contains("Successfully")) {
								for (CartBean book: cartList) {
									model.removeItemFromCart(book.getBid(), username);
									model.createVisitEvent(book.getBid(), "PURCHASE");
								}
							}
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						target = "/OrderResult.jspx";
						
						// initialize form variables
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
				String newCstmrLastName = request.getParameter("newCstmrLastName");
				String newCstmrFirstName = request.getParameter("cardFirstName");
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
						// Add new address and save it into the current user
						addressId = model.addNewAddress(street, province, country, zip, phone);
						currentUser.setAddressId(addressId);
						model.updateAddressId(currentUser.getUsername(), addressId);
					
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				// Add account information
				if (newCstmrAccount.equals("") || newCstmrEmail.equals("") || newCstmrPassword.equals("")
						|| newCstmrFirstName.equals("") || newCstmrLastName.equals("")) {
					System.out.println("field left empty (Account Information)");
					request.setAttribute(REGISTER_ERROR_MESSAGE, "Please enter all fields for Account Information");
				}
				else {		
					try {
						if (model.checkForUser(newCstmrAccount) == false) {
							model.addNewAccount(newCstmrAccount, newCstmrFirstName, newCstmrLastName, newCstmrEmail, newCstmrPassword, addressId);
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
					String resultMsg = model.placeOrder(newCstmrLastName, newCstmrFirstName, addressId, cartList);
					request.getSession().setAttribute(PAYMENT_RESULT_MESSAGE, resultMsg);
					
					// delete books from cart and add VisitEvent if order is processed.
					if (resultMsg.contains("Successfully")) {
						for (CartBean book: cartList) {
							model.removeItemFromCart(book.getBid(), username);
							model.createVisitEvent(book.getBid(), "PURCHASE");
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				target = "/OrderResult.jspx";
				
			}
			request.getRequestDispatcher(target).forward(request, response);
		}
		
		//Page for administrator functions and reports
		else if (URI.contains("Admin")) {
			
			//main page for admin
			if(request.getParameter("analytics") == null) {
				System.out.println("servlet in admin");
				target = "/AnalyticsPage.jspx";
			}
			//page to get most popular books of all time
			else if(request.getParameter("analytics").equals("lifetime")) {
				System.out.println("servlet in admin lifetime");
				target = "/AnalyticsPage.jspx";
			}
			//page to get books sold each month
			else if(request.getParameter("analytics").equals("month")) {
				
				System.out.println("servlet in admin monthly");
				target = "/AnalyticsPage.jspx";
				
				//retrieve all books sold
				LinkedList<VisitEventBean> listOfPurchases = new LinkedList<VisitEventBean>();
				try {
					listOfPurchases = model.retrieveBooksSold();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				TreeMap<String, HashMap<String, String>> booksSoldByMonth = new TreeMap<String, HashMap<String, String>>(new MyComparator());
				
				//Start populating Map with Keys, values won't matter right now
				for(VisitEventBean currentVisitEventBean : listOfPurchases) {
					System.out.println("Adding Bean: " + currentVisitEventBean);
					
					//create string for current month, year
					String currentBeanYear = currentVisitEventBean.getDay().substring(4);
					String currentBeanMonth = currentVisitEventBean.getDay().substring(0, 2);
					if(currentBeanMonth.equals("1") || currentBeanMonth.equals("01"))
						currentBeanMonth = "January";
					else if(currentBeanMonth.equals("2") || currentBeanMonth.equals("02"))
						currentBeanMonth = "February";
					else if(currentBeanMonth.equals("3") || currentBeanMonth.equals("03"))
						currentBeanMonth = "March";
					else if(currentBeanMonth.equals("4") || currentBeanMonth.equals("04"))
						currentBeanMonth = "April";
					else if(currentBeanMonth.equals("5") || currentBeanMonth.equals("05"))
						currentBeanMonth = "May";
					else if(currentBeanMonth.equals("6") || currentBeanMonth.equals("06"))
						currentBeanMonth = "June";
					else if(currentBeanMonth.equals("7") || currentBeanMonth.equals("07"))
						currentBeanMonth = "July";
					else if(currentBeanMonth.equals("8") || currentBeanMonth.equals("08"))
						currentBeanMonth = "August";
					else if(currentBeanMonth.equals("9") || currentBeanMonth.equals("09"))
						currentBeanMonth = "September";
					else if(currentBeanMonth.equals("10") || currentBeanMonth.equals("10"))
						currentBeanMonth = "October";
					else if(currentBeanMonth.equals("11") || currentBeanMonth.equals("11"))
						currentBeanMonth = "November";
					else
						currentBeanMonth = "December";
					String currentBeanFullDate = currentBeanMonth + ", " + currentBeanYear;
					
					HashMap<String, String> innerMapping = new HashMap<String, String>();
					
					String bookTitle = null;
					try {
						bookTitle = model.retrieveBooksByBID(currentVisitEventBean.getBid()).get(currentVisitEventBean.getBid()).getTitle();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					//if date isn't in map yet, add it with an inner mapping of the current purchase event's book
					if(booksSoldByMonth.containsKey(currentBeanFullDate) == false) {
						innerMapping.put(bookTitle, "1");
						booksSoldByMonth.put(currentBeanFullDate, innerMapping);
					}
					//if date is in map already
					else {
						innerMapping = booksSoldByMonth.get(currentBeanFullDate);
						int numberOfBooksSold;
						//if the inner mapping contains the book id
						if(innerMapping.containsKey(bookTitle)){
							numberOfBooksSold = Integer.parseInt(innerMapping.get(bookTitle));
							numberOfBooksSold++;
							innerMapping.put(bookTitle, Integer.toString(numberOfBooksSold));
							booksSoldByMonth.put(currentBeanFullDate, innerMapping);
						}
						//if the inner mapping does not contain the book id
						else {
							innerMapping.put(bookTitle, "1");
							booksSoldByMonth.put(currentBeanFullDate, innerMapping);
						}
					}
					
				}
				
				request.setAttribute(REPORT_LIST, booksSoldByMonth);
				System.out.println(booksSoldByMonth);
				target = "/BooksSoldEachMonthPage.jspx";
				
			}
			else {
				System.out.println("servlet in admin else");
				target = "/AnalyticsPage.jspx";
			}
			
			
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
	
	static class MyComparator implements Comparator<String>
    {
		@Override
        public int compare(String o1,String o2)
        {
			int result = 0;
			
            String o1Date[] = o1.split(", ");
            String o2Date[] = o2.split(", ");
            String o1MonthString = o1Date[0];
            int o1Year = Integer.parseInt(o1Date[1]);
            String o2MonthString = o2Date[0];
            int o2Year = Integer.parseInt(o2Date[1]);
            
            int o1Month = 0;
            int o2Month = 0;
            
            if (o1MonthString.equals("January"))
            	o1Month = 1;
            else if (o1MonthString.equals("February"))
            	o1Month = 2;
            else if (o1MonthString.equals("March"))
            	o1Month = 3;
            else if (o1MonthString.equals("April"))
            	o1Month = 4;
            else if (o1MonthString.equals("May"))
            	o1Month = 5;
            else if (o1MonthString.equals("June"))
            	o1Month = 6;
            else if (o1MonthString.equals("July"))
            	o1Month = 7;
            else if (o1MonthString.equals("August"))
            	o1Month = 8;
            else if (o1MonthString.equals("September"))
            	o1Month = 9;
            else if (o1MonthString.equals("October"))
            	o1Month = 10;
            else if (o1MonthString.equals("November"))
            	o1Month = 11;
            else if (o1MonthString.equals("December"))
            	o1Month = 12;
            
            if (o2MonthString.equals("January"))
            	o2Month = 1;
            else if (o2MonthString.equals("February"))
            	o2Month = 2;
            else if (o2MonthString.equals("March"))
            	o2Month = 3;
            else if (o2MonthString.equals("April"))
            	o2Month = 4;
            else if (o2MonthString.equals("May"))
            	o2Month = 5;
            else if (o2MonthString.equals("June"))
            	o2Month = 6;
            else if (o2MonthString.equals("July"))
            	o2Month = 7;
            else if (o2MonthString.equals("August"))
            	o2Month = 8;
            else if (o2MonthString.equals("September"))
            	o2Month = 9;
            else if (o2MonthString.equals("October"))
            	o2Month = 10;
            else if (o2MonthString.equals("November"))
            	o2Month = 11;
            else if (o2MonthString.equals("December"))
            	o2Month = 12;
            
            if(o1Year > o2Year) {
            	result = 1;
            }
            else if(o1Year < o2Year) {
            	result = -1;
            }
            else {
            	if(o1Month > o2Month) {
            		result = 1;
            	}
            	else if(o2Month > o1Month) {
            		result = -1;
            	}
            	else {
            		result = 0;
            	}
            	
            }
            
            return result;
        }
    }

}
