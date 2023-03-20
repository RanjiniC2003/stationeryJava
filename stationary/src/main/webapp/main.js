/**
 * 
 */


//---------------------------------------------------------------sign in ,sign up-----------------------------------------------------------------


function  UsersignUp(){
	 console.log("signUp")
	 var fn=document.getElementById("firstName").value;
	 var ln=document.getElementById("lastName").value;
	 var pd=document.getElementById("signUpPassword").value;
	 var pn=document.getElementById("phoneNumber").value;
	 
	 
	 var xhr=new XMLHttpRequest();
	 xhr.onreadystatechange=function(){
		 if(xhr.readyState==4){
			 if(this.status==200){
				var resp = JSON.parse(this.responseText); 
				if(resp.statusCode == 200){
	               alert(resp.detailedMessage);
	               window.location.href="./product.html";  
	            }	
		      }
		   }
		}
		
		let namePattern=/^[A-Za-z\. ]+$/;
		let lastPattern=/^[A-Za-z]+$/;
		let pattern = /^(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#\$%\^\&*\ )\(+=._-])(?=.*[0-9])[A-Za-z0-9!@#\$%\^\&*\ )\(+=._-]{8,}$/
		let phNum=/^[0-9]{10}$/;
		
		if(fn.trim()=="" || fn.replaceAll("."," ").trim()==""){
		     alert("first name must be filled out");
		}
		else if(fn.match(namePattern)){
			 if(ln.match(lastPattern)){ 
				 if(pd==""){
					 alert("password must be filled out");
				 }
				 else if(pd.match(pattern)){
			        if(pn==""){
						alert("Phone number must be filled out");
					}
					else if(pn.match(phNum)){
						xhr.open("POST","SignUp");
						xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
						xhr.send("firstName="+fn+"&lastName="+ln+"&password="+pd+"&phoneNumber="+pn);
					}
					else{
						alert("Invalid phone number");
					}
			        
		         }
				 else{
					 alert("Invalid Password...");
				 }
			 }
			 else{
				 alert("Invalid last name")
			 }
		}
		else{
			alert("Invalid first name");
		}	
		 
}
	
 
 
 function userSignIn(){
	 var num=document.getElementById("phoneNum").value;
	 var password=document.getElementById("signInPassword").value;
	 var xhr=new XMLHttpRequest();
	 xhr.onreadystatechange=function(){
		 if(xhr.readyState==4){
			 if(this.status==200){
				var resp = JSON.parse(this.responseText);
				if(resp.statusCode == 200){
					 console.log(resp.detailedMessage);	
					
					alert(resp.detailedMessage);
	                window.location.href="./product.html"; 
				}
				else if(num=="" && password==""){
					alert("Phone Number and password must be filled out");
				}
				else if(num==""){
					alert("Phone Number must be filled out");
				}
				else if(password==""){
					alert("password must be filled out");
				}
				
				else{	
					alert("Invalid Phone Number");
					document.getElementById("phoneNum").value="";
					document.getElementById("signInPassword").value="";
				}
			 }
		 }
	 }
	 xhr.open("POST","SignIn");
	 xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	 xhr.send("phoneNumber="+num+"&password="+password);
	 
 }
 
 

 function logOut(){
	 var xhr=new XMLHttpRequest();
	 xhr.onreadystatechange=function(){
		 if(xhr.readyState==4){
			 if(xhr.status==200){
				 window.location.href="./index.html";
			 }
		 }
	 }
	 
	 xhr.open("POST","logOut");
	 xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	 xhr.send();
 }
 
 
 var list="";
 
 function viewproduct(){
	 
	 var productList=document.getElementById("productList");
	 
	 var xhr=new XMLHttpRequest();
	 xhr.onreadystatechange=function(){
		 if(xhr.readyState==4){
			 if(xhr.status==200){
				 
				list=JSON.parse(this.responseText);
				var json=list;
				if(json.statusCode==200){
					
					if(json.role=="Admin"){
						var addProduct=document.getElementById("addProduct");
						var updateProduct=document.getElementById("updateProduct");
						addProduct.style.display="block";
						updateProduct.style.display="block";
					}
					else if(json.role=="Customer"){
						var addCart=document.getElementById("addCart");
						addCart.style.display="block";
					}
					if(json.productList.length>0){
						productList.innerHTML="";
						for(var i=0;i<json.productList.length;i++){
					
							var mainDiv=document.createElement("div");
							productList.appendChild(mainDiv);
							mainDiv.setAttribute("id","mainDiv"+json.productList[i].productId);
							mainDiv.classList.add("mainDiv");
							mainDiv.setAttribute("onclick","selectProduct('"+json.productList[i].productId+"')");
							
							if(json.role=="Admin"){
								mainDiv.style.pointerEvents="none";
							}
							
							var productName=document.createElement("p");
							mainDiv.appendChild(productName);
							productName.classList.add("productName");
							productName.textContent=json.productList[i].productName;
							productName.title=json.productList[i].productName;
							
							
							var price=document.createElement("p");
							mainDiv.appendChild(price);
							price.classList.add("price");
							price.textContent=json.productList[i].price;
							price.title=json.productList[i].price;
							
							var quentity=document.createElement("p");
							mainDiv.appendChild(quentity);
							quentity.classList.add("quentity");
							quentity.textContent=json.productList[i].quentity;
							quentity.title=json.productList[i].quentity;
							
					    }
				    }
					
				}
			 }
			 else if(xhr.status==301){
				window.location.href="./index.html";
			 }
			 
		 }
	 }
	 
	 
	 xhr.open("POST","Product/getProduct");
	 xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	 xhr.send();
 }
 
var stationaryContainer=document.getElementById("stationaryContainer"); 
var updateProductName=document.getElementById("updateProductName");
var updateProductPopUp=document.getElementById("updateProductPopUp");
function updateProduct(){
	
	var json=list;
	if(json.statusCode==200){
		
		updateProductPopUp.style.display="block";
		stationaryContainer.style.pointerEvents="none";
		stationaryContainer.style.filter="blur(3px)";
		
		if(json.productList.length>0){
			updateProductName.innerHTML="";
		   for(var i=0;i<json.productList.length;i++){
			  var option=document.createElement("option");
			  updateProductName.appendChild(option);
			  option.classList.add("option");
			  option.textContent=json.productList[i].productName;
			  option.setAttribute("value",json.productList[i].productName);  
		   }
		}
	}
}


function updateDatasInProduct(){
	var productName=updateProductName.value;
	var price=document.getElementById("updatePrice").value;
	var quantity=document.getElementById("updateQuantity").value;
	
	
	var xhr=new XMLHttpRequest();
	xhr.onreadystatechange=function(){
		if(xhr.readyState==4){
			if(xhr.status==200){
				console.log(this.responseText)
				var json=JSON.parse(this.responseText);
				if(json.statusCode==200){
					alert(json.detailedMessage);
					viewproduct();
					updateProductPopUp.style.display="none";
					stationaryContainer.style.pointerEvents="auto";
					stationaryContainer.style.filter="blur(0px)";
				}
			}
		}
	}
	
	if(price=="" || price<=0){
		alert("Invalid price");
	}
	else{
		if(quantity=="" || quantity<=0){
			alert("Invalid quentity");
		}
		else{
			xhr.open("POST","Product/updateProduct");
			xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
			xhr.send("productName="+productName+"&price="+price+"&quantity="+quantity);
		}
	}	
	
}

 
 function updateCancel(){
	updateProductPopUp.style.display="none";
	stationaryContainer.style.pointerEvents="auto";
	stationaryContainer.style.filter="blur(0px)";
 }
 
 
  
 var addProductPopUp=document.getElementById("addProductPopUp");
 function addProduct(){
	addProductPopUp.style.display="block";
	stationaryContainer.style.pointerEvents="none";
	stationaryContainer.style.filter="blur(3px)";
 }
 
 
 function addDatasInProduct(){
	 
	 var productName=document.getElementById("addProductName").value;
	 var price=document.getElementById("addPrice").value;
	 var quantity=document.getElementById("addQuantity").value;
	 
	 var xhr=new XMLHttpRequest();
	 xhr.onreadystatechange=function(){
		 if(xhr.readyState==4){
			 if(xhr.status==200){
				 var json=JSON.parse(xhr.responseText);
				 if(json.statusCode==200){
					alert(json.detailedMessage);
					addProductPopUp.style.display="none";
					stationaryContainer.style.pointerEvents="auto";
					stationaryContainer.style.filter="blur(0px)";
					viewproduct();
				 }
				 else{
			       alert(json.detailedMessage);
		         }
			 }
		 }
		
	 }
	 
	 
	 xhr.open("POST","Product/addProduct");
	 xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	 xhr.send("productName="+productName+"&price="+price+"&quantity="+quantity);
	 
	
 }
 
 function addCancel(){
	addProductPopUp.style.display="none";
	stationaryContainer.style.pointerEvents="auto";
	stationaryContainer.style.filter="blur(0px)";
 }
 
 
 var cartSelectList=document.getElementById("cartSelectList");
 var addCardPopUp=document.getElementById("addCardPopUp");
 
 var addCartJson="";
 function addCart(){
	 var xhr=new XMLHttpRequest();
	 xhr.onreadystatechange=function(){
		 if(xhr.readyState==4){
			 if(xhr.status==200){
				 console.log(this.responseText);
				 addCartJson=JSON.parse(xhr.responseText);
				 console.log("sdf"+addCartJson)
				 var json=addCartJson;
				 if(json.statusCode==200){
					 cartSelectList.innerHTML="";
					if(json.list.length==0){
						alert("You didn't choose any product");
					} 
					else{
						for(var i=0;i<json.list.length;i++){
							var cartDiv=document.createElement("div");
						    cartSelectList.appendChild(cartDiv);
						    cartDiv.classList.add("cartDiv");
						    
						    
						    var cartProductName=document.createElement("p");
						    cartDiv.appendChild(cartProductName);
						    cartProductName.classList.add("cartProductName");
						    cartProductName.textContent=json.list[i].productName;
						    cartProductName.title=json.list[i].productName;
						    
						    
						    var cartPrice=document.createElement("p");
						    cartDiv.appendChild(cartPrice);
						    cartPrice.classList.add("cartPrice");
						    cartPrice.textContent=json.list[i].price;
						    cartPrice.title=json.list[i].price;
						    
						    
						    var cartQuantity=document.createElement("input");
						    cartDiv.appendChild(cartQuantity);
						    cartQuantity.classList.add("cartQuantity");
						    cartQuantity.setAttribute("id",json.list[i].productId);
						    cartQuantity.setAttribute("type","number");
						    cartQuantity.setAttribute("value",0);
						    cartQuantity.textContent=json.list[i].quentity;
						    cartQuantity.title=json.list[i].quentity;
						    
						   
						   addCardPopUp.style.display="block";
						   stationaryContainer.style.pointerEvents="none";
						   stationaryContainer.style.filter="blur(3px)";
					   
					    } 
					}
					
				 }
			 }
		 }
     }
     
     xhr.open("POST","Product/viewCartList");
     xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
     xhr.send("arr="+JSON.stringify(arr));
     
 }
 
 var arr=[];
 
 function selectProduct(productId){
	 
	 arr.push(productId);
	 var selectDiv=document.getElementById("mainDiv"+productId);
	 
	 selectDiv.style.color="red";
	 
/*	 console.log(productId);	 
	 var xhr=new XMLHttpRequest();
	 xhr.onreadystatechange=function(){
		 if(this.readyState==4){
			 if(this.status==200){
				 var json=JSON.parse(this.responseText);
				 if(json.statusCode==200){
					 alert(json.detailedMessage);
				 }
			 }
		 }
	 }
	 
	 xhr.open("POST","Product/cartList");
	 xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	 xhr.send("productId="+productId);*/
 }
 function cartCancelButton(){
	  
	   
	   for(var i=0;i<list.productList.length;i++){
			  var selDiv=document.getElementById("mainDiv"+(i+1));
			  selDiv.style.color="#03a9f4";
	   }	
	   arr.length=0;
	   addCardPopUp.style.display="none";
	   stationaryContainer.style.pointerEvents="auto";
	   stationaryContainer.style.filter="blur(0px)";
 }
 
 function cartBackButton(){
	   addCardPopUp.style.display="none";
	   stationaryContainer.style.pointerEvents="auto";
	   stationaryContainer.style.filter="blur(0px)";
 }
 
 function cartAddButton(){
	  var array=[];
	  for(var i=0;i<list.productList.length;i++){
			  var selDiv=document.getElementById("mainDiv"+(i+1));
			  selDiv.style.color="#03a9f4";
	   }	
	  arr.length=0;
	  var check=true;
	  
	  for(var i=0;i<addCartJson.list.length;i++){
		  var quantityId=document.getElementById(addCartJson.list[i].productId).value;
		  
		  if(quantityId<0){
			  alert("Quantity can't be below 0");
			  check=false;
		  }
		  if(addCartJson.list[i].quentity<quantityId){
			  
			  alert("We don't have enough stock in "+addCartJson.list[i].productName+" ,Kindly re-enter it below "+addCartJson.list[i].quentity);
			  check=false;
		  }
		  else{
			  array.push(addCartJson.list[i].productId+"-"+quantityId+"-"+addCartJson.list[i].productName)
		  }
		 
		  
	  }
	  
	  
	   if(check){
		   var xhr=new XMLHttpRequest();
		   xhr.onreadystatechange=function(){
			   if(xhr.readyState==4){
				   if(xhr.status==200){
					   console.log(this.responseText)
					   var resp=JSON.parse(this.responseText);
					   if(resp.statusCode==200){
						   alert(resp.detailedMessage);
					   }
				   }
			   }
		   }
		   
		   xhr.open("POST","Product/byProduct");
		   xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
		   xhr.send("arr="+JSON.stringify(array));
		   
	   
		   addCardPopUp.style.display="none";
	       stationaryContainer.style.pointerEvents="auto";
	       stationaryContainer.style.filter="blur(0px)";
	   }
	 
	   
	   array.length=0;
 }
 
 
 
 