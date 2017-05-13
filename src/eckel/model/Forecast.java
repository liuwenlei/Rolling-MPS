package eckel.model;

public class Forecast {
	public int forecastid;
	public int productid;
	public int customerid;
	public int forecastquantity;
	public String deliverydate;
	
	public Forecast(){
		
	}
	public Forecast(int forecastid,int productid,int customerid,int forecastquantity,String deliverydate){
		this.forecastid=forecastid;
		this.productid=productid;
		this.customerid=customerid;
		this.forecastquantity=forecastquantity;
		this.deliverydate=deliverydate;
	}
	public String getJson(){
		String s1="\"forecastid\":"+forecastid;
		String s2="\"productid\":"+productid;
		String s3="\"customerid\":"+customerid;
		String s4="\"forecastquantity\":"+forecastquantity;
		String s5="\"deliverydate\":"+"\""+deliverydate+"\"";
		String json="{"+s1+","+s2+","+s3+","+s4+","+s5+"}";
		return json;
	}


}
