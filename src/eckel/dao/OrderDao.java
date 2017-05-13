package eckel.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import eckel.model.Order;

public class OrderDao {
	
	public static void addOrder(Order odr){
		Connection con=DBConnection.getConnection();
		String sql="insert into order_tb values(?,?,?,?,?)";
		PreparedStatement pstmt=null;
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,odr.orderid);
			pstmt.setInt(2,odr.productid);
			pstmt.setInt(3,odr.customerid);
			pstmt.setInt(4,odr.orderquantity);
			pstmt.setString(5,odr.deliverydate);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnection.close(pstmt);
			DBConnection.close(con);
		}
	}
	public static void modifyOrder(Order odr){
		Connection con=DBConnection.getConnection();
		String sql="update order_tb set productid=?,customerid=?,orderquantity=?,deliverydate=? where orderid=?";
		PreparedStatement pstmt=null;
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,odr.productid);
			pstmt.setInt(2,odr.customerid);
			pstmt.setInt(3,odr.orderquantity);
			pstmt.setString(4,odr.deliverydate);
			pstmt.setInt(5,odr.orderid);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnection.close(pstmt);
			DBConnection.close(con);
		}
	}
	
	public static List<Order> getOrders(){
		List<Order> orders=new ArrayList<Order>();
		Connection con=DBConnection.getConnection();
		String sql="select * from order_tb";
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				orders.add(new Order(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getString(5)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnection.close(pstmt);
			DBConnection.close(con);
		}
		return orders;
	}
	public static int getSumByDate(int productid,String day){
		int sum=0;
		Connection con=DBConnection.getConnection();
		String sql="select sum(orderquantity) from order_tb where productid=? and deliverydate=?";
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,productid);
			pstmt.setString(2,day);
			rs=pstmt.executeQuery();
			while(rs.next()){
				sum=rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnection.close(pstmt);
			DBConnection.close(con);
		}
		return sum;
	}
	public static void deleteOrderById(int orderid){
		Connection con=DBConnection.getConnection();
		String sql="delete from order_tb where orderid=?";
		PreparedStatement pstmt=null;
		try{
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,orderid);
			pstmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DBConnection.close(pstmt);
			DBConnection.close(con);
		}
	}
	public static Order getOrderById(int id){
		Order order=new Order();
		Connection con=DBConnection.getConnection();
		String sql="select * from order_tb where orderid=?";
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,id);
			rs=pstmt.executeQuery();
			while(rs.next()){
				order=new Order(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getString(5));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnection.close(pstmt);
			DBConnection.close(con);
		}
		return order;
	}
}
