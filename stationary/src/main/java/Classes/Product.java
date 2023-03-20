package Classes;




public class Product {
   private String productName;
   private double price;
   private int quentity;
   private int productId;
 
   
   public Product(String productName,double price,int quentity,int productId) {
	   this.productName=productName;
	   this.price=price;
	   this.quentity=quentity;
	   this.productId=productId;
   }
   
   public String toString() {
	   return "Product Name : "+productName+" ,Price : "+price+" ,Quentity : "+quentity;
   }
	public String getProductName() {
		return productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public int getQuentity() {
		return quentity;
	}
	
	public void setQuentity(int quentity) {
		this.quentity = quentity;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}
	    
}
