package Classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import Main.ProductManagement;

public class updateProduct extends Thread{
	String productName;
	double price;
	int quantity;
	int id;
	
	public updateProduct(String productName,double price,int quantity) {
		this.productName=productName;
		this.price=price;
		this.quantity=quantity;
	}
	
	public void run() {
		try {
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
					if(arr[0].equals(this.productName)) {
						  Path file = Paths.get("/home/ranjini-zstk321/eclipse-workspace/stationary/Product.csv");
						  id=Integer.parseInt(arr[3]);
				          List<String> lines = Files.readAllLines(file);
				          String store=this.productName+","+this.price+","+this.quantity+","+id;
				          lines.set(count-1,store);
				          // 
				          Files.write(file, lines);
				          Files.write(file, lines);
				          break;
					}
				}
			}
			
			for(int i=0;i<ProductManagement.getInstance().products.size();i++) {
				if(ProductManagement.getInstance().products.get(i).getProductName().equals(this.productName)) {
					ProductManagement.getInstance().products.remove(i);
				}
			}
			
			
			Product product=new Product(this.productName, this.price, this.quantity,id);
			ProductManagement.getInstance().products.add(product);
			
			
			
	          
	      } catch (IOException e) {
	      
//	          e.getMessage();
	      }
	}
}
