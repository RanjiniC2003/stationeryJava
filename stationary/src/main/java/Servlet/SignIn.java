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

import org.json.JSONObject;

import Classes.FileManager;
import Common.CookieManagement;
import Main.UserManagement;
import Users.Customer;

/**
 * Servlet implementation class SignIn
 */
@WebServlet("/SignIn")
public class SignIn extends HttpServlet {
	
    private final Pattern phoneNumPattern = Pattern.compile("^[0-9]{10}$");
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignIn() {
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

		JSONObject json=new JSONObject();
		
		String phoneNumber=request.getParameter("phoneNumber");
		String password=request.getParameter("password");
		
		boolean val=false;
		if(FileManager.getInstance().getAdmin().getPhoneNumber()==Long.parseLong(phoneNumber) && FileManager.getInstance().getAdmin().getPassword().equals(password)) {
			val=true;
			UUID sessionId=UUID.randomUUID();
			CookieManagement.setCookie(response, "SESSIONID", String.valueOf(sessionId));
			
			
			
			
			fileReWrite(String.valueOf(sessionId),Long.parseLong(phoneNumber));
			
			
			json.put("statusCode",200);
			json.put("message", "Success");
			json.put("detailedMessage","Sign In Successfully");
			response.getWriter().write(json.toString());
			return;
		}
		for(int i=0;i<UserManagement.getInstance().getCustomers().size();i++) {
			if(UserManagement.getInstance().getCustomers().get(i).getPassword().equals(password) && UserManagement.getInstance().getCustomers().get(i).getPhoneNumber()==Long.parseLong(phoneNumber)) {
				val=true;
				
				UUID sessionId=UUID.randomUUID();
				CookieManagement.setCookie(response, "SESSIONID", String.valueOf(sessionId));
				fileReWrite(String.valueOf(sessionId),Long.parseLong(phoneNumber));
				
				
			    json.put("statusCode", 200);
			    json.put("message", "Success");
			    json.put("detailedMessage","Sign In Successfully");
			    response.getWriter().write(json.toString());
			    return;
			}
		}
		
		if(val==false) {
			 json.put("statusCode", 500);
			 json.put("message", "Failed");
			 json.put("detailedMessage","Invalid Phone number or password");
			 response.getWriter().write(json.toString());
			 return;
		}
		
		
	}

	
	
	public static void fileReWrite(String sessionId,long phoneNumber) throws IOException {
		System.out.println(phoneNumber);
		boolean check=false;
		
		int count=0; 
			
		File file1=new File("/home/ranjini-zstk321/eclipse-workspace/stationary/session.csv");
		BufferedReader br=new BufferedReader(new FileReader(file1));
		String value="";
		while(true) {
			value=br.readLine();
			if(value==null) {
				break;
			}
			else if(!value.isBlank()) {
				count++;
				String[] arr=value.split(",");
				if(arr[2].equals(phoneNumber)) {
					  check=true;
					  Path file2 = Paths.get("/home/ranjini-zstk321/eclipse-workspace/stationary/session.csv");
		
			          List<String> lines = Files.readAllLines(file2);
			          String store="SESSION,"+sessionId+","+phoneNumber;
			          lines.set(count,store);
			          // 
			          Files.write(file2, lines);
			          Files.write(file2, lines);
			          break;
				}
			}
		}
		
		if(!check) {
			FileWriter fw=new FileWriter("/home/ranjini-zstk321/eclipse-workspace/stationary/session.csv",true);
			BufferedWriter gw=new BufferedWriter(fw);
			
			String value1="\n"+"SESSION,"+sessionId+","+phoneNumber;
			gw.write(value1);
			gw.close();
		}
	}
}
