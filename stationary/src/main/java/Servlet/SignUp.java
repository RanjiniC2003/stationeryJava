package Servlet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.User;
import org.json.JSONObject;

import Classes.FileManager;
import Common.CookieManagement;
import Enum.Role;
import Main.UserManagement;
import Users.Customer;

/**
 * Servlet implementation class SignUp
 */
@WebServlet("/SignUp")
public class SignUp extends HttpServlet {
	private final Pattern namePattern = Pattern.compile("^[A-Za-z. ]+$");
    private final Pattern lastPattern = Pattern.compile("^[A-Za-z]+$");
    private final Pattern phoneNumPattern = Pattern.compile("^[0-9]{10}$");
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUp() {
        super();
        // TODO Auto-generated constructor stub
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
//		doGet(request, response);
		
		JSONObject json=new JSONObject();
		
		
		String firstName=request.getParameter("firstName");
		String lastName=request.getParameter("lastName");
		String phoneNumber=request.getParameter("phoneNumber");
		String password=request.getParameter("password");
		
		
		  if(firstName.equals("") || firstName.trim().equals("") || (!namePattern.matcher(firstName).find())) {
			   json.put("statusCode", 400);
			   json.put("message", "Failed");
			   json.put("detailedMessage","Invalid first name");
			   response.getWriter().write(json.toString());
			   return;
		   }
		   if(lastName.equals("") || lastName.trim().equals("") || (!lastPattern.matcher(lastName).find())) {
			   json.put("statusCode", 400);
			   json.put("message", "Failed");
			   json.put("detailedMessage","Invalid last name");
			   response.getWriter().write(json.toString());
			   return;
			  
		   }
		   
		   if(!phoneNumPattern.matcher(String.valueOf(phoneNumber)).find()) {
			   json.put("statusCode", 400);
			   json.put("message", "Failed");
			   json.put("detailedMessage","Invalid phone Number");
			   response.getWriter().write(json.toString());
			   return;
		
		   }
		   if(FileManager.getInstance().getAdmin().getPhoneNumber()!=Long.parseLong(phoneNumber)) {
			   for(int i=0;i<UserManagement.getInstance().getCustomers().size();i++) {
				   if(UserManagement.getInstance().getCustomers().get(i).getPhoneNumber()==Long.parseLong(phoneNumber)) {
					   json.put("statusCode", 400);
					   json.put("message", "Failed");
					   json.put("detailedMessage","Invalid phone Number");
					   response.getWriter().write(json.toString());
					   return;
				   }
			   }
		   }
		   else {
			   json.put("statusCode", 400);
			   json.put("message", "Failed");
			   json.put("detailedMessage","Invalid phone Number");
			   response.getWriter().write(json.toString());
			   return;
		   }
		   
		
		   if(password.equals("") || password.trim().equals("")) {
			   json.put("statusCode", 400);
			   json.put("message", "Failed");
			   json.put("detailedMessage","Invalid password");
			   response.getWriter().write(json.toString());
			   return;
		   }
		   
		   
		   try {
				
				FileWriter file=new FileWriter("/home/ranjini-zstk321/eclipse-workspace/stationary/Customer.csv",true);
				BufferedWriter bw=new BufferedWriter(file);
				String value="\n"+firstName+","+lastName+","+phoneNumber+","+password+",Customer";
				Customer customer=new Customer(firstName, lastName,Long.parseLong(phoneNumber), password,Role.valueOf("Customer"));
				UserManagement.getInstance().addCustomers(customer);
				bw.write(value);
				bw.close();
				
				
				UUID sessionId=UUID.randomUUID();
				CookieManagement.setCookie(response, "SESSIONID", String.valueOf(sessionId));
//				System.out.println(sessionId);
				
				
				FileWriter fw=new FileWriter("/home/ranjini-zstk321/eclipse-workspace/stationary/session.csv",true);
				BufferedWriter gw=new BufferedWriter(fw);
				String value1="\nSESSION,"+sessionId+","+phoneNumber; 
				gw.write(value1);
				gw.close();
				
				
				
				
				json.put("statusCode", 200);
			    json.put("message", "Success");
			    json.put("detailedMessage","Sign up Successfully");
			    response.getWriter().write(json.toString());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
