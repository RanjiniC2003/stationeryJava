package Filter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import Common.CookieManagement;

/**
 * Servlet Filter implementation class AuthendicationFilter
 */
//@WebFilter("/AuthendicationFilter")
public class AuthendicationFilter extends HttpFilter implements Filter {
       
    /**
     * @see HttpFilter#HttpFilter()
     */
    public AuthendicationFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain){
	
		try {	
//			System.out.println("authendicationFIlter");
			
			String stringID = CookieManagement.getCookie((HttpServletRequest)request, "SESSIONID");
			if (!stringID.isEmpty() && !stringID.isBlank()) {
				File file=new File("/home/ranjini-zstk321/eclipse-workspace/stationary/session.csv");
				BufferedReader br;
				
					br = new BufferedReader(new FileReader(file));
				
				String value="";
				while(true) {
				
				    value=br.readLine();
				
					if(value==null) {
	//					((HttpServletResponse)response).setStatus(301);
	//					response.getWriter().write("Permission denied");
						break;
					}
					else if(!value.isBlank()) {
						String arr[]=value.split(",");
						
//						System.out.println(arr[1]+" "+stringID);
						
						if(arr[1].equals(stringID)) {
							File file1=new File("/home/ranjini-zstk321/eclipse-workspace/stationary/Customer.csv");
							BufferedReader br1=new BufferedReader(new FileReader(file1));
							String val="";
							while(true) {
								val=br1.readLine();
								if(val==null) {
									break;
								}
								else if(!val.isBlank()) {
									String arr1[]=val.split(",");
									if(arr[2].equals(arr1[2])) {
//										System.out.println(arr1[2]);
										request.setAttribute("role",arr1[4]);
										
										chain.doFilter(request, response);
									
										break;
									}
								}
							}
							
						}
					}	
				}
			}
			else {
				
				((HttpServletResponse)response).setStatus(301);
				response.getWriter().write("Permission denied");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 catch (ServletException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
