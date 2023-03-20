package Main;


import java.util.ArrayList;
import java.util.List;


import Classes.Product;

public class ProductManagement {
    
	private ProductManagement() {}
	public List<Product> products=new ArrayList<>();
	private static ProductManagement object;
	public static ProductManagement getInstance() {
		if(object==null) {
			return object=new ProductManagement();
		}
		return object;
	}
	
	
	public void addProduct(Product product){
		products.add(product);
	}
	
	public List<Product> getProduct() {
		return products;
	}
	
}
