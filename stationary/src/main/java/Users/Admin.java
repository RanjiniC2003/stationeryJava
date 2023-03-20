package Users;

import java.util.List;

import Enum.Role;
import Main.UserManagement;

public class Admin extends Customer{
 
    private List<Customer> Customers;
    
    
    public Admin(String firstName,String lastName,long phoneNumber,String password,Role role) {
    	super(firstName, lastName, phoneNumber, password,role);
    	
    }


	public List<Customer> getCustomers() {
		return Customers=UserManagement.getInstance().getCustomers();
	}


	public void setCustomers(List<Customer> customers) {
		Customers = customers;
	}
    
	
    
}
