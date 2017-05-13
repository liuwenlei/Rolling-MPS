package eckel.model;

public class Customer {
	public int customerid;//2xxx
	public String customername;
	public String contacts;
	public String tel;
	public String email;
	public String address;
	
	public Customer(){
		
	}
	
	public Customer(int customerid,String customername,String contacts,String tel,String email,String address){
		this.customerid=customerid;
		this.customername=customername;
		this.contacts=contacts;
		this.tel=tel;
		this.email=email;
		this.address=address;
	}
	
	public String getJson(){
		String s1="\"customerid\":"+"\""+customerid+"\"";
		String s2="\"customername\":"+"\""+customername+"\"";
		String s3="\"contacts\":"+"\""+contacts+"\"";
		String s4="\"tel\":"+"\""+tel+"\"";
		String s5="\"email\":"+"\""+email+"\"";
		String s6="\"address\":"+"\""+address+"\"";
		String json="{"+s1+","+s2+","+s3+","+s4+","+s5+","+s6+"}";
		return json;
	}
}
