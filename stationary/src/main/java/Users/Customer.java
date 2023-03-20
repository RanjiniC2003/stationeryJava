package Users;

import java.util.List;
import Classes.Product;
import Enum.Role;

public class Customer {
	private String firstName;
	private String lastName;
	private long phoneNumber;
	private  String password;
	private List<Product> productDetails;
	private Role role;
	
	public String toString() {
		return "First Name : "+firstName+" ,Last Name : "+lastName+" ,Phone Number : "+phoneNumber;
	}
	
	public Customer(String firstName,String lastName,long phoneNumber, String password,Role role) {
		this.firstName=firstName;
		this.lastName=lastName;
		this.phoneNumber=phoneNumber;
		this.password=password;
		this.role=role;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public long getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public List<Product> getProductDetails() {
		return productDetails;
	}


	public void setProductDetails(List<Product> productDetails) {
		this.productDetails = productDetails;
	}


	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}
	
	
	
}
