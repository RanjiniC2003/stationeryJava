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

public class BYProduct extends Thread {
	String productName;
	double price;
	int quantity;
	int updateQuantity;
	int id;
	
	public BYProduct(String productName,double price,int quantity,int updateQuantity) {
		this.productName=productName;
		this.price=price;
		this.quantity=quantity;
		this.updateQuantity=updateQuantity;
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
				          updateData(this.productName,this.price,this.quantity,this.updateQuantity,this.id,file,count,lines);
				          
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
	
	public synchronized static void updateData(String productName, double price, int quantity, int updateQuantity, int id, Path file, int count, List<String> lines) throws IOException {
		
		updateQuantity=updateQuantity-quantity;
		String store=productName+","+price+","+updateQuantity+","+id;
        lines.set(count-1,store);
        // 
        Files.write(file, lines);
        Files.write(file, lines);
        
	}
}
