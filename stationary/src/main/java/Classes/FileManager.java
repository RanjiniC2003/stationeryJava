package Classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import Enum.Role;
import Users.Customer;

public class FileManager {
	private FileManager() {}
	private static FileManager object; 
	
	public static FileManager getInstance() {
		if(object==null) {
			return object=new FileManager();
		}
		return object;
	}
	
	public List<Customer> getCustomers(){
		List <Customer> customers=new ArrayList<>();
		
		File file=new File("/home/ranjini-zstk321/eclipse-workspace/stationary/Customer.csv");
		String val="";
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(file));
			while(true) {
					val=reader.readLine();
			
				if(val==null) {
					break;
				}
				else if(!val.isBlank()) {
					String arr[]=val.split(",");
					if(arr[4].equals("Customer")) {
						Customer customer=new Customer(arr[0],arr[1],Long.parseLong(arr[2]),arr[3],Role.valueOf(arr[4]));
						customers.add(customer);
					}
				}
			}
			
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return customers;
	}
	
	
	public List<Product> getProducts(){
		List<Product> products=new ArrayList<>();
		File file=new File("/home/ranjini-zstk321/eclipse-workspace/stationary/Product.csv");
		String val="";
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(file));
			while(true) {
				val=reader.readLine();
				if(val==null) {
					break;
				}
				else if(!val.isBlank()) {
					String arr[]=val.split(",");
					Product product=new Product(arr[0],Double.parseDouble(arr[1]),Integer.parseInt(arr[2]),Integer.parseInt(arr[3]));
				    products.add(product);
				}
			}
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return products;
	}
	
	public Customer getAdmin() {
	
		Customer admin=null;
		File file=new File("/home/ranjini-zstk321/eclipse-workspace/stationary/Customer.csv");
		try {
			BufferedReader br=new BufferedReader(new FileReader(file));
			String value="";
			while(true) {
				value=br.readLine();
				if(value==null) {
					break;
				}
				else if(!value.isBlank()) {
					String arr[]=value.split(",");
					if(arr[4].equals("Admin")) {
						Customer admin1=new Customer(arr[0],arr[1],Long.parseLong(arr[2]), arr[3],Role.valueOf(arr[4]));
						admin=admin1;
					}
				}
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return admin;
	}
	
}
