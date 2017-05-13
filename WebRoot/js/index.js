/**
 * index.js
 */
 var orderCount=0;
 var forecastCount=0;
 var clickedmps;
 
 function addLoadEvent(func){
	 var oldLoad=window.onload;
	 if(typeof oldLoad!="function"){
		 window.onload=func;
	 }else{
		 window.onload=function(){
			 oldLoad();
			 func();
		 }
	 }
 }
 function viewmenu(){
		var user=JSON.parse(window.sessionStorage.getItem("user"));
		if(user.status=="销售员"){
			document.getElementById("menu2").style="display:none";
			document.getElementById("menu4").style="display:none";
			document.getElementById("menu5").style="display:none";
		}else if(user.status=="仓管员"){
			document.getElementById("menu1").style="display:none";
			document.getElementById("menu2").style="display:none";
			document.getElementById("menu3").style="display:none";
			document.getElementById("menu5").style="display:none";
		}
	}
addLoadEvent(viewmenu);
 function showUserInfo(){
	 var userstatus=document.getElementById("userstatus");
	 var username=document.getElementById("username");
	 var user=JSON.parse(window.sessionStorage.getItem("user"));
	 userstatus.innerHTML=""+user.status;
	 username.innerHTML=""+user.username;
 }
 function loadPersonalForm(){
	 var user=JSON.parse(window.sessionStorage.getItem("user"));
	 var personalform=document.getElementById("personalform");
	 var inputs=personalform.getElementsByTagName("input");
	 inputs[0].value=user.userid;
	 inputs[1].value=user.username;
	 inputs[2].value=user.account;
	 inputs[3].value="";
	 inputs[4].value=user.status;
}
 addLoadEvent(loadPersonalForm);
 
 function modifyUserSelf(event){
		event.stopPropagation();
		var personalform=document.getElementById("personalform");
		var inputs=personalform.getElementsByTagName("input");
		var xhr=new XMLHttpRequest();
		var userid=inputs[0].value;
		var username=inputs[1].value;
		var account=inputs[2].value;
		var password=inputs[3].value;
		var status=inputs[4].value;
		var requrl="ModifyUser?userid="+userid+"&username="+username+"&account="+account+"&password="+password+"&status="+status;
		xhr.open("get",requrl,false);
		xhr.send(null);
		var p=document.getElementById("modifyResult");
		p.innerHTML="修改成功";
		showUsers();
	 }
 
 /*获取待审订单及已审订单*/
 function getOrders(){
	 var orders=null;
	 var xhr=new XMLHttpRequest();
	 xhr.onreadystatechange=function(){
		 if(xhr.readyState==4&&xhr.status==200){
			 orders=JSON.parse(xhr.responseText);
		 }
	 }
	 xhr.open("get","QueryOrders",false);
	 xhr.send(null);
	 return orders;
 }
 
 function initOrderForm(){
	 var orderform=document.getElementById("orderform");
	 var selects=orderform.getElementsByTagName("select");
	 selects[0].innerHTML=getProductOption();
	 selects[1].innerHTML=getCustomerOption();
}
 function showOrders(){
	 var orders=getOrders();
	 var checkedtb=document.getElementsByClassName("checkedtb")[0];
	 checkedtb.innerHTML="<tr>"+
							"<th>序  号</th>"+
							"<th>订单号</th>"+
							"<th>产品号</th>"+
							"<th>客户号</th>"+
							"<th>订单量</th>"+
							"<th>交货期</th>"+
						 "</tr>";
	 orders.forEach(function(item,index,array){
		 var tr=document.createElement("tr");
		 tr.innerHTML="<td>"+(index+1)+"</td>"+
		 			  "<td class=\"orderid\">"+item.orderid+"</td>"+
		 			  "<td class=\"productid\">"+item.productid+"</td>"+
		 			  "<td class=\"customerid\">"+item.customerid+"</td>"+
		 			  "<td class=\"orderquantity\">"+item.orderquantity+"</td>"+
		 			  "<td class=\"deliverydate\">"+item.deliverydate+"</td>";
		tr.onclick=toform;
		checkedtb.appendChild(tr);
	 }); 
	 initOrderForm();
 }
 
 function toform(event){
	 event.stopPropagation();
	 var orderform=document.getElementById("orderform");
	 var tds=this.getElementsByTagName("td");
	 var selects=orderform.getElementsByTagName("select");
	 var inputs=orderform.getElementsByTagName("input");
	 setSelected(selects[0],tds[2].innerHTML);
	 setSelected(selects[1],tds[3].innerHTML);
	 inputs[0].value=tds[1].innerHTML;
	 inputs[1].value=tds[4].innerHTML;
	 inputs[2].value=tds[5].innerHTML;
 }
 function setSelected(select,value){
	 var options=select.getElementsByTagName("option");
	 for(var i=0,len=options.length;i<len;i++){
		 if(options[i].value==value){
			 options[i].setAttribute("selected","selected");
		 }
	 }
 }
 
 function modifyOrder(event){
	 event.stopPropagation();
	 var orderform=document.getElementById("orderform");
	 var selects=orderform.getElementsByTagName("select");
	 var inputs=orderform.getElementsByTagName("input");
	 var orderid=inputs[0].value;
	 var productid=selects[0].value;
	 var customerid=selects[1].value;
	 var orderquantity=inputs[1].value;
	 var deliverydate=inputs[2].value
	 validate(productid,deliverydate,function(){
		 var xhr=new XMLHttpRequest();
		 var requrl="ModifyOrder"+"?orderid="+orderid+"&productid="+productid+"&customerid="+customerid+"&orderquantity="+orderquantity+"&deliverydate="+deliverydate;
		 xhr.open("get",requrl,false);
		 xhr.send(null);
		 showOrders();
		 clearOrderform();
	 }); 
 }
 function clearOrderform(){
	 event.stopPropagation();
	 var orderform=document.getElementById("orderform");
	 var selects=orderform.getElementsByTagName("select");
	 firstSelected(selects[0]);
	 firstSelected(selects[1]);
	 var inputs=orderform.getElementsByTagName("input");
	 inputs[0].value="";
	 inputs[1].value="";
	 inputs[2].value="";
 }
 function firstSelected(select){
	 var options=select.getElementsByTagName("option");
	 for(var i=0,len=options.length;i<len;i++){
		options[i].removeAttribute("selected");
	 }
	 options[0].setAttribute("selected","selected")
 }
 
 function deleteOrder(){
	 event.stopPropagation(); 
	 var orderform=document.getElementById("orderform");
	 var selects=orderform.getElementsByTagName("select");
	 var inputs=orderform.getElementsByTagName("input");
	 var orderid=inputs[0].value;
	 var productid=selects[0].value;
	 var deliverydate=inputs[2].value
	 validate(productid,deliverydate,function(){
		 var xhr=new XMLHttpRequest();
		 var requrl="DeleteOrder"+"?orderid="+orderid;
		 xhr.open("get",requrl,false);
		 xhr.send(null);
		 showOrders();
		 clearOrderform();
	 }); 
	 
 }
   
//~
 /*获取预测单*/
 function getForecasts(){
	 var forecasts=null;
	 var xhr=new XMLHttpRequest();
	 xhr.onreadystatechange=function(){
		 if(xhr.readyState==4&&xhr.status==200){
			 forecasts=JSON.parse(xhr.responseText);
		 }
	 }
	 xhr.open("get","QueryForecasts",false);
	 xhr.send(null);
	 return forecasts;
 }
 function initForecastForm(){
	 var forecastform=document.getElementById("forecastform");
	 var selects=forecastform.getElementsByTagName("select");
	 selects[0].innerHTML=getProductOption();
	 selects[1].innerHTML=getCustomerOption();
}
 function showForecasts(){
	 var forecasts=getForecasts();
	 var checkedtb=document.getElementsByClassName("checkedtb")[1];
	 checkedtb.innerHTML="<tr>"+
							"<th>序  号</th>"+
							"<th>预测号</th>"+
							"<th>产品号</th>"+
							"<th>客户号</th>"+
							"<th>订单量</th>"+
							"<th>交货期</th>"+
						 "</tr>";
	 forecasts.forEach(function(item,index,array){
		 var tr=document.createElement("tr");
		 tr.innerHTML="<td>"+(index+1)+"</td>"+
		 			  "<td class=\"forecasts\">"+item.forecastid+"</td>"+
		 			  "<td class=\"productid\">"+item.productid+"</td>"+
		 			  "<td class=\"customerid\">"+item.customerid+"</td>"+
		 			  "<td class=\"forecastquantity\">"+item.forecastquantity+"</td>"+
		 			  "<td class=\"deliverydate\">"+item.deliverydate+"</td>";
		tr.onclick=toForecastform;
		checkedtb.appendChild(tr);
	 }); 
	 initForecastForm();
 }
 
 function toForecastform(event){
	 event.stopPropagation();
	 var forecastform=document.getElementById("forecastform");
	 var tds=this.getElementsByTagName("td");
	 var selects=forecastform.getElementsByTagName("select");
	 var inputs=forecastform.getElementsByTagName("input");
	 setSelected(selects[0],tds[2].innerHTML);
	 setSelected(selects[1],tds[3].innerHTML);
	 inputs[0].value=tds[1].innerHTML;
	 inputs[1].value=tds[4].innerHTML;
	 inputs[2].value=tds[5].innerHTML;
 }
 function modifyForecast(event){
	 event.stopPropagation();
	 var forecastform=document.getElementById("forecastform");
	 var selects=forecastform.getElementsByTagName("select");
	 var inputs=forecastform.getElementsByTagName("input");
	 var forecastid=inputs[0].value;
	 var productid=selects[0].value;
	 var customerid=selects[1].value;
	 var forecastquantity=inputs[1].value;
	 var deliverydate=inputs[2].value
	 validate(productid,deliverydate,function(){
		 var xhr=new XMLHttpRequest();
		 var requrl="ModifyForecast"+"?forecastid="+forecastid+"&productid="+productid+"&customerid="+customerid+"&forecastquantity="+forecastquantity+"&deliverydate="+deliverydate;
		 xhr.open("get",requrl,false);
		 xhr.send(null);
		 showForecasts();
		 clearForecastform();
	 }); 
	 
 }
 function clearForecastform(){
	 event.stopPropagation();
	 var forecastform=document.getElementById("forecastform");
	 var selects=forecastform.getElementsByTagName("select");
	 firstSelected(selects[0]);
	 firstSelected(selects[1]);
	 var inputs=forecastform.getElementsByTagName("input");
	 inputs[0].value="";
	 inputs[1].value="";
	 inputs[2].value="";
 }
 function deleteForecast(){
	 event.stopPropagation();
	 
	 var forecastform=document.getElementById("forecastform");
	 var selects=forecastform.getElementsByTagName("select");
	 var inputs=forecastform.getElementsByTagName("input");
	 var forecastid=inputs[0].value;
	 var productid=selects[0].value;
	 var deliverydate=inputs[2].value
	 validate(productid,deliverydate,function(){
		 var xhr=new XMLHttpRequest();
		 var requrl="DeleteForecast"+"?forecastid="+forecastid;
		 xhr.open("get",requrl,false);
		 xhr.send(null);
		 showForecasts();
		 clearForecastform();
	 }); 
	
 }
   
//~
 
 //customer
 function getCustomers(){
	 var customers=null;
	 var xhr=new XMLHttpRequest();
	 xhr.onreadystatechange=function(){
		if(xhr.readyState==4&&xhr.status==200){
			var res=xhr.responseText;
			customers=JSON.parse(res);
		}
	}
	var requrl="QueryCustomers";
	xhr.open("get",requrl,false);
	xhr.send(null);
	return customers;
 }

function addCustomer(event){
	event.stopPropagation();
	var customerform=document.getElementById("customerform");
	var inputs=customerform.getElementsByTagName("input");
	var xhr=new XMLHttpRequest();
	var customerid=0;
	var customername=inputs[1].value;
	var contacts=inputs[2].value;
	var tel=inputs[3].value;
	var email=inputs[4].value;
	var address=inputs[5].value;
	var requrl="AddCustomer?customerid="+customerid+"&customername="+customername+"&contacts="+contacts+"&tel="+tel+"&email="+email+"&address="+address;
	xhr.open("get",requrl,false);
	xhr.send(null);	
	showCustomers();
	clearCustomerInput(inputs);
}
	 
 
function modifyCustomer(event){
	event.stopPropagation();
	var customerform=document.getElementById("customerform");
	var inputs=customerform.getElementsByTagName("input");
	var xhr=new XMLHttpRequest();
	var customerid=inputs[0].value;
	var customername=inputs[1].value;
	var contacts=inputs[2].value;
	var tel=inputs[3].value;
	var email=inputs[4].value;
	var address=inputs[5].value;
	var requrl="ModifyCustomer?customerid="+customerid+"&customername="+customername+"&contacts="+contacts+"&tel="+tel+"&email="+email+"&address="+address;
	xhr.open("get",requrl,false);
	xhr.send(null);	
	showCustomers();
	var cancelbtn=customerform.getElementsByTagName("a")[2];
	cancelbtn.click();
 }
function deleteCustomer(event){
	event.stopPropagation();
	var customerform=document.getElementById("customerform");
	var inputs=customerform.getElementsByTagName("input");
	var xhr=new XMLHttpRequest();
	var customerid=inputs[0].value;
	var requrl="DeleteCustomer?customerid="+customerid;
	xhr.open("get",requrl,false);
	xhr.send(null);	
	showCustomers();
	var cancelbtn=customerform.getElementsByTagName("a")[2];
	cancelbtn.click();
 }
 
 function mkCustomerModify(event){
	 event.stopPropagation();
	 var customerform=document.getElementById("customerform");
	 var inputs=customerform.getElementsByTagName("input");
	 var tds=this.getElementsByTagName("td");
	 inputs[0].value=tds[1].innerHTML;
	 inputs[1].value=tds[2].innerHTML;
	 inputs[2].value=tds[3].innerHTML;
	 inputs[3].value=tds[4].innerHTML;
	 inputs[4].value=tds[5].innerHTML;
	 inputs[5].value=tds[6].innerHTML;
	 var btn=customerform.getElementsByClassName("btn")[0];
	 btn.style="height:30px";
	 btn.innerHTML="<a href=\"javascript:void(0)\" style=\"width:150px;margin-left:3px; margin-right:3px;\" onclick=\"modifyCustomer(event)\">修改</a>"+
	 			   "<a href=\"javascript:void(0)\" style=\"width:150px;margin-left:3px; margin-right:3px;\" onclick=\"deleteCustomer(event)\">删除</a>"+
	 			   "<a href=\"javascript:void(0)\" style=\"width:150px;margin-left:3px; margin-right:3px;\" onclick=\"cancelCustomerModify(event)\">取消</a>";
 }
 
 
 function clearCustomerInput(inputs){
	 inputs[0].value="";
	 inputs[1].value="";
	 inputs[2].value="";
	 inputs[3].value="";
	 inputs[4].value="";
	 inputs[5].value="";
 }
 function cancelCustomerModify(event){
	 event.stopPropagation();
	 var customerform=document.getElementById("customerform");
	 var inputs=customerform.getElementsByTagName("input");
	 clearCustomerInput(inputs);
	 var btn=customerform.getElementsByClassName("btn")[0];
	 btn.innerHTML="<a href=\"javascript:void(0)\" style=\"width:400px; margin-left:61px;\" onclick=\"addCustomer(event)\">添加</a>";
 }

 function showCustomers(){
	 var customers=getCustomers();
	 var customertb=document.getElementsByClassName("customertb")[0];
	 customertb.innerHTML="<tr>"+
							"<th>序  号</th>"+
							"<th>客户号</th>"+
							"<th>客户名</th>"+
							"<th>联系人</th>"+
							"<th>电  话</th>"+
							"<th>E-mail</th>"+
							"<th>地  址</th>"+
						  "</tr>";
	 customers.forEach(function(item,index,array){
		 var tr=document.createElement("tr");
		 tr.innerHTML="<td>"+(index+1)+"</td>"+
					  "<td class=\"customerid\">"+item.customerid+"</td>"+
					  "<td class=\"customername\">"+item.customername+"</td>"+
					  "<td class=\"contacts\">"+item.contacts+"</td>"+
					  "<td class=\"tel\">"+item.tel+"</td>"+
					  "<td class=\"email\">"+item.email+"</td>"+
					  "<td class=\"address\">"+item.address+"</td>";
		 tr.onclick=mkCustomerModify;
		 customertb.appendChild(tr);
	 });
	
 }
 /**/

 function getUsers(){
	 var users=null;
	 var xhr=new XMLHttpRequest();
	 xhr.onreadystatechange=function(){
		if(xhr.readyState==4&&xhr.status==200){
			var res=xhr.responseText;
			users=JSON.parse(res);
		}
	}
	var requrl="QueryUsers";
	xhr.open("get",requrl,false);
	xhr.send(null);
	return users;
 }
 
 function mkUserModify(event){
	 event.stopPropagation();
	 var userform=document.getElementById("userform");
	 var inputs=userform.getElementsByTagName("input");
	 var select=userform.getElementsByTagName("select")[0];
	 var tds=this.getElementsByTagName("td");
	 inputs[0].value=tds[1].innerHTML;
	 inputs[1].value=tds[2].innerHTML;
	 inputs[2].value=tds[3].innerHTML;
	 inputs[3].value=tds[4].innerHTML;
	 setSelected(select,tds[5].innerHTML);
	 var btn=userform.getElementsByClassName("btn")[0];
	 btn.style="height:30px";
	 btn.innerHTML="<a href=\"javascript:void(0)\" style=\"width:150px;margin-left:3px; margin-right:3px;\" onclick=\"modifyUser(event)\">修改</a>"+
	 			   "<a href=\"javascript:void(0)\" style=\"width:150px;margin-left:3px; margin-right:3px;\" onclick=\"deleteUser(event)\">删除</a>"+
	 			   "<a href=\"javascript:void(0)\" style=\"width:150px;margin-left:3px; margin-right:3px;\" onclick=\"cancelUserModify(event)\">取消</a>";
 }
 
 function modifyUser(event){
		event.stopPropagation();
		var userform=document.getElementById("userform");
		var inputs=userform.getElementsByTagName("input");
		var select=userform.getElementsByTagName("select")[0];
		var xhr=new XMLHttpRequest();
		var userid=inputs[0].value;
		var username=inputs[1].value;
		var account=inputs[2].value;
		var password=inputs[3].value;
		var status=select.value;
		var requrl="ModifyUser?userid="+userid+"&username="+username+"&account="+account+"&password="+password+"&status="+status;
		xhr.open("get",requrl,false);
		xhr.send(null);	
		showUsers();
		var cancelbtn=userform.getElementsByTagName("a")[2];
		cancelbtn.click();
	 }
 function deleteUser(event){
		event.stopPropagation();
		var userform=document.getElementById("userform");
		var inputs=userform.getElementsByTagName("input");
		var xhr=new XMLHttpRequest();
		var userid=inputs[0].value;
		var requrl="DeleteUser?userid="+userid;
		xhr.open("get",requrl,false);
		xhr.send(null);	
		showUsers();
		var cancelbtn=userform.getElementsByTagName("a")[2];
		cancelbtn.click();
	 }
 
 function cancelUserModify(event){
	 event.stopPropagation();
	 var userform=document.getElementById("userform");
	 var inputs=userform.getElementsByTagName("input");
	 var select=userform.getElementsByTagName("select")[0];
	 clearUserInput(inputs,select);
	 var btn=userform.getElementsByClassName("btn")[0];
	 btn.innerHTML="<a href=\"javascript:void(0)\" style=\"width:400px; margin-left:55px;\" onclick=\"addUser(event)\">添加</a>";
 }
 function addUser(event){
		event.stopPropagation();
		var userform=document.getElementById("userform");
		var inputs=userform.getElementsByTagName("input");
		var select=userform.getElementsByTagName("select")[0];
		var xhr=new XMLHttpRequest();
		var userid=0;
		var username=inputs[1].value;
		var account=inputs[2].value;
		var password=inputs[3].value;
		var status=select.value;
		var requrl="AddUser?userid="+userid+"&username="+username+"&account="+account+"&password="+password+"&status="+status;
		xhr.open("get",requrl,false);
		xhr.send(null);	
		showUsers();
		clearUserInput(inputs,select);
	}
 
 function clearUserInput(inputs,select){
	 inputs[0].value="";
	 inputs[1].value="";
	 inputs[2].value="";
	 inputs[3].value="";
	 firstSelected(select);
 }
 function showUsers(){
	 var users=getUsers();
	 var usertb=document.getElementsByClassName("usertb")[0];
	 usertb.innerHTML="<tr>"+
					  "<th>序  号</th>"+
					  "<th>用户号</th>"+
					  "<th>用户名</th>"+
					  "<th>账户</th>"+
					  "<th>密码</th>"+
					  "<th>身份</th>"+
					  "</tr>";
	 users.forEach(function(item,index,array){
	 var tr=document.createElement("tr");
	 tr.innerHTML="<td>"+(index+1)+"</td>"+
				  "<td class=\"userid\">"+item.userid+"</td>"+
				  "<td class=\"username\">"+item.username+"</td>"+
				  "<td class=\"account\">"+item.account+"</td>"+
				  "<td class=\"password\">"+item.password+"</td>"+
				  "<td class=\"status\">"+item.status+"</td>";
		 tr.onclick=mkUserModify;
		 usertb.appendChild(tr);
	 });
 }
 
 /*producttb*/
 
 function getProducts(){
	 var products=null;
	 var xhr=new XMLHttpRequest();
	 xhr.onreadystatechange=function(){
		if(xhr.readyState==4&&xhr.status==200){
			var res=xhr.responseText;
			products=JSON.parse(res);
		}
	}
	var requrl="QueryProducts";
	xhr.open("get",requrl,false);
	xhr.send(null);
	return products;
 }
 
 function mkProductModify(event){
	 event.stopPropagation();
	 var productform=document.getElementById("productform");
	 var inputs=productform.getElementsByTagName("input");
	 var tds=this.getElementsByTagName("td");
	 inputs[0].value=tds[1].innerHTML;
	 inputs[1].value=tds[2].innerHTML;
	 inputs[2].value=tds[3].innerHTML;
	 inputs[3].value=tds[4].innerHTML;
	 var btn=productform.getElementsByClassName("btn")[0];
	 btn.style="height:30px";
	 btn.innerHTML="<a href=\"javascript:void(0)\" style=\"width:150px;margin-left:3px; margin-right:3px;\" onclick=\"modifyProduct(event)\">修改</a>"+
	 			   "<a href=\"javascript:void(0)\" style=\"width:150px;margin-left:3px; margin-right:3px;\" onclick=\"deleteProduct(event)\">删除</a>"+
	 			   "<a href=\"javascript:void(0)\" style=\"width:150px;margin-left:3px; margin-right:3px;\" onclick=\"cancelProductModify(event)\">取消</a>";
 }
 
 function modifyProduct(event){
		event.stopPropagation();
		var productform=document.getElementById("productform");
		var inputs=productform.getElementsByTagName("input");
		var xhr=new XMLHttpRequest();
		var productid=inputs[0].value;
		var productname=inputs[1].value;
		var spec=inputs[2].value;
		var leadtime=inputs[3].value;
		var requrl="ModifyProduct?productid="+productid+"&productname="+productname+"&spec="+spec+"&leadtime="+leadtime;
		xhr.open("get",requrl,false);
		xhr.send(null);	
		showProducts();
		var cancelbtn=productform.getElementsByTagName("a")[2];
		cancelbtn.click();
	 }
 function deleteProduct(event){
		event.stopPropagation();
		var productform=document.getElementById("productform");
		var inputs=productform.getElementsByTagName("input");
		var xhr=new XMLHttpRequest();
		var productid=inputs[0].value;
		var requrl="DeleteProduct?productid="+productid;
		xhr.open("get",requrl,false);
		xhr.send(null);	
		showProducts();
		var cancelbtn=productform.getElementsByTagName("a")[2];
		cancelbtn.click();
	 }   
 
 function cancelProductModify(event){
	 event.stopPropagation();
	 var productform=document.getElementById("productform");
	 var inputs=productform.getElementsByTagName("input");
	 clearProductInput(inputs);
	 var btn=productform.getElementsByClassName("btn")[0];
	 btn.innerHTML="<a href=\"javascript:void(0)\" style=\"width:400px; margin-left:55px;\" onclick=\"addProduct(event)\">添加</a>";
 }
 function addProduct(event){
		event.stopPropagation();
		var productform=document.getElementById("productform");
		var inputs=productform.getElementsByTagName("input");
		var xhr=new XMLHttpRequest();
		var productid=0;
		var productname=inputs[1].value;
		var spec=inputs[2].value;
		var leadtime=inputs[3].value;
		var requrl="AddProduct?productid="+productid+"&productname="+productname+"&spec="+spec+"&leadtime="+leadtime;
		xhr.open("get",requrl,false);
		xhr.send(null);	
		showProducts();
		clearProductInput(inputs);
	}
 
 function clearProductInput(inputs){
	 inputs[0].value="";
	 inputs[1].value="";
	 inputs[2].value="";
	 inputs[3].value="";
 }
 function showProducts(){
	 var products=getProducts();
	 var producttb=document.getElementsByClassName("producttb")[0];
	 producttb.innerHTML="<tr>"+
					  "<th>序  号</th>"+
					  "<th>产品号</th>"+
					  "<th>产品名</th>"+
					  "<th>规格</th>"+
					  "<th>提前期</th>"+
					  "</tr>";
	 products.forEach(function(item,index,array){
	 var tr=document.createElement("tr");
	 tr.innerHTML="<td>"+(index+1)+"</td>"+
				  "<td class=\"productid\">"+item.productid+"</td>"+
				  "<td class=\"productname\">"+item.productname+"</td>"+
				  "<td class=\"spec\">"+item.spec+"</td>"+
				  "<td class=\"leadtime\">"+item.leadtime+"</td>";
		 tr.onclick=mkProductModify;
		 producttb.appendChild(tr);
	 });
 }
 
function showStocks(){
	var products=getProducts();
	 var stocktb=document.getElementsByClassName("stocktb")[0];
	 stocktb.innerHTML="<tr>"+
	 						 "<th>序号</th>"+
						     "<th>产品号</th>"+
						     "<th>产品名</th>"+
						     "<th>可用库存</th>"+
						     "<th>安全库存</th>"+
					     "</tr>";
	 products.forEach(function(item,index,array){
		 var tr=document.createElement("tr");
		 tr.innerHTML="<td>"+(index+1)+"</td>"+
					  "<td class=\"productid\">"+item.productid+"</td>"+
					  "<td class=\"productname\">"+item.productname+"</td>"+
					  "<td class=\"spec\">"+item.stock+"</td>"+
					  "<td class=\"leadtime\">"+item.safestock+"</td>";
		 tr.onclick=mkStockModify;
		 stocktb.appendChild(tr);
	 });
}
 
function mkStockModify(event){
	event.stopPropagation();
	var stockform=document.getElementById("stockform");
	var inputs=stockform.getElementsByTagName("input");
	var tds=this.getElementsByTagName("td");
	inputs[0].value=tds[1].innerHTML;
	inputs[1].value=tds[2].innerHTML;
    inputs[2].value=tds[3].innerHTML;
	inputs[3].value=tds[4].innerHTML;
}
function clearStockform(){
	var stockform=document.getElementById("stockform");
	var inputs=stockform.getElementsByTagName("input");
	inputs[0].value="";
	inputs[1].value="";
    inputs[2].value="";
	inputs[3].value="";
}
function modifyStock(event){
	event.stopPropagation();
	var xhr=new XMLHttpRequest();
	var stockform=document.getElementById("stockform");
	var inputs=stockform.getElementsByTagName("input");
	var productid=inputs[0].value;
	var stock=inputs[2].value;
	var safestock=inputs[3].value;
	var requrl="ModifyStock"+"?productid="+productid+"&stock="+stock+"&safestock="+safestock;
	xhr.open("get",requrl,false);
	xhr.send(null);	
	showStocks();
	clearStockform();
}
function refreshStock(event){
	showStocks();
}
 addLoadEvent(showUserInfo);
 addLoadEvent(showCustomers);
 addLoadEvent(showUsers);
 addLoadEvent(showProducts);
 addLoadEvent(showOrders);
 addLoadEvent(showForecasts);
 addLoadEvent(showStocks);
 
 
 /**/
 function expand(event){
	 event.stopPropagation();
	 var target=event.target;
	 var expTarget=nextElement(target);
	 if(expTarget.className=="hidden"){
		 expTarget.className="";
		 target.parentNode.className="expanded";
	 }else{
		 expTarget.className="hidden";
		 target.parentNode.className="";
	 }
	 
 }
 function nextElement(node){
	 var sibling=node.nextSibling;
	 while(sibling.nodeType!==1){
		 sibling=sibling.nextSibling;
	 }
	 return sibling;
 }
 
function nav(){
	 event.stopPropagation();
	 var target=event.target;
	 var id=target.id;
	 var desId;
	switch(id){
		case "0":desId="personalinfo";break;
		case "1":desId="ordercheck";break;
		case "2":desId="forecastcheck";break;
		case "3":desId="announcement";break;
		case "4":desId="usermanage";break;
		case "5":desId="productinfo";break;
		case "6":desId="ordermanage";break;
		case "7":desId="forecastmanage";break;
		case "8":desId="customerinfo";break;
		case "9":desId="stockmanage";break;
		case "10":desId="receiptmanage";break;
		case "11":desId="allmps";break;
		case "12":desId="newmps";break;
	}
	var destination=document.getElementById(desId);
	var nohidden=document.getElementsByClassName("nohidden")[0];
	removeClass(nohidden,"nohidden");
	addClass(nohidden,"hidden");
	
	removeClass(destination,"hidden");
	addClass(destination,"nohidden");
} 


function hasClass(obj, cls) {
    return obj.className.match(new RegExp('(\\s|^)' + cls + '(\\s|$)'));  
}  
  
function addClass(obj, cls) {  
    if (!this.hasClass(obj, cls)) obj.className += " " + cls;  
}  
  
function removeClass(obj, cls) {  
    if (hasClass(obj, cls)) {  
        var reg = new RegExp('(\\s|^)' + cls + '(\\s|$)');  
        obj.className = obj.className.replace(reg, ' ');  
    }  
}


/*批量添加之订单*/
function getProductOption(){
	var optionStr="";
	var options=getProducts();
	options.forEach(function(item,index,array){
		optionStr+="<option value="+item.productid+">"+item.productname+" "+item.spec+"</option>"
	})
	return optionStr;
}
function getCustomerOption(){
	var optionStr="";
	var options=getCustomers();
	options.forEach(function(item,index,array){
		optionStr+="<option value="+item.customerid+">"+item.customername+"</option>"
	})
	return optionStr;
}
function addOrderRow(event){
	orderCount++;
	event.stopPropagation();
	var productOption=getProductOption();
	var customerOption=getCustomerOption();
	var orderaddtable=document.getElementsByClassName("ordertb")[0];
	var newrow=document.createElement("tr");
	var innerHTMLString="<td>"+orderCount+"</td>"+
						"<td><select name=\"productid\">"+productOption+"</select></td>"+
						"<td><select name=\"customerid\">"+customerOption+"</select></td>"+
						"<td><input type=\"text\" name=\"orderquantity\"></td>"+
						"<td><input type=\"date\" name=\"deliveydate\"></td>";
	newrow.innerHTML=innerHTMLString;
	orderaddtable.appendChild(newrow);
}
function submitOrder(event){
	event.stopPropagation();
	var orderaddtable=document.getElementsByClassName("ordertb")[0];
	var trs=orderaddtable.getElementsByTagName("tr");
	for(var i=1,len=trs.length;i<len;i++){
		var selects=trs[i].getElementsByTagName("select");
		var inputs=trs[i].getElementsByTagName("input");
		var productid=selects[0].value;
		var customerid=selects[1].value;
		var orderquantity=inputs[0].value;
		var deliverydate=inputs[1].value;
		var requrl="AddOrder?"+"productid="+productid+"&customerid="+customerid+"&orderquantity="+orderquantity+"&deliverydate="+deliverydate;
		validate(productid,deliverydate,function(){
			var xhr=new XMLHttpRequest();
			xhr.open("get",requrl,false);
			xhr.send(null);
		});
		
	}
}
function noRows(event){
	event.stopPropagation();
	var orderaddtable=document.getElementsByClassName("ordertb")[0];
	orderaddtable.innerHTML="<tr>"+
							"<th>序  号</th>"+
							"<th>产品号</th>"+
							"<th>客户号</th>"+
							"<th>订单量</th>"+
							"<th>交货期</th>"+
						    "</tr>";
	orderCount=0;
}

/*批量添加之预测*/ 
function addForecastRow(event){
	forecastCount++;
	event.stopPropagation();
	var productOption=getProductOption();
	var customerOption=getCustomerOption();
	var forecastaddtable=document.getElementsByClassName("forecasttb")[0];
	var newrow=document.createElement("tr");
	var innerHTMLString="<td>"+forecastCount+"</td>"+
						"<td><select name=\"productid\">"+productOption+"</select></td>"+
						"<td><select name=\"customerid\">"+customerOption+"</select></td>"+
						"<td><input type=\"text\" name=\"forecastquantity\"></td>"+
						"<td><input type=\"date\" name=\"deliveydate\"></td>";
	newrow.innerHTML=innerHTMLString;
	forecastaddtable.appendChild(newrow);
}

function submitForecast(event){
	event.stopPropagation();
	var forecastaddtable=document.getElementsByClassName("forecasttb")[0];
	var trs=forecastaddtable.getElementsByTagName("tr");
	for(var i=1,len=trs.length;i<len;i++){
		var selects=trs[i].getElementsByTagName("select");
		var inputs=trs[i].getElementsByTagName("input");
		var productid=selects[0].value;
		var customerid=selects[1].value;
		var forecastquantity=inputs[0].value;
		var deliverydate=inputs[1].value;
		var requrl="AddForecast?"+"productid="+productid+"&customerid="+customerid+"&forecastquantity="+forecastquantity+"&deliverydate="+deliverydate;
		validate(productid,deliverydate,function(){
			var xhr=new XMLHttpRequest();
			xhr.open("get",requrl,false);
			xhr.send(null);
		});
		
	}
}
function noRows2(event){
	event.stopPropagation();
	var forecastaddtable=document.getElementsByClassName("forecasttb")[0];
	forecastaddtable.innerHTML="<tr>"+
							"<th>序  号</th>"+
							"<th>产品号</th>"+
							"<th>客户号</th>"+
							"<th>订单量</th>"+
							"<th>交货期</th>"+
						    "</tr>";
	forecastCount=0;
}
/**/

/**/
function mkproduct(){
	var productOption=getProductOption();
	var newmpscontent=document.getElementById("newmps");
	var productSelect=newmpscontent.getElementsByTagName("select")[0];
	productSelect.innerHTML=productOption;
}
function afterCreate(select,inputs,key){
	firstSelected(select);
	inputs[0].value="";
	inputs[1].value="";
	inputs[2].value="";
	inputs[3].value="";
	var span=document.getElementById("newmpsInfo");
	if(key=="1"){
		span.innerHTML="新建成功";
	}else{
		span.innerHTML="新建失败(已创建该MPS或时界分割不合理)";
	}

	
}
function createmps(event){
	event.stopPropagation();
	var key="0";
	var newmpscontent=document.getElementById("newmps");
	var productSelect=newmpscontent.getElementsByTagName("select")[0];
	var inputs=newmpscontent.getElementsByTagName("input");
	var productid=productSelect.value;
	var ph=inputs[0].value;
	var lotsize=inputs[1].value;
	var rf=inputs[2].value;
	var sf=inputs[3].value;
	if(sf>=rf){
		var xhr=new XMLHttpRequest();
		var requrl="AddMPS"+"?productid="+productid+"&ph="+ph+"&lotsize="+lotsize+"&rf="+rf+"&sf="+sf;
		xhr.onreadystatechange=function(){
			if(xhr.readyState==4&&xhr.status==200){
				key=xhr.responseText;
			}
		}
		xhr.open("get",requrl,false);
		xhr.send(null);
	}
	afterCreate(productSelect,inputs,key);
}
addLoadEvent(mkproduct);
/**///~~~

/**/
function getMPSes(){
	 var mpses=null;
	 var xhr=new XMLHttpRequest();
	 xhr.onreadystatechange=function(){
		if(xhr.readyState==4&&xhr.status==200){
			var res=xhr.responseText;
			mpses=JSON.parse(res);
		}
	}
	var requrl="QueryMPSes";
	xhr.open("get",requrl,false);
	xhr.send(null);
	return mpses;
}
function getProductById(id){
	var product=null;
	var xhr=new XMLHttpRequest();
	xhr.onreadystatechange=function(){
		if(xhr.readyState==4&&xhr.status==200){
			var res=xhr.responseText;
			product=JSON.parse(res);
		}
	}
	xhr.open("get","GetProductById?productid="+id,false);
	xhr.send(null);
	return product;
}

function getMPSById(id){
	var plans=null;
	var xhr=new XMLHttpRequest();
	xhr.onreadystatechange=function(){
		if(xhr.readyState==4&&xhr.status==200){
			var response=xhr.responseText;
			plans=JSON.parse(response);
		}
	}
	xhr.open("get","GetMPSById?id="+id,false);
	xhr.send(null);
	return plans;
}

function showDetail(){
	var mpsid=this.getElementsByTagName("td")[1].innerHTML;
	clickedmps=mpsid;
	var plans=getMPSById(mpsid);
	var detailtb=document.getElementById("detailtb");
	detailtb.innerHTML="";
	var tr=[];
	tr[0]=document.createElement("tr");
	tr[0].innerHTML="<td>类别</td>";
	
	tr[1]=document.createElement("tr");
	tr[1].innerHTML="<td>预测量</td>";
	
	tr[2]=document.createElement("tr");
	tr[2].innerHTML="<td>订单量</td>";
	
	tr[3]=document.createElement("tr");
	tr[3].innerHTML="<td>毛需求</td>";
	
	tr[4]=document.createElement("tr");
	tr[4].innerHTML="<td>pabI</td>";
	
	tr[5]=document.createElement("tr");
	tr[5].innerHTML="<td>pab</td>";
	
	tr[6]=document.createElement("tr");
	tr[6].innerHTML="<td>净需求</td>";
	
	tr[7]=document.createElement("tr");
	tr[7].innerHTML="<td>计划量</td>";
	
	tr[8]=document.createElement("tr");
	tr[8].innerHTML="<td>por</td>";
	
	tr[9]=document.createElement("tr");
	tr[9].innerHTML="<td>atp</td>";
	
	var len=plans.length;
	
	for(var j=0;j<len;j++){
		var td=[];
		td[0]=document.createElement("td");
		td[0].innerHTML=""+plans[j].ts;
		tr[0].appendChild(td[0]);
		
		td[1]=document.createElement("td");
		td[1].innerHTML=""+plans[j].fc;
		tr[1].appendChild(td[1]);
		
		td[2]=document.createElement("td");
		td[2].innerHTML=""+plans[j].odr;
		tr[2].appendChild(td[2]);
		
		td[3]=document.createElement("td");
		td[3].innerHTML=""+plans[j].gr;
		tr[3].appendChild(td[3]);
		
		td[4]=document.createElement("td");
		td[4].innerHTML=""+plans[j].pabI;
		tr[4].appendChild(td[4]);
		
		td[5]=document.createElement("td");
		td[5].innerHTML=""+plans[j].pab;
		tr[5].appendChild(td[5]);
		
		td[6]=document.createElement("td");
		td[6].innerHTML=""+plans[j].nr;
		tr[6].appendChild(td[6]);
	
		td[7]=document.createElement("td");
		td[7].innerHTML=""+plans[j].mps;
		tr[7].appendChild(td[7]);
		
		td[8]=document.createElement("td");
		td[8].innerHTML=""+plans[j].por;
		tr[8].appendChild(td[8]);
		
		td[9]=document.createElement("td");
		td[9].innerHTML=""+plans[j].atp;
		tr[9].appendChild(td[9]);
	}
	for(var k=0;k<10;k++){
		detailtb.appendChild(tr[k]);
	}
	
}
function showMPSes(){
	var mpses=getMPSes();
	var mpstb=document.getElementsByClassName("mpstb")[0];
	mpstb.innerHTML="<tr>"+
	 						 "<th>序号</th>"+
						     "<th>产品号</th>"+
						     "<th>产品名</th>"+
						     "<th>规格</th>"+
						     "<th>可用库存</th>"+
						     "<th>安全库存</th>"+
						     "<th>提前期</th>"+
						     "<th>批量</th>"+
						     "<th>需求时界</th>"+
						     "<th>计划时界</th>"+
					     "</tr>";
	mpses.forEach(function(item,index,array){
		 var product=getProductById(item.productid);
		 var tr=document.createElement("tr");
		 tr.innerHTML="<td>"+(index+1)+"</td>"+
					  "<td class=\"productid\">"+item.productid+"</td>"+
					  "<td class=\"productname\">"+product.productname+"</td>"+
					  "<td class=\"spec\">"+product.spec+"</td>"+
					  "<td class=\"stock\">"+product.stock+"</td>"+
					  "<td class=\"safestock\">"+product.safestock+"</td>"+
					  "<td class=\"leadtime\">"+product.leadtime+"</td>"+
					  "<td class=\"lotsize\">"+item.lotsize+"</td>"+
					  "<td class=\"rf\">"+item.rf+"</td>"+
					  "<td class=\"sf\">"+item.sf+"</td>";
		 tr.onclick=showDetail;
		 mpstb.appendChild(tr);
	 });
}

function refreshMPS(event){
	event.stopPropagation();
	showMPSes();
}
addLoadEvent(showMPSes);

function deleteMPS(event){
	event.stopPropagation();
	var xhr=new XMLHttpRequest();
	xhr.open("get","DeleteMPSById?id="+clickedmps,false);
	xhr.send(null);
	showMPSes();
	var detailtb=document.getElementById("detailtb");
	detailtb.innerHTML="";
}

/**///~~
function getReceipts(){
	var receipts=null;
	 var xhr=new XMLHttpRequest();
	 xhr.onreadystatechange=function(){
		if(xhr.readyState==4&&xhr.status==200){
			var res=xhr.responseText;
			receipts=JSON.parse(res);
		}
	}
	var requrl="QueryReceipts";
	xhr.open("get",requrl,false);
	xhr.send(null);
	return receipts;
}
function changetoform(){
	 var receiptform=document.getElementById("receiptform");
	 var tds=this.getElementsByTagName("td");
	 var selects=receiptform.getElementsByTagName("select");
	 var inputs=receiptform.getElementsByTagName("input");
	 selects[0].innerHTML=getProductOption();
	 setSelected(selects[0],tds[2].innerHTML);
	 inputs[0].value=tds[1].innerHTML;
	 inputs[1].value=tds[3].innerHTML;
	 inputs[2].value=tds[4].innerHTML;
	 var btn=receiptform.getElementsByClassName("btn")[0];
	 btn.innerHTML="<a href=\"javascript:void(0)\" style=\"width:150px;margin-left:30px; margin-right:3px;\" onclick=\"modifyReceipt(event)\">修改</a>"+
	 			   "<a href=\"javascript:void(0)\" style=\"width:150px;margin-left:3px; margin-right:3px;\" onclick=\"deleteReceipt(event)\">删除</a>"+
	 			   "<a href=\"javascript:void(0)\" style=\"width:150px;margin-left:3px; margin-right:3px;\" onclick=\"clearReceiptForm(event)\">取消</a>";
}

function clearReceiptForm(){
	 var receiptform=document.getElementById("receiptform");
	 var selects=receiptform.getElementsByTagName("select");
	 var inputs=receiptform.getElementsByTagName("input");
	 firstSelected(selects[0]);
	 inputs[0].value="";
	 inputs[1].value="";
	 inputs[2].value="";
	 var btn=receiptform.getElementsByClassName("btn")[0];
	 btn.innerHTML="<a href=\"javascript:void(0)\" style=\"width:400px; margin-left:79px;\" onclick=\"addReceipt(event)\">添加</a>";
}
function initReceiptForm(){
	 var receiptform=document.getElementById("receiptform");
	 var selects=receiptform.getElementsByTagName("select");
	 selects[0].innerHTML=getProductOption();
}

function addReceipt(event){
	 event.stopPropagation();
	 var receiptform=document.getElementById("receiptform");
	 var selects=receiptform.getElementsByTagName("select");
	 var inputs=receiptform.getElementsByTagName("input");
	 var productid=selects[0].value;
	 var quantity=inputs[1].value;
	 var from=inputs[2].value;
	var xhr=new XMLHttpRequest();
	var requrl="AddReceipt?productid="+productid+"&quantity="+quantity+"&from="+from;
	xhr.open("get",requrl,false);
	xhr.send(null);	
	showReceipts();
	clearReceiptForm();
}
function deleteReceipt(event){
	event.stopPropagation();
	var receiptform=document.getElementById("receiptform");
	var inputs=receiptform.getElementsByTagName("input");
	var receiptid=inputs[0].value;
	var xhr=new XMLHttpRequest();
	var requrl="DeleteReceipt?receiptid="+receiptid;
	xhr.open("get",requrl,false);
	xhr.send(null);	
	showReceipts();
	clearReceiptForm();
}
function modifyReceipt(event){
	event.stopPropagation();
	 var receiptform=document.getElementById("receiptform");
	 var selects=receiptform.getElementsByTagName("select");
	 var inputs=receiptform.getElementsByTagName("input");
	 var receiptid=inputs[0].value;
	 var productid=selects[0].value;
	 var quantity=inputs[1].value;
	 var from=inputs[2].value;
	 var xhr=new XMLHttpRequest();
	 var requrl="ModifyReceipt?receiptid="+receiptid+"&productid="+productid+"&quantity="+quantity+"&from="+from;
	 xhr.open("get",requrl,false);
	 xhr.send(null);	
	 showReceipts();
	 clearReceiptForm();
}
function showReceipts(){
	var receipts=getReceipts();
	var receipttb=document.getElementsByClassName("receipttb")[0];
	receipttb.innerHTML="<tr>"+
					"<th>序号</th>"+
					"<th>接收号</th>"+
					"<th>产品</th>"+
					"<th>接收量</th>"+
					"<th>来自</th>"+
					"<th>接收时间</th>"+
					"</tr>";
	receipts.forEach(function(item,index,array){
		 var tr=document.createElement("tr");
		 tr.innerHTML="<td>"+(index+1)+"</td>"+
		  			  "<td>"+item.receiptid+"</td>"+
		  			  "<td>"+item.productid+"</td>"+
		  			  "<td>"+item.quantity+"</td>"+
		  			  "<td>"+item.from+"</td>"+
		  			  "<td>"+item.date+"</td>";
		 tr.onclick=changetoform;
		 receipttb.appendChild(tr);
	});
	initReceiptForm();
}
addLoadEvent(showReceipts);

function getSRById(id){
	var sr=[];
	var xhr=new XMLHttpRequest();
	xhr.onreadystatechange=function(){
		if(xhr.readyState==4&&xhr.status==200){
			var res=xhr.responseText.split("&");
			sr[0]=parseInt(res[0]);
			sr[1]=parseInt(res[1]);
		}
	}
	xhr.open("get","GetSRByProductid?id="+id,false);
	xhr.send(null);
	return sr;
}
function getTS(date){
	Date.prototype.diff = function(date){
		  return (this.getTime() - date.getTime())/(24 * 60 * 60 * 1000);
		};
	var now=new Date();
	var today=new Date(now.getFullYear()+"-"+(now.getMonth()+1)+"-"+now.getDate());
	var dateday=new Date(date.replace(/-/g, '/'));
	var ts=dateday.diff(today);
	return ts;
}
function validate(id,date,func){
	var sr=getSRById(id);
	var rf=sr[0];
	var sf=sr[1];console.log(rf+""+sf);
	var ts=getTS(date);
	var user=JSON.parse(window.sessionStorage.getItem("user"));
	if(ts<=sf&&ts>rf){
		if(user.status=="管理员"||user.status=="高层"){
			func();
		}else{
			alert("您没有权限操作采购准备期/"+date);
		}
	}else if(ts<=rf){
		if(user.status=="高层"){
			func();
		}else{
			alert("您没有权限操作加工期/"+date);
		}
	}else{
		func();
	}
}

function exit(){
	window.location.href="login.html";
}