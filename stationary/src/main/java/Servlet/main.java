package Servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Classes.FileManager;
import Classes.Product;
import Main.ProductManagement;
import Main.UserManagement;
import Users.Customer;

/**
 * Servlet implementation class main
 */
//@WebServlet("/main")
public class main extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public main() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init(){
//    	System.out.println("sdf");
    	List<Customer> customers=FileManager.getInstance().getCustomers();
		for(Customer cus:customers) {
			UserManagement.getInstance().addCustomers(cus);
		}
		List<Product> products=FileManager.getInstance().getProducts();
		for(Product product:products) {
			ProductManagement.getInstance().addProduct(product);
		}
		
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
