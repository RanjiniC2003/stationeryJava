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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import Classes.BYProduct;
import Classes.Product;
import Classes.updateProduct;
import Main.ProductManagement;

/**
 * Servlet implementation class Product
 */
@WebServlet("/Product/*")
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductServlet() {
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
		String path=(String) request.getPathInfo();
		String role=(String) request.getAttribute("role");
		
	
		if(path.equals("/getProduct")) {
			System.out.println("df");
			JSONObject json=new JSONObject();
			json.put("statusCode", 200);
			json.put("message", "Successfully");
			json.put("role", role);
			json.put("productList",ProductManagement.getInstance().getProduct());
			response.getWriter().write(json.toString());
			
		}
		else if(path.equals("/updateProduct")) {
			String productName=request.getParameter("productName");
			String price=request.getParameter("price");
			String quantity=request.getParameter("quantity");
			
			updateProduct updateproduct=new updateProduct(productName, Double.parseDouble(price), Integer.parseInt(quantity));
	        updateproduct.start();
	        
	        try {
				updateproduct.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JSONObject json=new JSONObject();
			json.put("statusCode",200);
			json.put("message", "Success");
			json.put("detailedMessage","Update Successfully");
			response.getWriter().write(json.toString());
			
		}
		else if(path.equals("/addProduct")) {
			
			JSONObject json=new JSONObject();
			
			
			String productName=request.getParameter("productName");
			String price=request.getParameter("price");
			String quantity=request.getParameter("quantity");
			
		    int count=0; 
		
			File file1=new File("/home/ranjini-zstk321/eclipse-workspace/stationary/Product.csv");
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
					
					if(arr[0].equals(productName)) {
						System.out.println("product1");
						 json.put("statusCode", 500);
						 json.put("message", "Failed");
						 json.put("detailedMessage","This product was already added");
						 response.getWriter().write(json.toString());
						 return;
					}
				}
			}
			
			
			FileWriter fileWriter=new FileWriter("/home/ranjini-zstk321/eclipse-workspace/stationary/Product.csv",true);
			BufferedWriter bufferedWriter=new BufferedWriter(fileWriter);
//			System.out.println(productName+","+price+","+quantity+","+count);
			
			count=count+1;
			String store="\n"+productName+","+price+","+quantity+","+count;
			bufferedWriter.write(store);
			bufferedWriter.close();
			Product product=new Product(productName,Double.parseDouble(price),Integer.parseInt(quantity), count);
			
			ProductManagement.getInstance().addProduct(product);
			
			
			 json.put("statusCode", 200);
			 json.put("message", "Success");
			 json.put("detailedMessage","Product was successfully added");
			 response.getWriter().write(json.toString());
			
			
		}
//		else if(path.equals("/cartList")) {
//			String productId=request.getParameter("productId");
//			product.add(Integer.parseInt(productId));
//			
////			System.out.println(product.size());
//			
////			for(Integer set:product) {
////				System.out.println(set);
////			}
//			
//			JSONObject json=new JSONObject();
//			json.put("statusCode",200);
//			json.put("detailedMessage","SUCCESS");
//			response.getWriter().write(json.toString());
//		}
		else if(path.equals("/viewCartList")) {
			
			JSONObject json=new JSONObject();
			String arrJson = request.getParameter("arr");
			JSONArray jsonArray = new JSONArray(arrJson);
			HashSet<Integer> product=new HashSet<Integer>();
			for(int i=0;i<jsonArray.length();i++) {
				Object val=jsonArray.get(i);
				Integer id=Integer.parseInt(val.toString());
			    product.add(id);
				
			}
			
			ArrayList<Product> prod=new ArrayList<>();
			
			for(Integer id:product) {
				FileReader file=new FileReader("/home/ranjini-zstk321/eclipse-workspace/stationary/Product.csv");
				BufferedReader br=new BufferedReader(file);
				String value="";
				while(true) {
					value=br.readLine();
					if(value==null) {
						break;
					}
					else if(!value.isBlank()) {
						String[] arr=value.split(",");
						if(id==Integer.parseInt(arr[3])) {
							Product productDetails=new Product(arr[0],Double.parseDouble(arr[1]),Integer.parseInt(arr[2]),Integer.parseInt(arr[3]));
							prod.add(productDetails);
						}
						
					}
				}
			}
			
			
			
			json.put("statusCode",200);
			json.put("detailedMessage","SUCCESS");
			json.put("list", prod);
			response.getWriter().write(json.toString());
		}
		
		else if(path.equals("/byProduct")) {
			String arr=request.getParameter("arr");
			JSONArray jsonArray=new JSONArray(arr);
			for(int i=0;i<jsonArray.length();i++) {
				Object val=jsonArray.get(i);
				String[] split=String.valueOf(val).split("-");
			    String productName=String.valueOf(split[2]);
			    Integer quantity=Integer.parseInt(split[1]);
				FileReader file=new FileReader("/home/ranjini-zstk321/eclipse-workspace/stationary/Product.csv");
				BufferedReader bufferedReader=new BufferedReader(file);
				String value="";
				while(true) {
					value=bufferedReader.readLine();
					if(value==null) {
						break;
					}
					else if(!value.isBlank()) {
						String[] array=value.split(",");
						if(productName.equals(array[0])) {
//							quantity=Integer.parseInt(arr[2])-quantity;
							BYProduct byProduct=new BYProduct(productName, Double.parseDouble(array[1]),quantity,Integer.parseInt(array[2]));
							byProduct.start();
							
					        try {
					        	byProduct.join();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			}
			JSONObject json=new JSONObject();
			json.put("statusCode",200);
			json.put("message", "Success");
			json.put("detailedMessage","Your order has been placed Successfully");
			response.getWriter().write(json.toString());
		}
	}

}
