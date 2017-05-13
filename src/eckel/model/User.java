package eckel.model;

import eckel.dao.UserDao;
import eckel.model.User;

public class User {
	public int userid;
	public String username;
	public String account;
	public String password;
	public String status;
	
	public User(){
		
	}
	
	public User(int userid,String username,String account,String password,String identity){
		this.userid=userid;
		this.username=username;
		this.account=account;
		this.password=password;
		this.status=identity;
	}
	
	public String getJson(){
		String s1="\"userid\":"+"\""+userid+"\"";
		String s2="\"username\":"+"\""+username+"\"";
		String s3="\"account\":"+"\""+account+"\"";
		String s4="\"password\":"+"\""+password+"\"";
		String s5="\"status\":"+"\""+status+"\"";
		String json="{"+s1+","+s2+","+s3+","+s4+","+s5+"}";
		return json;
	}
	
	public int login(){
		int key=101;//表示密码错误
		User tempUser=UserDao.getUserByAccount(this.account);
		if(tempUser.account.equals("")){
			key=102;//101表示账户不存在
		}else if(tempUser.password.equals(this.password)){
			key=100;//100表示密码正确
		}
		if(key==100){
			this.userid=tempUser.userid;
			this.username=tempUser.username;
			this.status=tempUser.status;
		}
		return key;
	}
}
