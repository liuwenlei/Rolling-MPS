/**
 * 
 */
function login(){
	var account=document.getElementById("account").value;
	var password=document.getElementById("password").value;
	var msg=document.getElementById("msg");
	var xhr=new XMLHttpRequest();
	xhr.onreadystatechange=function(){
		if(xhr.readyState==4&&xhr.status==200){
			var res=xhr.responseText.split("&");
			var key=res[0];
			var user=JSON.parse(res[1]);
			if(key==101){
				msg.innerHTML="密码错误";
			}else if(key==102){
				msg.innerHTML="账户不存在";
			}else{
				var session=window.sessionStorage;
				session.setItem("user",res[1]);
				window.location.href="index.html";
			}
		}
	}
	xhr.open("post","Login",true);
	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;");
	xhr.send("account="+account+"&password="+password);
}
		