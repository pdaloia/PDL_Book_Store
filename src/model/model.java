package model;

import java.util.HashMap;
import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import bean.VisitEventBean;
import bean.POBean;
import bean.AddressBean;
import bean.BookBean;
import bean.POItemBean;

import DAO.AddressDAO;
import DAO.BookDAO;
import DAO.PODAO;
import DAO.POItemDAO;
import DAO.VisitEventDAO;

import java.util.Map;

public class model {

private BookDAO bookDAO;

	public model(){
		
		try {
			bookDAO = new BookDAO();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}