package eckel.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Receipt {
	public int receiptid;
	public int productid;
	public int quantity;
	public String from;
	public String date;
	
	public Receipt(){
		
	}
	public Receipt(int receiptid,int productid,int quantity,String from,String date){
		this.receiptid=receiptid;
		this.productid=productid;
		this.quantity=quantity;
		this.from=from;
		this.date=date;
	}
	public Receipt(int receiptid,int productid,int quantity,String from){
		this.receiptid=receiptid;
		this.productid=productid;
		this.quantity=quantity;
		this.from=from;
		Date d=new Date();
		SimpleDateFormat format=new SimpleDateFormat("YYYY-MM-dd");
		String dStr=format.format(d);
		this.date=dStr;
	}
	
	public String getJson(){
		String s1="\"receiptid\":"+receiptid;
		String s2="\"productid\":"+productid;
		String s3="\"quantity\":"+quantity;
		String s4="\"from\":"+"\""+from+"\"";
		String s5="\"date\":"+"\""+date+"\"";
		String json="{"+s1+","+s2+","+s3+","+s4+","+s5+"}";
		return json;
	}

}
