/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import bean.BookBean;
import javax.jws.WebService;

import java.util.Collection;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import model.model;


@WebService(serviceName = "SoapWebService")
public class SoapWebService {
   model m;
    public SoapWebService() {
        m = new model();
    }
    
    
    /**
     * Web service operation
     */
    @WebMethod(operationName = "getProductInfo")
    public BookBean getProductInfo(@WebParam(name = "productId") String productId) throws Exception {
        return m.retrieveBooksByBID(productId).get(productId);
    }
    @WebMethod(operationName = "getAllProduct")
    public Collection<BookBean> getAllProduct() throws Exception {
        return m.getAllBooks();
    }

   
   
    
}
