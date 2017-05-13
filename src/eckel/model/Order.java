package eckel.model;

public class Order {
	public int orderid;
	public int productid;
	public int customerid;
	public int orderquantity;
	public String deliverydate;

	public Order(){
		
	}
	public Order(int orderid,int productid,int customerid,int orderquantity,String deliverydate){
		this.orderid=orderid;
		this.productid=productid;
		this.customerid=customerid;
		this.orderquantity=orderquantity;
		this.deliverydate=deliverydate;
	}
	public String getJson(){
		String s1="\"orderid\":"+orderid;
		String s2="\"productid\":"+productid;
		String s3="\"customerid\":"+customerid;
		String s4="\"orderquantity\":"+orderquantity;
		String s5="\"deliverydate\":"+"\""+deliverydate+"\"";
		String json="{"+s1+","+s2+","+s3+","+s4+","+s5+"}";
		return json;
	}

}
