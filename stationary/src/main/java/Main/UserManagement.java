package Main;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import Classes.FileManager;
import Enum.Role;
import Users.Customer;



public class UserManagement {
	private UserManagement() {}
	
	private static UserManagement object;
	List<Customer> customers=new ArrayList<>();
	
	
	public static  UserManagement getInstance(){
		if(object==null) {
			return object=new UserManagement();
		}
		return object;
	}
	
	
	
	
	public void addCustomers(Customer cus){
		customers.add(cus);
	}
	public List<Customer> getCustomers() {
		return customers;
	}
	
	
	
	public Customer logIn(long phoneNumber,String password) {
		boolean val=false;
		if(FileManager.getInstance().getAdmin().getPhoneNumber()==phoneNumber && FileManager.getInstance().getAdmin().getPassword().equals(password)) {
			val=true;
			return FileManager.getInstance().getAdmin();
		}
		for(int i=0;i<getCustomers().size();i++) {
			if(getCustomers().get(i).getPassword().equals(password) && getCustomers().get(i).getPhoneNumber()==phoneNumber) {
				val=true;
				return getCustomers().get(i);
			}
		}
		
		if(val==false) {
			System.out.println("Invalid Phone number or password");
		}
		return null;
	}
	
}
