package eckel.model;

public class Product {
	public int productid;
	public String productname;
	public String spec;
	public int leadtime;
	public int stock;
	public int safestock;
	
	public Product(){
		
	}
	public Product(int productid,String productname,String spec,int leadtime,int stock,int safestock){
		this.productid=productid;
		this.productname=productname;
		this.spec=spec;
		this.leadtime=leadtime;
		this.stock=stock;
		this.safestock=safestock;
	}
	
	public String getJson(){
		String s1="\"productid\":"+"\""+productid+"\"";
		String s2="\"productname\":"+"\""+productname+"\"";
		String s3="\"spec\":"+"\""+spec+"\"";
		String s4="\"leadtime\":"+"\""+leadtime+"\"";
		String s5="\"stock\":"+"\""+stock+"\"";
		String s6="\"safestock\":"+"\""+safestock+"\"";
		String json="{"+s1+","+s2+","+s3+","+s4+","+s5+","+s6+"}";
		return json;
	}
}
